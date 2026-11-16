package lms.service;

import lms.contract.Reservable;
import lms.exception.DataAccessException;
import lms.exception.ItemNotAvailableException;
import lms.exception.MemberNotFoundException;
import lms.model.Book;
import lms.model.DVD;
import lms.model.LibraryItem;
import lms.model.Magazine;
import lms.model.Member;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Service orchestrating Library inventory, lending workflows, and custom exception routing.
 * Decouples business invariants from display presentation via structured exceptions.
 */
public class Library {
    private final List<LibraryItem> items;
    private final List<Member> members;

    public Library() {
        this.items = new ArrayList<>();
        this.members = new ArrayList<>();
    }

    public void addItem(LibraryItem item) {
        if (item == null) throw new IllegalArgumentException("Item cannot be null");
        items.add(item);
    }

    public void addItem(Book book) {
        if (book == null) throw new IllegalArgumentException("Book cannot be null");
        items.add(book);
    }

    public void addItem(DVD dvd) {
        if (dvd == null) throw new IllegalArgumentException("DVD cannot be null");
        items.add(dvd);
    }

    public void addItem(Magazine mag) {
        if (mag == null) throw new IllegalArgumentException("Magazine cannot be null");
        items.add(mag);
    }

    public void addMember(Member member) {
        if (member == null) throw new IllegalArgumentException("Member cannot be null");
        members.add(member);
    }

    /**
     * Looks up an item by its ID.
     * Throws checked ItemNotAvailableException if no matching item exists.
     *
     * @param id the item ID
     * @return the LibraryItem instance
     * @throws ItemNotAvailableException if item does not exist
     */
    public LibraryItem findItem(String id) throws ItemNotAvailableException {
        if (id == null || id.isBlank()) {
            throw new ItemNotAvailableException("Item ID cannot be null or blank.");
        }
        for (LibraryItem item : items) {
            if (item.getId().equalsIgnoreCase(id.trim())) {
                return item;
            }
        }
        throw new ItemNotAvailableException("Item with ID '" + id + "' does not exist in the library catalog.");
    }

    /**
     * Looks up a member by identifier.
     * Throws unchecked MemberNotFoundException if not found, storing the offending memberId.
     *
     * @param memberId unique member ID
     * @return registered Member
     * @throws MemberNotFoundException if member is not registered
     */
    public Member findMember(String memberId) throws MemberNotFoundException {
        if (memberId == null || memberId.isBlank()) {
            throw new MemberNotFoundException(memberId, "Member ID cannot be null or blank.");
        }
        for (Member member : members) {
            if (member.getMemberId().equalsIgnoreCase(memberId.trim())) {
                return member;
            }
        }
        throw new MemberNotFoundException(memberId, "Member with ID '" + memberId + "' was not found in registry.");
    }

    /**
     * Loans an item to a member using checked exception control flow.
     *
     * @param itemId   item identifier
     * @param memberId member identifier
     * @throws ItemNotAvailableException if item not found, already loaned, or limit exceeded
     * @throws MemberNotFoundException   if member does not exist
     */
    public void loanItem(String itemId, String memberId) throws ItemNotAvailableException {
        Member member = findMember(memberId); // May throw MemberNotFoundException (Unchecked)
        LibraryItem item = findItem(itemId);  // May throw ItemNotAvailableException (Checked)
        item.loan(member);                    // May throw ItemNotAvailableException (Checked)
    }

    /**
     * Returns an item and clears state.
     *
     * @param itemId item identifier
     * @throws ItemNotAvailableException if item not found or was not loaned out
     */
    public void returnItem(String itemId) throws ItemNotAvailableException {
        LibraryItem item = findItem(itemId);  // May throw ItemNotAvailableException (Checked)
        item.returnItem();                   // May throw ItemNotAvailableException (Checked)
    }

    /**
     * Reserves an item for a member.
     *
     * @param itemId   item identifier
     * @param memberId member identifier
     * @throws ItemNotAvailableException if item does not exist or does not support reservations
     */
    public void reserveItem(String itemId, String memberId) throws ItemNotAvailableException {
        Member member = findMember(memberId);
        LibraryItem item = findItem(itemId);
        if (item instanceof Reservable reservable) {
            reservable.reserve(member);
        } else {
            throw new ItemNotAvailableException("Item \"" + item.getTitle() + "\" (" +
                    item.getClass().getSimpleName() + ") does not support reservations.");
        }
    }

    /**
     * Cancels a reservation hold.
     *
     * @param itemId item identifier
     * @throws ItemNotAvailableException if item not found or not reservable
     */
    public void cancelReservation(String itemId) throws ItemNotAvailableException {
        LibraryItem item = findItem(itemId);
        if (item instanceof Reservable reservable) {
            reservable.cancelReservation();
        } else {
            throw new ItemNotAvailableException("Item \"" + item.getTitle() + "\" is not a reservable asset.");
        }
    }

    /**
     * Simulates underlying persistence layer failure, converting checked low-level exceptions
     * into a structured unchecked DataAccessException.
     *
     * @throws DataAccessException always thrown to test fault tolerance
     */
    public void simulateDatabaseFailure() {
        try {
            // Simulate SQL connection failure
            throw new SQLException("SQLState 08001: Connection refused at host: 192.168.1.50:3306 (DB_TIMEOUT)");
        } catch (SQLException ex) {
            throw new DataAccessException("Data layer failure: Unable to commit transaction to primary catalog store.", ex);
        }
    }

    /**
     * Ranks members by activity (borrowedCount descending).
     * Sorts defensive copy to avoid altering repository state.
     *
     * @return sorted list of members
     */
    public List<Member> getMembersSortedByActivity() {
        List<Member> defensiveCopy = new ArrayList<>(this.members);
        Collections.sort(defensiveCopy);
        return defensiveCopy;
    }

    public void printAllLateFees(int daysLate) {
        System.out.println("\n--- Late Fee Breakdown for " + daysLate + " Overdue Days ---");
        for (LibraryItem item : items) {
            double fee = item.calculateLateFee(daysLate);
            System.out.printf("  [%-4s] %-25s (%-8s) -> Fee: Rs. %-7.2f%n",
                    item.getId(), "\"" + item.getTitle() + "\"", item.getClass().getSimpleName(), fee);
        }
    }

    public void displayCatalog() {
        System.out.println("\n--- Catalog Inventory (" + items.size() + " items) ---");
        for (LibraryItem item : items) {
            System.out.println("  " + item);
        }
    }

    public void displayMembers() {
        System.out.println("\n--- Registered Members (" + members.size() + " members) ---");
        for (Member member : members) {
            System.out.println("  " + member);
        }
    }

    public List<LibraryItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public List<Member> getMembers() {
        return Collections.unmodifiableList(members);
    }
}
