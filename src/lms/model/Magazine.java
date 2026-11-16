package lms.model;

/**
 * Concrete Magazine periodical publication.
 * Extends abstract LibraryItem, tracking issue number with a daily late fee rate of Rs. 5.0.
 */
public class Magazine extends LibraryItem {
    private int issueNumber;

    /**
     * Constructs a Magazine entity.
     *
     * @param id          unique periodical identifier
     * @param title       publication title
     * @param issueNumber the edition/issue number (> 0)
     */
    public Magazine(String id, String title, int issueNumber) {
        super(id, title);
        setIssueNumber(issueNumber);
    }

    public int getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(int issueNumber) {
        if (issueNumber <= 0) {
            throw new IllegalArgumentException("Issue number must be greater than zero");
        }
        this.issueNumber = issueNumber;
    }

    @Override
    public double calculateLateFee(int daysLate) {
        if (daysLate <= 0) {
            return 0.0;
        }
        return daysLate * 5.0;
    }

    @Override
    public String toString() {
        return String.format("Magazine [ID: %s, Title: \"%s\", Issue #: %d, Status: %s]",
                id, title, issueNumber, (isBorrowed ? "Borrowed" : "Available"));
    }
}
