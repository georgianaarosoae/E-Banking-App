package domain.exceptions;

/**
 * Excepție aruncată atunci când o tranzacție este considerată invalidă.
 * Această excepție extinde {@link RuntimeException}, permițându-i să fie aruncată la rulare fără a fi necesar
 * să fie declarată explicit în semnăturile metodelor.
 */
public class InvalidTransactionException extends RuntimeException {

    /**
     * Construieste o nouă excepție InvalidTransactionException cu mesajul detaliat specificat.
     *
     * @param message mesajul detaliat, care oferă informații suplimentare despre excepție
     */
    public InvalidTransactionException(String message) {
        super(message);
    }
}
