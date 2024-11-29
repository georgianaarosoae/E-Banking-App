package data.repository;

import data.models.User;
import domain.exceptions.UserNameException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository pentru operațiuni de încărcare și salvare a utilizatorilor dintr-un fișier.
 */
public class UserRepository {
    private static final String FILE_PATH = "src/resources/users_file.txt";

    /**
     * Încarcă toți utilizatorii din fișierul specificat.
     *
     * @return Lista de utilizatori încărcați
     * @throws IOException dacă apare o eroare de citire a fișierului
     */
    public static List<User> loadUsers() throws IOException {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String id = parts[0].trim();
                    String firstName = parts[1].trim();
                    String lastName = parts[2].trim();
                    try {
                        User user = new User(id, firstName, lastName);
                        users.add(user);
                    } catch (UserNameException e) {
                        System.err.println("Invalid user name: " + firstName + " " + lastName);
                    }
                }
            }
        }
        return users;
    }

    /**
     * Salvează lista de utilizatori în fișierul specificat.
     *
     * @param users Lista de utilizatori de salvat
     * @throws IOException dacă apare o eroare de scriere în fișier
     */
    public static void saveUsers(List<User> users) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (User user : users) {
                String line = user.getId() + "," + user.getFirstName() + "," + user.getLastName();
                writer.write(line);
                writer.newLine();
            }
        }
    }
}
