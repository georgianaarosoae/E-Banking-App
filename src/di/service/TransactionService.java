package di.service;

import data.models.Transaction;
import data.repository.TransactionRepository;
import domain.mappers.TransactionMapper;
import domain.models.TransactionModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviciu pentru gestionarea operațiunilor asupra tranzacțiilor bancare.
 * Oferă metode pentru încărcarea, salvarea, adăugarea și obținerea tranzacțiilor.
 */
public class TransactionService {
    private List<TransactionModel> transactions = new ArrayList<>();

    /**
     * Încarcă tranzacțiile din fișierul specificat și le adaugă în lista internă.
     *
     * @throws IOException dacă apare o eroare de citire a fișierului
     */
    private void loadTransactionsFromFile() throws IOException {
        List<Transaction> listOfTransactions = TransactionRepository.loadTransactions();
        List<TransactionModel> list = listOfTransactions.stream()
                .map(TransactionMapper::toModel)
                .collect(Collectors.toList());
        transactions.addAll(list);
    }

    /**
     * Returnează lista de tranzacții asociate unui cont, identificat prin IBAN.
     *
     * @param iban IBAN-ul contului
     * @return O listă de tranzacții pentru contul specificat
     * @throws IOException dacă apare o eroare de citire a fișierului
     */
    public List<TransactionModel> getTransactionsByAccountIban(String iban) throws IOException {
        List<Transaction> transactions = TransactionRepository.loadTransactions();
        return transactions.stream()
                .filter(transaction -> transaction.getIban().equals(iban))
                .map(TransactionMapper::toModel)
                .collect(Collectors.toList());
    }

    /**
     * Adaugă o tranzacție nouă și o salvează în fișier.
     *
     * @param transaction Tranzacția de adăugat
     * @throws IOException dacă apare o eroare de scriere în fișier
     */
    public void addTransaction(TransactionModel transaction) throws IOException {
        transactions.add(transaction);
        TransactionRepository.saveTransactions(transactions.stream().map(
                TransactionMapper::toData).collect(Collectors.toList()));
    }

    /**
     * Returnează toate tranzacțiile încărcate în serviciu.
     *
     * @return O listă de tranzacții
     */
    public List<TransactionModel> getAllTransactions() {
        return transactions;
    }

    /**
     * Salvează toate tranzacțiile din lista internă în fișier.
     *
     * @throws IOException dacă apare o eroare de scriere în fișier
     */
    public void saveTransactions() throws IOException {
        TransactionRepository.saveTransactions(transactions.stream()
                .map(TransactionMapper::toData)
                .collect(Collectors.toList()));
    }
}
