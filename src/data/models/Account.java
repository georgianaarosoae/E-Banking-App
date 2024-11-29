package data.models;

/**
 * Reprezintă un cont bancar asociat unui utilizator.
 * Conține informații despre IBAN, tipul contului și soldul curent.
 */
public class Account {
    private String userId;
    private String iban;
    private String type;
    private double balance;

    /**
     * Creează un nou cont bancar.
     *
     * @param userId ID-ul utilizatorului deținător al contului
     * @param iban IBAN-ul contului
     * @param type Tipul contului (ex: curent, economii)
     * @param balance Soldul inițial al contului
     */
    public Account(String userId, String iban, String type, double balance) {
        this.userId = userId;
        this.iban = iban;
        this.type = type;
        this.balance = balance;
    }

    /**
     * @return ID-ul utilizatorului deținător al contului
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @return IBAN-ul contului
     */
    public String getIban() {
        return iban;
    }

    /**
     * @return Tipul contului (ex: curent, economii)
     */
    public String getType() {
        return type;
    }

    /**
     * @return Soldul curent al contului
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Returnează o reprezentare text a contului, inclusiv ID-ul utilizatorului, IBAN-ul,
     * tipul contului și soldul curent.
     *
     * @return Reprezentarea text a contului
     */
    @Override
    public String toString() {
        return "Account{" +
                "userId='" + userId + '\'' +
                ", iban='" + iban + '\'' +
                ", type='" + type + '\'' +
                ", balance=" + balance +
                '}';
    }
}
