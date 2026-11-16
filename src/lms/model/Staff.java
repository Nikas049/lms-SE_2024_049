package lms.model;

/**
 * Represents academic and non-academic university staff.
 * Grants an extended max borrow limit of 10 items concurrently.
 */
public class Staff extends Member {
    public static final int STAFF_BORROW_LIMIT = 10;

    public Staff(String memberId, String name) {
        super(memberId, name, STAFF_BORROW_LIMIT);
    }

    @Override
    public String toString() {
        return String.format("Staff [ID: %s, Name: %s, Borrowed: %d/%d]",
                memberId, name, borrowedCount, maxBorrowLimit);
    }
}
