package lms.model;

/**
 * Base class representing a library member.
 * Introduces maxBorrowLimit and borrowing capacity validation.
 */
public class Member {
    protected String memberId;
    protected String name;
    protected int borrowedCount;
    protected int maxBorrowLimit;

    /**
     * Constructs a Member instance with a customized borrow limit.
     *
     * @param memberId       unique member ID (non-blank)
     * @param name           member name (non-blank)
     * @param maxBorrowLimit maximum number of items allowed to borrow concurrently (> 0)
     */
    public Member(String memberId, String name, int maxBorrowLimit) {
        setMemberId(memberId);
        setName(name);
        setMaxBorrowLimit(maxBorrowLimit);
        this.borrowedCount = 0;
    }

    /**
     * Default constructor for general members (defaults limit to 5).
     */
    public Member(String memberId, String name) {
        this(memberId, name, 5);
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

    public int getMaxBorrowLimit() {
        return maxBorrowLimit;
    }

    public void setMaxBorrowLimit(int maxBorrowLimit) {
        if (maxBorrowLimit <= 0) {
            throw new IllegalArgumentException("Max borrow limit must be greater than zero");
        }
        this.maxBorrowLimit = maxBorrowLimit;
    }

    public boolean canBorrow() {
        return borrowedCount < maxBorrowLimit;
    }

    public void incrementBorrowedCount() {
        if (!canBorrow()) {
            throw new IllegalStateException("Member " + name + " has reached maximum borrow limit of " + maxBorrowLimit);
        }
        this.borrowedCount++;
    }

    public void decrementBorrowedCount() {
        if (this.borrowedCount > 0) {
            this.borrowedCount--;
        }
    }

    @Override
    public String toString() {
        return String.format("Member [ID: %s, Name: %s, Borrowed: %d/%d]",
                memberId, name, borrowedCount, maxBorrowLimit);
    }
}
