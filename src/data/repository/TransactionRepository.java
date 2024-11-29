package data.repository;

import data.models.Transaction;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Repository pentru operațiuni de încărcare și salvare a tranzacțiilor dintr-un fișier.
 */
public class TransactionRepository {
    private static final String FILE_PATH = "src/resources/transactions_file.txt";

    /**
     * Încarcă toate tranzacțiile din fișierul specificat.
     *
     * @return Lista de tranzacții încărcate
     * @throws IOException dacă apare o eroare de citire a fișierului
     */
    public static List<Transaction> loadTransactions() throws IOException {
        List<Transaction> transactions = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String iban = parts[0].trim();
                    double amount = Double.parseDouble(parts[1].trim());
                    String dateString = parts[2].trim();
                    try {
                        Date date = dateFormat.parse(dateString);
                        Transaction transaction = new Transaction(iban, amount, date);
                        transactions.add(transaction);
                    } catch (ParseException e) {
                        System.err.println("Date format is incorrect: " + dateString);
                    }
                }
            }
        }
        return transactions;
    }

    /**
     * Salvează lista de tranzacții în fișierul specificat.
     *
     * @param transactions Lista de tranzacții de salvat
     * @throws IOException dacă apare o eroare de scriere în fișier
     */
    public static void saveTransactions(List<Transaction> transactions) throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            for (Transaction transaction : transactions) {
                String line = transaction.getIban() + "," + transaction.getAmount() + "," +
                        dateFormat.format(transaction.getDate());
                writer.write(line);
                writer.newLine();
            }
        }
    }
}
