package practic2;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public abstract class TransactionAnalyzer {
    private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("dd-MM-uuuu");
    private static final DateTimeFormatter MONTH = DateTimeFormatter.ofPattern("MM-uuuu");
    private static final Comparator<Transaction> BY_AMOUNT =
            Comparator.comparingDouble(Transaction::getAmount);

    private TransactionAnalyzer() {
    }

    public static double calculateTotalBalance(List<Transaction> transactions) {
        return transactions.stream().mapToDouble(Transaction::getAmount).sum();
    }

    public static int countTransactionsByMonth(List<Transaction> transactions, String monthYear) {
        YearMonth month = YearMonth.parse(monthYear, MONTH);
        return (int) transactions.stream()
                .filter(transaction -> YearMonth.from(dateOf(transaction)).equals(month))
                .count();
    }

    public static List<Transaction> findTopExpenses(List<Transaction> transactions) {
        return transactions.stream()
                .filter(transaction -> transaction.getAmount() < 0)
                .sorted(BY_AMOUNT)
                .limit(10)
                .toList();
    }

    public static Optional<Transaction> findLargestExpense(
            List<Transaction> transactions, LocalDate from, LocalDate to) {
        return expensesInPeriod(transactions, from, to).stream().min(BY_AMOUNT);
    }

    public static Optional<Transaction> findSmallestExpense(
            List<Transaction> transactions, LocalDate from, LocalDate to) {
        return expensesInPeriod(transactions, from, to).stream().max(BY_AMOUNT);
    }

    private static List<Transaction> expensesInPeriod(
            List<Transaction> transactions, LocalDate from, LocalDate to) {
        if (to.isBefore(from)) {
            throw new IllegalArgumentException("Кінець періоду раніше за початок");
        }
        return transactions.stream()
                .filter(transaction -> transaction.getAmount() < 0)
                .filter(transaction -> {
                    LocalDate date = dateOf(transaction);
                    return !date.isBefore(from) && !date.isAfter(to);
                })
                .toList();
    }

    public static Map<String, Double> expensesByCategory(List<Transaction> transactions) {
        Map<String, Double> totals = new TreeMap<>();
        for (Transaction transaction : transactions) {
            if (transaction.getAmount() < 0) {
                totals.merge(transaction.getDescription(), -transaction.getAmount(), Double::sum);
            }
        }
        return totals;
    }

    public static Map<YearMonth, Double> expensesByMonth(List<Transaction> transactions) {
        Map<YearMonth, Double> totals = new TreeMap<>();
        for (Transaction transaction : transactions) {
            if (transaction.getAmount() < 0) {
                YearMonth month = YearMonth.from(dateOf(transaction));
                totals.merge(month, -transaction.getAmount(), Double::sum);
            }
        }
        return totals;
    }

    private static LocalDate dateOf(Transaction transaction) {
        String date = transaction.getDate();
        if (date.length() == 10 && date.charAt(4) == '-') {
            return LocalDate.parse(date);
        }
        return LocalDate.parse(date, DATE);
    }
}
