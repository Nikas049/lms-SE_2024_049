package lms.contract;

import lms.model.Member;

/**
 * Interface defining the contract for items supporting hold/reservation requests.
 */
public interface Reservable {
    /**
     * Places a reservation hold on the item for a specified member.
     *
     * @param member the member requesting reservation
     */
    void reserve(Member member);

    /**
     * Cancels an active reservation hold.
     */
    void cancelReservation();

    /**
     * Queries whether the item supports reservation holds.
     *
     * @return true if reservable
     */
    boolean isReservable();

    /**
     * Queries whether the item is currently reserved.
     *
     * @return true if currently reserved
     */
    boolean isReserved();

    /**
     * Retrieves the member holding the reservation.
     *
     * @return reserving Member, or null if unreserved
     */
    Member getReservedBy();
}
