package di.service;

import data.models.Account;
import domain.mappers.AccountMapper;
import data.repository.AccountRepository;
import domain.models.AccountModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviciu pentru gestionarea operațiunilor asupra conturilor bancare.
 * Oferă metode pentru încărcarea, salvarea, adăugarea și ștergerea conturilor.
 */
public class AccountService {
    private List<AccountModel> accounts = new ArrayList<>();

    /**
     * Creează un nou serviciu pentru gestionarea conturilor și încarcă conturile din fișier.
     */
    public AccountService() {
        try {
            loadAccountsFromFile();
        } catch (IOException e) {
            System.out.println("Error loading accounts: " + e.getMessage());
        }
    }

    /**
     * Încarcă conturile din fișierul specificat și le adaugă în lista internă.
     *
     * @throws IOException dacă apare o eroare de citire a fișierului
     */
    private void loadAccountsFromFile() throws IOException {
        List<Account> listOfAccounts = AccountRepository.loadAccounts();
        List<AccountModel> list = listOfAccounts.stream().map(AccountMapper::toModel)
                .collect(Collectors.toList());
        accounts.addAll(list);
    }

    /**
     * Returnează lista de conturi asociate cu un anumit ID de utilizator.
     *
     * @param userId ID-ul utilizatorului
     * @return O listă de conturi ale utilizatorului specificat
     * @throws IOException dacă apare o eroare de citire a datelor
     */
    public List<AccountModel> getAccountsByUserId(String userId) throws IOException {
        return accounts.stream()
                .filter(account -> account.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    /**
     * Adaugă un cont nou în lista internă de conturi.
     *
     * @param account Contul de adăugat
     */
    public void addAccount(AccountModel account) {
        accounts.add(account);
    }

    /**
     * Returnează toate conturile încărcate în serviciu.
     *
     * @return O listă de conturi
     */
    public List<AccountModel> getAllAccounts() {
        return accounts;
    }

    /**
     * Salvează lista de conturi în fișierul specificat.
     *
     * @param accounts Lista de conturi de salvat
     * @throws IOException dacă apare o eroare de scriere în fișier
     */
    public void saveAccounts(List<AccountModel> accounts) throws IOException {
        AccountRepository.saveAccounts(accounts.stream().map(
                AccountMapper::toData).collect(Collectors.toList()));
    }

    /**
     * Șterge un cont din lista internă și salvează modificările în fișier.
     *
     * @param accountModel Contul de șters
     * @throws IOException dacă apare o eroare de scriere în fișier
     */
    public void deleteAccountById(AccountModel accountModel) throws IOException {
        accounts.removeIf(a -> a.equals(accountModel));
        saveAccounts(accounts);
    }

    /**
     * Reîncarcă lista finală de conturi din fișier și o salvează înapoi în fișier.
     *
     * @throws IOException dacă apare o eroare de citire sau scriere
     */
    public void saveFinalListOfAccounts() throws IOException {
        accounts = AccountRepository.loadAccounts().stream().map(
                AccountMapper::toModel
        ).collect(Collectors.toList());
        AccountRepository.saveAccounts(accounts.stream().map(
                AccountMapper::toData).collect(Collectors.toList()));
    }
}
