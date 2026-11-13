package practic2;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class TransactionReportGenerator {
    private static final int GRN_PER_STAR = 1000;
    private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("dd-MM-uuuu");
    private static final DateTimeFormatter MONTH = DateTimeFormatter.ofPattern("MM-uuuu");

    private TransactionReportGenerator() {
    }

    public static void printTransactions(List<Transaction> transactions) {
        transactions.forEach(System.out::println);
    }

    public static void printBalanceReport(double totalBalance) {
        System.out.println("Загальний баланс: " + totalBalance);
    }

    public static void printTransactionsCountByMonth(String monthYear, int count) {
        System.out.println("Кількість транзакцій за " + monthYear + ": " + count);
    }

    public static void printTopExpensesReport(List<Transaction> topExpenses) {
        System.out.println("10 найбільших витрат:");
        for (Transaction expense : topExpenses) {
            System.out.println(expense.getDescription() + ": " + expense.getAmount());
        }
    }

    public static void printPeriodReport(LocalDate from, LocalDate to,
                                         Optional<Transaction> largest,
                                         Optional<Transaction> smallest) {
        System.out.println("Витрати за період " + from.format(DATE) + " — " + to.format(DATE));
        System.out.println("Найбільша витрата: " + expenseText(largest));
        System.out.println("Найменша витрата: " + expenseText(smallest));
    }

    public static void printCategoryReport(Map<String, Double> totals) {
        System.out.println("Сумарні витрати за категоріями (1 * = 1000 грн):");
        totals.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(entry -> printBar(entry.getKey(), entry.getValue()));
    }

    public static void printMonthlyReport(Map<YearMonth, Double> totals) {
        System.out.println("Сумарні витрати за місяцями (1 * = 1000 грн):");
        totals.forEach((month, amount) -> printBar(month.format(MONTH), amount));
    }

    private static String expenseText(Optional<Transaction> expense) {
        return expense.map(transaction ->
                transaction.getDescription() + ": " + transaction.getAmount()
        ).orElse("Витрат немає");
    }

    private static void printBar(String label, double amount) {
        String bar = "*".repeat((int) (amount / GRN_PER_STAR));
        System.out.printf("%s: %.2f грн %s%n", label, amount, bar);
    }
}
