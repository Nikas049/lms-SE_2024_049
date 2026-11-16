package lms.model;

import lms.contract.Loanable;

/**
 * Abstract base class representing a circulating library asset.
 * Implements the Loanable contract and mandates abstract calculateLateFee pricing.
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

    @Override
    public void loan(Member member) {
        if (member == null) {
            throw new IllegalArgumentException("Borrower cannot be null");
        }
        if (this.isBorrowed) {
            throw new IllegalStateException("Item \"" + title + "\" is already on loan to " + (borrower != null ? borrower.getName() : "another member"));
        }
        if (!member.canBorrow()) {
            throw new IllegalStateException("Member " + member.getName() + " has reached maximum borrow limit of " + member.getMaxBorrowLimit());
        }

        this.isBorrowed = true;
        this.borrower = member;
        member.incrementBorrowedCount();
    }

    @Override
    public void returnItem() {
        if (!this.isBorrowed) {
            throw new IllegalStateException("Item \"" + title + "\" is not currently borrowed");
        }
        if (this.borrower != null) {
            this.borrower.decrementBorrowedCount();
            this.borrower = null;
        }
        this.isBorrowed = false;
    }

    /**
     * Abstract contract forcing all concrete subclasses to define specific late fee pricing rules.
     *
     * @param daysLate number of overdue days
     * @return fee amount
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
