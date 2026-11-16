package lms.model;

import lms.contract.Reservable;

/**
 * Concrete Book class.
 * Extends abstract LibraryItem and implements the Reservable interface contract.
 */
public class Book extends LibraryItem implements Reservable {
    private String author;
    private boolean isReserved;
    private Member reservedBy;

    public Book(String id, String title, String author) {
        super(id, title);
        setAuthor(author);
        this.isReserved = false;
        this.reservedBy = null;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        if (author == null || author.isBlank()) {
            throw new IllegalArgumentException("Author cannot be blank");
        }
        this.author = author.trim();
    }

    // ==========================================
    // Reservable Contract Implementation
    // ==========================================

    @Override
    public void reserve(Member member) {
        if (member == null) {
            throw new IllegalArgumentException("Reserving member cannot be null");
        }
        if (this.isReserved) {
            throw new IllegalStateException("Book \"" + title + "\" is already reserved by " + reservedBy.getName());
        }
        this.isReserved = true;
        this.reservedBy = member;
    }

    @Override
    public void cancelReservation() {
        if (!this.isReserved) {
            throw new IllegalStateException("Book \"" + title + "\" has no active reservation to cancel");
        }
        this.isReserved = false;
        this.reservedBy = null;
    }

    @Override
    public boolean isReservable() {
        return true;
    }

    @Override
    public boolean isReserved() {
        return isReserved;
    }

    @Override
    public Member getReservedBy() {
        return reservedBy;
    }

    // ==========================================
    // Abstract Method Implementation
    // ==========================================

    @Override
    public double calculateLateFee(int daysLate) {
        if (daysLate <= 0) {
            return 0.0;
        }
        return daysLate * 10.0;
    }

    @Override
    public String toString() {
        String base = String.format("Book [ID/ISBN: %s, Title: \"%s\", Author: %s, Status: %s]",
                id, title, author, (isBorrowed ? "Borrowed" : "Available"));
        if (isReserved) {
            base += " [Reserved by: " + (reservedBy != null ? reservedBy.getName() : "Unknown") + "]";
        }
        return base;
    }
}
