package lms.model;

/**
 * Represents a DVD disc item, extending LibraryItem.
 * Introduces duration in minutes and overrides late fee calculation (daysLate * 25.0).
 */
public class DVD extends LibraryItem {
    private int durationMinutes;

    /**
     * Constructs a DVD item.
     *
     * @param id              unique item identifier (non-blank)
     * @param title           title of the movie/documentary (non-blank)
     * @param durationMinutes length of runtime in minutes (must be > 0)
     */
    public DVD(String id, String title, int durationMinutes) {
        super(id, title);
        setDurationMinutes(durationMinutes);
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        if (durationMinutes <= 0) {
            throw new IllegalArgumentException("Duration must be greater than zero minutes");
        }
        this.durationMinutes = durationMinutes;
    }

    /**
     * Overrides late fee calculation for multimedia DVD items.
     * Formula: daysLate * 25.0
     *
     * @param daysLate number of overdue days
     * @return calculated late fee
     */
    @Override
    public double calculateLateFee(int daysLate) {
        if (daysLate <= 0) {
            return 0.0;
        }
        return daysLate * 25.0;
    }

    @Override
    public String toString() {
        return String.format("DVD [ID: %s, Title: \"%s\", Duration: %d mins, Status: %s]",
                id, title, durationMinutes, (isBorrowed ? "Borrowed" : "Available"));
    }
}
