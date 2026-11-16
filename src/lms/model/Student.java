package lms.model;

/**
 * Represents an undergraduate or postgraduate student member.
 * Restricts max borrow limit to 3 items concurrently.
 */
public class Student extends Member {
    public static final int STUDENT_BORROW_LIMIT = 3;

    public Student(String memberId, String name) {
        super(memberId, name, STUDENT_BORROW_LIMIT);
    }

    @Override
    public String toString() {
        return String.format("Student [ID: %s, Name: %s, Borrowed: %d/%d]",
                memberId, name, borrowedCount, maxBorrowLimit);
    }
}
