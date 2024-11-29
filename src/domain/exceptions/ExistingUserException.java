package domain.exceptions;

/**
 * Excepție aruncată atunci când un utilizator existent este întâlnit într-un sistem care necesită utilizatori unici.
 * Această excepție extinde {@link RuntimeException}, permițându-i să fie aruncată la rulare fără a fi necesar
 * să fie declarată explicit în semnăturile metodelor.
 */
public class ExistingUserException extends RuntimeException {

    /**
     * Construieste o nouă excepție ExistingUserException cu mesajul detaliat specificat.
     *
     * @param message mesajul detaliat, care oferă informații suplimentare despre excepție
     */
    public ExistingUserException(String message) {
        super(message);
    }
}
