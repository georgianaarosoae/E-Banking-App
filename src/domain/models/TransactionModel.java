package domain.models;

import java.util.Date;

/**
 * Model de date pentru o tranzacție bancară.
 * Această clasă reprezintă o tranzacție care include detalii precum IBAN-ul,
 * suma tranzacționată și data tranzacției.
 */
public class TransactionModel {
    private String iban;
    private double amount;
    private Date date;

    /**
     * Construieste un obiect TransactionModel cu detalii despre tranzacție.
     *
     * @param iban IBAN-ul contului pentru tranzacție
     * @param amount suma tranzacției
     * @param date data la care a avut loc tranzacția
     */
    public TransactionModel(String iban, double amount, Date date) {
        this.iban = iban;
        this.amount = amount;
        this.date = date;
    }

    /**
     * Obține IBAN-ul asociat tranzacției.
     *
     * @return IBAN-ul tranzacției
     */
    public String getIban() {
        return iban;
    }

    /**
     * Obține suma tranzacției.
     *
     * @return suma tranzacției
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Obține data tranzacției.
     *
     * @return data tranzacției
     */
    public Date getDate() {
        return date;
    }

    /**
     * Setează IBAN-ul tranzacției.
     *
     * @param iban IBAN-ul tranzacției
     */
    public void setIban(String iban) {
        this.iban = iban;
    }

    /**
     * Setează suma tranzacției.
     *
     * @param amount suma tranzacției
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Setează data tranzacției.
     *
     * @param date data tranzacției
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Returnează o reprezentare textuală a obiectului TransactionModel.
     *
     * @return șirul care descrie obiectul TransactionModel
     */
    @Override
    public String toString() {
        return "TransactionModel{" +
                "iban='" + iban + '\'' +
                ", amount=" + amount +
                ", date=" + date +
                '}';
    }
}
