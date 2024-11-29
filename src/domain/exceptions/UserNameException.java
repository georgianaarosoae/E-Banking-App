package domain.exceptions;

/**
 * Excepție aruncată atunci când un nume de utilizator nu respectă criteriile cerute sau este invalid.
 * Această excepție extinde {@link RuntimeException}, permițându-i să fie aruncată la rulare fără a fi necesar
 * să fie declarată explicit în semnăturile metodelor.
 */
public class UserNameException extends RuntimeException {

    /**
     * Construieste o nouă excepție UserNameException cu mesajul detaliat specificat.
     *
     * @param message mesajul detaliat, care oferă informații suplimentare despre excepție
     */
    public UserNameException(String message) {
        super(message);
    }
}
