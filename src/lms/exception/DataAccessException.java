package lms.exception;

/**
 * Unchecked runtime exception wrapping unexpected persistence and data-tier failures.
 * Shields business domain logic from infrastructure-specific exceptions (e.g. SQLException, IO faults).
 */
public class DataAccessException extends RuntimeException {

    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
