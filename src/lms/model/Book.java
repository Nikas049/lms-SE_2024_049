package lms.model;

/**
 * Represents a Book in the Library Management System.
 * Demonstrates encapsulation with private fields and validated setters.
 */
public class Book {
    private String title;
    private String author;
    private String isbn;
    private boolean isBorrowed;

    /**
     * Constructs a new Book instance.
     * Constructor delegates initialization directly to validated setters.
     *
     * @param title  the book title (non-blank)
     * @param author the author name (non-blank)
     * @param isbn   the ISBN code (non-blank)
     */
    public Book(String title, String author, String isbn) {
        setTitle(title);
        setAuthor(author);
        setIsbn(isbn);
        this.isBorrowed = false;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        if (author == null || author.isBlank()) {
            throw new IllegalArgumentException("Author cannot be blank");
        }
        this.author = author.trim();
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        if (isbn == null || isbn.isBlank()) {
            throw new IllegalArgumentException("ISBN cannot be blank");
        }
        this.isbn = isbn.trim();
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void setBorrowed(boolean borrowed) {
        this.isBorrowed = borrowed;
    }

    @Override
    public String toString() {
        return String.format("Book [ISBN: %s, Title: \"%s\", Author: %s, Status: %s]",
                isbn, title, author, (isBorrowed ? "Borrowed" : "Available"));
    }
}
