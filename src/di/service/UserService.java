package di.service;


import data.models.User;
import data.repository.UserRepository;
import domain.exceptions.ExistingUserException;
import domain.exceptions.UserNameException;
import domain.mappers.UserMapper;
import domain.models.UserModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Serviciu pentru gestionarea operațiunilor asupra utilizatorilor.
 * Oferă metode pentru încărcarea, salvarea și adăugarea utilizatorilor.
 */
public class UserService {
    private List<UserModel> users = new ArrayList<>();

    /**
     * Încarcă utilizatorii din fișierul specificat și îi adaugă în lista internă.
     *
     * @throws IOException dacă apare o eroare de citire a fișierului
     */
    private void loadUsersFromFile() throws IOException {
        List<User> listOfUsers = UserRepository.loadUsers();
        users.addAll(listOfUsers.stream().map(UserMapper::toModel).collect(Collectors.toList()));
    }
    /**
     * Returnează toți utilizatorii încărcați în serviciu.
     *
     * @return O listă de utilizatori
     * @throws IOException dacă apare o eroare de citire a fișierului
     */

    public List<UserModel> getAllUsers() throws IOException {
        loadUsersFromFile();
        return users;
    }
    /**
     * Adaugă un utilizator nou în lista de utilizatori, dacă acesta nu există deja.
     *
     * @param usersSet Setul de utilizatori unde se va adăuga noul utilizator
     * @param user Utilizatorul de adăugat
     * @throws IOException dacă apare o eroare de scriere în fișier
     * @throws ExistingUserException dacă utilizatorul există deja în set
     */

    public void addUser(Set<UserModel> usersSet, User user) throws IOException {
        boolean userExists=usersSet.stream().anyMatch(existingUser->
                user.getId().equals(existingUser.getId()));
        if(userExists){
            throw new ExistingUserException("User with ID " + user.getId() + " already exists.");
        }
        usersSet.add(UserMapper.toModel(user));
        UserRepository.saveUsers(usersSet.stream().map(UserMapper::toData).collect(Collectors.toList()));
    }
    /**
     * Salvează toți utilizatorii din lista internă în fișier.
     *
     * @throws IOException dacă apare o eroare de scriere în fișier
     */

    public void saveUsers() throws IOException {
        UserRepository.saveUsers(users.stream().map(UserMapper::toData).collect(Collectors.toList()));
    }
}

