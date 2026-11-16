package lms.contract;

import lms.model.Member;

/**
 * Interface establishing the contract for loanable library assets.
 */
public interface Loanable {
    /**
     * Loans the item to a registered member.
     *
     * @param member the borrower
     */
    void loan(Member member);

    /**
     * Returns the item, resetting its loan status.
     */
    void returnItem();

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
