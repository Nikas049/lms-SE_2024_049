package lms.exception;

/**
 * Checked exception thrown when a requested library item cannot be loaned,
 * is already on loan, is missing from inventory, or violates lending invariants.
 * Callers are forced by the compiler to explicitly handle or declare this exception.
 */
public class ItemNotAvailableException extends Exception {

    public ItemNotAvailableException(String message) {
        super(message);
    }

    public ItemNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
