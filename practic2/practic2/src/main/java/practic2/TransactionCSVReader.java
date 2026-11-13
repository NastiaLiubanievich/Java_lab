package practic2;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public abstract class TransactionCSVReader {
    private TransactionCSVReader() {
    }

    public static List<Transaction> readTransactions(Reader source) throws IOException {
        List<Transaction> transactions = new ArrayList<>();

        try (CSVParser parser = CSVFormat.DEFAULT.parse(source)) {
            for (CSVRecord row : parser.getRecords()) {
                String date = row.get(0).strip().replaceFirst("^\uFEFF", "");
                if (row.getRecordNumber() == 1
                        && ("date".equalsIgnoreCase(date) || "дата".equalsIgnoreCase(date))) {
                    continue;
                }
                if (row.size() != 3) {
                    throw new IOException("Очікується три поля в рядку " + row.getRecordNumber());
                }

                try {
                    double amount = Double.parseDouble(row.get(1).strip());
                    transactions.add(new Transaction(date, amount, row.get(2).strip()));
                } catch (NumberFormatException e) {
                    throw new IOException("Некоректна сума в рядку " + row.getRecordNumber(), e);
                }
            }
        }
        return transactions;
    }
}