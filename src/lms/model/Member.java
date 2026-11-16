package lms.model;

/**
 * Represents a registered library member.
 * Demonstrates encapsulation with strict boundary validations.
 */
public class Member {
    private String memberId;
    private String name;
    private int borrowedCount;

    /**
     * Constructs a Member instance.
     * Constructor delegates initialization to validated setters.
     *
     * @param memberId unique member identifier (non-blank)
     * @param name     full name of the member (non-blank)
     */
    public Member(String memberId, String name) {
        setMemberId(memberId);
        setName(name);
        this.borrowedCount = 0;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        if (memberId == null || memberId.isBlank()) {
            throw new IllegalArgumentException("Member ID cannot be blank");
        }
        this.memberId = memberId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
        this.name = name.trim();
    }

    public int getBorrowedCount() {
        return borrowedCount;
    }

    public void setBorrowedCount(int borrowedCount) {
        if (borrowedCount < 0) {
            throw new IllegalArgumentException("Borrowed count cannot be negative");
        }
        this.borrowedCount = borrowedCount;
    }

    public void incrementBorrowedCount() {
        this.borrowedCount++;
    }

    public void decrementBorrowedCount() {
        if (this.borrowedCount > 0) {
            this.borrowedCount--;
        }
    }

    @Override
    public String toString() {
        return String.format("Member [ID: %s, Name: %s, Borrowed Count: %d]",
                memberId, name, borrowedCount);
    }
}
