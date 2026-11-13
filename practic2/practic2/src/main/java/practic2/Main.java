package practic2;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Main {
    private static final String CSV_URL = "https://informer.com.ua/dut/java/pr2.csv";
    private static final DateTimeFormatter MONTH = DateTimeFormatter.ofPattern("MM-uuuu");
    private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("dd-MM-uuuu");

    public static void main(String[] args) throws IOException {
        String source = args.length > 0 ? args[0] : CSV_URL;
        String monthYear = args.length > 1 ? args[1] : "01-2024";
        YearMonth month = YearMonth.parse(monthYear, MONTH);
        LocalDate from = args.length > 2 ? LocalDate.parse(args[2], DATE) : month.atDay(1);
        LocalDate to = args.length > 3 ? LocalDate.parse(args[3], DATE) : month.atEndOfMonth();

        List<Transaction> transactions = TransactionCSVReader.readTransactions(openSource(source));

        TransactionReportGenerator.printTransactions(transactions);
        TransactionReportGenerator.printBalanceReport(
                TransactionAnalyzer.calculateTotalBalance(transactions));
        TransactionReportGenerator.printTransactionsCountByMonth(
                monthYear, TransactionAnalyzer.countTransactionsByMonth(transactions, monthYear));
        TransactionReportGenerator.printTopExpensesReport(
                TransactionAnalyzer.findTopExpenses(transactions));
        TransactionReportGenerator.printPeriodReport(from, to,
                TransactionAnalyzer.findLargestExpense(transactions, from, to),
                TransactionAnalyzer.findSmallestExpense(transactions, from, to));
        TransactionReportGenerator.printCategoryReport(
                TransactionAnalyzer.expensesByCategory(transactions));
        TransactionReportGenerator.printMonthlyReport(
                TransactionAnalyzer.expensesByMonth(transactions));
    }

    private static Reader openSource(String source) throws IOException {
        if (source.startsWith("https://") || source.startsWith("http://")) {
            URLConnection connection = URI.create(source).toURL().openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            return new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8);
        }
        return Files.newBufferedReader(Path.of(source), StandardCharsets.UTF_8);
    }
}
