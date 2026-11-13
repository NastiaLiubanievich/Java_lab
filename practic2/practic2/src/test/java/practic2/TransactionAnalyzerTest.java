package practic2;

import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TransactionAnalyzerTest {
    @Test
    void testCalculateTotalBalance() {
        List<Transaction> transactions = List.of(
                new Transaction("2023-01-01", 100.0, "Дохід"),
                new Transaction("2023-01-02", -50.0, "Витрата"),
                new Transaction("2023-01-03", 150.0, "Дохід"));

        assertEquals(200.0, TransactionAnalyzer.calculateTotalBalance(transactions), 0.001);
    }

    @Test
    void testCountTransactionsByMonth() {
        List<Transaction> transactions = List.of(
                new Transaction("01-02-2023", 50.0, "Дохід"),
                new Transaction("15-02-2023", -20.0, "Витрата"),
                new Transaction("05-03-2023", 100.0, "Дохід"),
                new Transaction("01-02-2024", 75.0, "Дохід"));

        assertEquals(2, TransactionAnalyzer.countTransactionsByMonth(transactions, "02-2023"));
        assertEquals(1, TransactionAnalyzer.countTransactionsByMonth(transactions, "03-2023"));
    }

    @Test
    void testReadTransactions() throws Exception {
        String csv = """
                date,amount,description
                01-01-2024,100.0,Дохід
                02-01-2024,-50.0,"Книги, канцтовари"
                """;

        List<Transaction> transactions =
                TransactionCSVReader.readTransactions(new StringReader(csv));

        assertEquals(2, transactions.size());
        assertEquals("01-01-2024", transactions.getFirst().getDate());
        assertEquals(-50.0, transactions.getLast().getAmount(), 0.001);
        assertEquals("Книги, канцтовари", transactions.getLast().getDescription());
    }

    @Test
    void testFindTopExpenses() {
        List<Transaction> transactions = IntStream.rangeClosed(1, 13)
                .mapToObj(number -> new Transaction("01-01-2024",
                        number == 13 ? 1000 : -number, "Операція " + number))
                .toList();

        List<Double> amounts = TransactionAnalyzer.findTopExpenses(transactions).stream()
                .map(Transaction::getAmount)
                .toList();

        assertEquals(List.of(-12.0, -11.0, -10.0, -9.0, -8.0,
                -7.0, -6.0, -5.0, -4.0, -3.0), amounts);
    }

    @Test
    void testPeriodAndExpenseTotals() {
        Transaction food = new Transaction("01-01-2024", -1200, "Їжа");
        Transaction transport = new Transaction("31-01-2024", -100, "Транспорт");
        List<Transaction> transactions = List.of(food, transport,
                new Transaction("01-02-2024", -2000, "Їжа"),
                new Transaction("15-01-2024", 5000, "Дохід"));

        LocalDate from = LocalDate.of(2024, 1, 1);
        LocalDate to = LocalDate.of(2024, 1, 31);

        assertEquals(food, TransactionAnalyzer.findLargestExpense(transactions, from, to).orElseThrow());
        assertEquals(transport, TransactionAnalyzer.findSmallestExpense(transactions, from, to).orElseThrow());
        assertTrue(TransactionAnalyzer.findLargestExpense(transactions, from.plusYears(1),
                to.plusYears(1)).isEmpty());
        assertEquals(3200.0, TransactionAnalyzer.expensesByCategory(transactions).get("Їжа"), 0.001);
        assertEquals(1300.0, TransactionAnalyzer.expensesByMonth(transactions)
                .get(YearMonth.of(2024, 1)), 0.001);
    }
}
