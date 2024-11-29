package domain.models;

/**
 * Model de date pentru un cont bancar.
 * Această clasă reprezintă un cont bancar cu detalii precum ID-ul utilizatorului,
 * IBAN-ul, tipul contului și soldul.
 */
public class AccountModel {
    private String userId;
    private String iban;
    private String type;
    private double balance;

    /**
     * Construieste un obiect AccountModel cu detalii despre contul bancar.
     *
     * @param userId ID-ul utilizatorului asociat contului
     * @param iban IBAN-ul contului bancar
     * @param type tipul contului (de exemplu, "economii", "curent")
     * @param balance soldul contului
     */
    public AccountModel(String userId, String iban, String type, double balance) {
        this.userId = userId;
        this.iban = iban;
        this.type = type;
        this.balance = balance;
    }

    /**
     * Obține ID-ul utilizatorului asociat contului.
     *
     * @return ID-ul utilizatorului
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Obține IBAN-ul contului.
     *
     * @return IBAN-ul contului
     */
    public String getIban() {
        return iban;
    }

    /**
     * Obține tipul contului.
     *
     * @return tipul contului
     */
    public String getType() {
        return type;
    }

    /**
     * Obține soldul contului.
     *
     * @return soldul contului
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Returnează o reprezentare textuală a obiectului AccountModel.
     *
     * @return șirul care descrie obiectul AccountModel
     */
    @Override
    public String toString() {
        return "AccountModel{" +
                "userId='" + userId + '\'' +
                ", iban='" + iban + '\'' +
                ", type='" + type + '\'' +
                ", balance=" + balance +
                '}';
    }
}
