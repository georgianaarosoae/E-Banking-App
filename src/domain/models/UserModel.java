package domain.models;

/**
 * Model de date pentru un utilizator.
 * Această clasă reprezintă un utilizator cu detalii precum ID-ul, numele și prenumele.
 */
public class UserModel {
    private String id;
    private String firstName;
    private String lastName;

    /**
     * Construieste un obiect UserModel cu detalii despre utilizator.
     *
     * @param id ID-ul utilizatorului
     * @param firstName prenumele utilizatorului
     * @param lastName numele de familie al utilizatorului
     */
    public UserModel(String id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Obține ID-ul utilizatorului.
     *
     * @return ID-ul utilizatorului
     */
    public String getId() {
        return id;
    }

    /**
     * Obține prenumele utilizatorului.
     *
     * @return prenumele utilizatorului
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Obține numele de familie al utilizatorului.
     *
     * @return numele de familie al utilizatorului
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setează ID-ul utilizatorului.
     *
     * @param id ID-ul utilizatorului
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Setează prenumele utilizatorului.
     *
     * @param firstName prenumele utilizatorului
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Setează numele de familie al utilizatorului.
     *
     * @param lastName numele de familie al utilizatorului
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returnează o reprezentare textuală a obiectului UserModel.
     *
     * @return șirul care descrie obiectul UserModel
     */
    @Override
    public String toString() {
        return "UserModel{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
