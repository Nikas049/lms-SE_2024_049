package lms.exception;

/**
 * Unchecked exception thrown when a member lookup fails.
 * Captures and encapsulates the offending member identifier for debugging and diagnostics.
 */
public class MemberNotFoundException extends RuntimeException {
    private final String offendingMemberId;

    public MemberNotFoundException(String memberId, String message) {
        super(message);
        this.offendingMemberId = memberId;
    }

    public MemberNotFoundException(String memberId) {
        this(memberId, "Member with ID '" + memberId + "' was not found in the registry.");
    }

    public String getOffendingMemberId() {
        return offendingMemberId;
    }
}
