package domain.mappers;

import data.models.Account;
import domain.models.AccountModel;

/**
 * Mapper pentru conversia între modelele de date {@link Account} și modelele de domeniu {@link AccountModel}.
 */
public class AccountMapper {

    /**
     * Convertește un obiect de tip {@link Account} într-un obiect de tip {@link AccountModel}.
     *
     * @param account obiectul de tip {@link Account} ce trebuie convertit
     * @return obiectul de tip {@link AccountModel} rezultat din conversie
     */
    public static AccountModel toModel(Account account) {
        return new AccountModel(account.getUserId(), account.getIban(), account.getType(), account.getBalance());
    }

    /**
     * Convertește un obiect de tip {@link AccountModel} într-un obiect de tip {@link Account}.
     *
     * @param account obiectul de tip {@link AccountModel} ce trebuie convertit
     * @return obiectul de tip {@link Account} rezultat din conversie
     */
    public static Account toData(AccountModel account) {
        return new Account(account.getUserId(), account.getIban(), account.getType(), account.getBalance());
    }
}
