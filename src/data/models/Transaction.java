package data.models;

import java.util.Date;

/**
 * Reprezintă o tranzacție bancară efectuată într-un cont specificat prin IBAN.
 * Conține informații despre suma tranzacționată și data tranzacției.
 */
public class Transaction {
    private String iban;
    private double amount;
    private Date date;

    /**
     * Creează o nouă tranzacție.
     *
     * @param iban IBAN-ul contului asociat tranzacției
     * @param amount Suma tranzacționată
     * @param date Data tranzacției
     */
    public Transaction(String iban, double amount, Date date) {
        this.iban = iban;
        this.amount = amount;
        this.date = date;
    }

    /**
     * @return IBAN-ul contului asociat tranzacției
     */
    public String getIban() {
        return iban;
    }

    /**
     * @return Suma tranzacționată
     */
    public double getAmount() {
        return amount;
    }

    /**
     * @return Data tranzacției
     */
    public Date getDate() {
        return date;
    }

    /**
     * Setează IBAN-ul contului pentru tranzacție.
     *
     * @param iban IBAN-ul contului
     */
    public void setIban(String iban) {
        this.iban = iban;
    }

    /**
     * Setează suma tranzacționată.
     *
     * @param amount Suma tranzacționată
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Setează data tranzacției.
     *
     * @param date Data tranzacției
     */
    public void setDate(Date date) {
        this.date = date;
    }
}
