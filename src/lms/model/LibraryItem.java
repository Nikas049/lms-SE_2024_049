package lms.model;

import lms.contract.Loanable;
import lms.exception.ItemNotAvailableException;

/**
 * Abstract base class representing a circulating library asset.
 * Implements the Loanable contract with robust invariant validation and checked exceptions.
 */
public abstract class LibraryItem implements Loanable {
    protected String id;
    protected String title;
    protected boolean isBorrowed;
    protected Member borrower;

    /**
     * Constructs a LibraryItem base instance.
     *
     * @param id    unique item identifier
     * @param title item title
     */
    public LibraryItem(String id, String title) {
        setId(id);
        setTitle(title);
        this.isBorrowed = false;
        this.borrower = null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Item ID cannot be blank");
        }
        this.id = id.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be blank");
        }
        this.title = title.trim();
    }

    @Override
    public boolean isBorrowed() {
        return isBorrowed;
    }

    @Override
    public Member getBorrower() {
        return borrower;
    }

    /**
     * Loans the item to a member.
     * Throws checked ItemNotAvailableException if item is already borrowed or member cannot borrow.
     *
     * @param member the borrower
     * @throws ItemNotAvailableException if item is unavailable or member limit exceeded
     */
    @Override
    public void loan(Member member) throws ItemNotAvailableException {
        if (member == null) {
            throw new IllegalArgumentException("Borrower cannot be null");
        }
        if (this.isBorrowed) {
            throw new ItemNotAvailableException(String.format(
                    "Item \"%s\" (ID: %s) is currently unavailable -- already loaned to %s.",
                    title, id, (borrower != null ? borrower.getName() : "another member")));
        }
        if (!member.canBorrow()) {
            throw new ItemNotAvailableException(String.format(
                    "Cannot loan \"%s\": Member %s has reached the maximum borrowing limit of %d items.",
                    title, member.getName(), member.getMaxBorrowLimit()));
        }

        // State tracking updates
        this.isBorrowed = true;
        this.borrower = member;
        member.incrementBorrowedCount();
    }

    /**
     * Returns the item to library circulation.
     * Clears borrower reference and safely decrements the member's borrow counter.
     *
     * @throws ItemNotAvailableException if the item was not checked out
     */
    @Override
    public void returnItem() throws ItemNotAvailableException {
        if (!this.isBorrowed) {
            throw new ItemNotAvailableException(String.format(
                    "Invariant violation: Cannot return item \"%s\" (ID: %s) because it is not currently on loan.",
                    title, id));
        }

        if (this.borrower != null) {
            this.borrower.decrementBorrowedCount();
            this.borrower = null; // Release ownership slot
        }
        this.isBorrowed = false;
    }

    /**
     * Abstract pricing contract forcing concrete subclasses to supply pricing calculations.
     *
     * @param daysLate number of overdue days
     * @return fee in currency units
     */
    public abstract double calculateLateFee(int daysLate);

    @Override
    public String toString() {
        return String.format("[%s] ID: %s | Title: \"%s\" | Status: %s%s",
                getClass().getSimpleName(), id, title,
                (isBorrowed ? "Borrowed by " + (borrower != null ? borrower.getName() : "Unknown") : "Available"),
                (this instanceof lms.contract.Reservable res && res.isReserved() ? " (Reserved by " + res.getReservedBy().getName() + ")" : ""));
    }
}
