package lms.model;

/**
 * Represents a Book, extending the general LibraryItem superclass.
 * Inherits id, title, and isBorrowed, and introduces author.
 */
public class Book extends LibraryItem {
    private String author;

    /**
     * Constructs a Book entity.
     *
     * @param id     unique item ID / ISBN (non-blank)
     * @param title  the book title (non-blank)
     * @param author the author name (non-blank)
     */
    public Book(String id, String title, String author) {
        super(id, title);
        setAuthor(author);
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

    // Book inherits the default calculateLateFee: daysLate * 10.0

    @Override
    public String toString() {
        return String.format("Book [ID/ISBN: %s, Title: \"%s\", Author: %s, Status: %s]",
                id, title, author, (isBorrowed ? "Borrowed" : "Available"));
    }
}
