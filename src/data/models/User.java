package data.models;

import domain.exceptions.UserNameException;

import java.util.Objects;

/**
 * Reprezintă un utilizator cu un ID unic, prenume și nume de familie.
 */
public class User {
    private String id;
    private String firstName;
    private String lastName;

    /**
     * Creează un nou utilizator.
     *
     * @param id ID-ul unic al utilizatorului
     * @param firstName Prenumele utilizatorului
     * @param lastName Numele de familie al utilizatorului
     * @throws UserNameException dacă prenumele sau numele este null
     */
    public User(String id, String firstName, String lastName) throws UserNameException {
        this.id = id;
        if (firstName == null || lastName == null) {
            throw new UserNameException("Invalid names for user!");
        }
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * @return ID-ul unic al utilizatorului
     */
    public String getId() {
        return id;
    }

    /**
     * @return Prenumele utilizatorului
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @return Numele de familie al utilizatorului
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returnează o reprezentare text a utilizatorului.
     *
     * @return Reprezentarea text a utilizatorului, incluzând ID-ul, prenumele și numele
     */
    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    /**
     * Verifică egalitatea între acest utilizator și alt obiect pe baza ID-ului.
     *
     * @param o Obiectul de comparat
     * @return true dacă obiectele sunt egale, altfel false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    /**
     * Calculează codul hash pe baza ID-ului utilizatorului.
     *
     * @return Codul hash al utilizatorului
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
