package lms.model;

/**
 * Base member entity implementing Comparable to enable natural activity-based sorting.
 * Orders members descending by borrowedCount (most active first).
 */
public class Member implements Comparable<Member> {
    protected String memberId;
    protected String name;
    protected int borrowedCount;
    protected int maxBorrowLimit;

    public Member(String memberId, String name, int maxBorrowLimit) {
        setMemberId(memberId);
        setName(name);
        setMaxBorrowLimit(maxBorrowLimit);
        this.borrowedCount = 0;
    }

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
        this.borrowedCount++;
    }

    public void decrementBorrowedCount() {
        if (this.borrowedCount > 0) {
            this.borrowedCount--;
        }
    }

    /**
     * Natural ordering for Member: sorts members by activity (borrowedCount descending).
     * Breaks ties deterministically using memberId ascending.
     *
     * @param other the other Member to compare with
     * @return negative if this member is more active, positive if less active, 0 if identical
     */
    @Override
    public int compareTo(Member other) {
        if (other == null) {
            return -1;
        }
        int countDiff = Integer.compare(other.borrowedCount, this.borrowedCount); // Descending order
        if (countDiff != 0) {
            return countDiff;
        }
        return this.memberId.compareToIgnoreCase(other.memberId);
    }

    @Override
    public String toString() {
        return String.format("%s [ID: %s, Name: %s, Active Borrows: %d/%d]",
                getClass().getSimpleName(), memberId, name, borrowedCount, maxBorrowLimit);
    }
}
