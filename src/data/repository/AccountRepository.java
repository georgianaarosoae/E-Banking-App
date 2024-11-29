package data.repository;

import data.models.Account;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository pentru operațiuni de încărcare și salvare a conturilor dintr-un fișier.
 */
public class AccountRepository {
    private static final String FILE_PATH = "src/resources/accounts_file.txt";

    /**
     * Încarcă toate conturile din fișierul specificat.
     *
     * @return Lista de conturi încărcate
     * @throws IOException dacă apare o eroare de citire a fișierului
     */
    public static List<Account> loadAccounts() throws IOException {
        List<Account> accounts = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Account account = new Account(parts[0], parts[1], parts[2], Double.parseDouble(parts[3]));
                accounts.add(account);
            }
        }
        return accounts;
    }

    /**
     * Salvează lista de conturi în fișierul specificat.
     *
     * @param accounts Lista de conturi de salvat
     * @throws IOException dacă apare o eroare de scriere în fișier
     */
    public static void saveAccounts(List<Account> accounts) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Account account : accounts) {
                String line = account.getUserId() + "," + account.getIban() + "," +
                        account.getType() + "," + account.getBalance();
                writer.write(line);
                writer.newLine();
            }
        }
    }
}
