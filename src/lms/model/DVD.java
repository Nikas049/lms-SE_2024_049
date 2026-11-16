package lms.model;

/**
 * Concrete DVD multimedia asset.
 * Extends abstract LibraryItem, remains non-reservable, and implements specific pricing.
 */
public class DVD extends LibraryItem {
    private int durationMinutes;

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
