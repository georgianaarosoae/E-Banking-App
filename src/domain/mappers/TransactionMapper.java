package domain.mappers;

import data.models.Transaction;
import domain.exceptions.InvalidTransactionException;
import domain.models.TransactionModel;

/**
 * Mapper pentru conversia între modelele de date {@link Transaction} și modelele de domeniu {@link TransactionModel}.
 */
public class TransactionMapper {

    /**
     * Convertește un obiect de tip {@link Transaction} într-un obiect de tip {@link TransactionModel}.
     * Aruncă excepție {@link InvalidTransactionException} dacă IBAN-ul este gol sau null, sau dacă suma tranzacției este 0.
     *
     * @param transaction obiectul de tip {@link Transaction} ce trebuie convertit
     * @return obiectul de tip {@link TransactionModel} rezultat din conversie
     * @throws InvalidTransactionException dacă IBAN-ul este gol sau null, sau dacă suma tranzacției este 0
     */
    public static TransactionModel toModel(Transaction transaction) {
        if (transaction.getIban() == null || transaction.getIban().isEmpty()) {
            throw new InvalidTransactionException("IBAN-ul nu poate fi gol sau null.");
        }
        if (transaction.getAmount() == 0) {
            throw new InvalidTransactionException("Suma tranzacției nu poate fi 0!");
        }
        return new TransactionModel(transaction.getIban(), transaction.getAmount(), transaction.getDate());
    }

    /**
     * Convertește un obiect de tip {@link TransactionModel} într-un obiect de tip {@link Transaction}.
     * Aruncă excepție {@link InvalidTransactionException} dacă IBAN-ul este gol sau null, sau dacă suma tranzacției este 0.
     *
     * @param transaction obiectul de tip {@link TransactionModel} ce trebuie convertit
     * @return obiectul de tip {@link Transaction} rezultat din conversie
     * @throws InvalidTransactionException dacă IBAN-ul este gol sau null, sau dacă suma tranzacției este 0
     */
    public static Transaction toData(TransactionModel transaction) {
        if (transaction.getIban() == null || transaction.getIban().isEmpty()) {
            throw new InvalidTransactionException("IBAN-ul nu poate fi gol sau null.");
        }
        if (transaction.getAmount() == 0) {
            throw new InvalidTransactionException("Suma tranzacției nu poate fi 0!");
        }
        return new Transaction(transaction.getIban(), transaction.getAmount(), transaction.getDate());
    }
}
