package lms.model;

/**
 * Superclass for all circulating library items.
 * Extracts common properties (id, title, isBorrowed) and defines default late fee calculation.
 */
public class LibraryItem {
    protected String id;
    protected String title;
    protected boolean isBorrowed;

    /**
     * Constructs a LibraryItem with validated fields.
     *
     * @param id    unique item identifier / code (non-blank)
     * @param title the item title (non-blank)
     */
    public LibraryItem(String id, String title) {
        setId(id);
        setTitle(title);
        this.isBorrowed = false;
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

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void setBorrowed(boolean borrowed) {
        this.isBorrowed = borrowed;
    }

    /**
     * Calculates late fees for an overdue item.
     * Default rule for general library items: daysLate * 10.0
     *
     * @param daysLate number of overdue days
     * @return calculated late fee in standard currency units
     */
    public double calculateLateFee(int daysLate) {
        if (daysLate <= 0) {
            return 0.0;
        }
        return daysLate * 10.0;
    }

    @Override
    public String toString() {
        return String.format("LibraryItem [ID: %s, Title: \"%s\", Status: %s]",
                id, title, (isBorrowed ? "Borrowed" : "Available"));
    }
}
