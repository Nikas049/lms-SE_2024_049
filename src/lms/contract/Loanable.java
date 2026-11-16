package lms.contract;

import lms.exception.ItemNotAvailableException;
import lms.model.Member;

/**
 * Interface establishing the contract for loanable library assets.
 * Methods declare checked ItemNotAvailableException forcing callers to manage lending invariant failures.
 */
public interface Loanable {
    /**
     * Loans the item to a registered member.
     *
     * @param member the borrower
     * @throws ItemNotAvailableException if the item is already borrowed or member limit is violated
     */
    void loan(Member member) throws ItemNotAvailableException;

    /**
     * Returns the item, resetting its loan status and releasing borrower hold.
     *
     * @throws ItemNotAvailableException if the item is not currently on loan
     */
    void returnItem() throws ItemNotAvailableException;

    /**
     * Checks whether the item is currently on loan.
     *
     * @return true if loaned out, false otherwise
     */
    boolean isBorrowed();

    /**
     * Retrieves the current borrower.
     *
     * @return the borrower Member, or null if available
     */
    Member getBorrower();
}
