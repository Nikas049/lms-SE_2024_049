package lms.service;

import lms.model.Book;
import lms.model.DVD;
import lms.model.LibraryItem;
import lms.model.Member;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Library Service container managing polymorphic collections of LibraryItem and Member.
 * Demonstrates static binding (method overloading) and dynamic binding (polymorphic late fees).
 */
public class Library {
    private final List<LibraryItem> items;
    private final List<Member> members;

    public Library() {
        this.items = new ArrayList<>();
        this.members = new ArrayList<>();
    }

    // ==========================================
    // Static Binding (Method Overloading)
    // ==========================================

    /**
     * Overloaded addItem: processes generic LibraryItem.
     */
    public void addItem(LibraryItem item) {
        if (item == null) throw new IllegalArgumentException("Item cannot be null");
        items.add(item);
        System.out.println("[Static Binding: addItem(LibraryItem)] Added generic item: " + item.getTitle());
    }

    /**
     * Overloaded addItem: specialized for Book instances.
     */
    public void addItem(Book book) {
        if (book == null) throw new IllegalArgumentException("Book cannot be null");
        items.add(book);
        System.out.println("[Static Binding: addItem(Book)] Cataloged book by author '" + book.getAuthor() + "': " + book.getTitle());
    }

    /**
     * Overloaded addItem: specialized for DVD instances.
     */
    public void addItem(DVD dvd) {
        if (dvd == null) throw new IllegalArgumentException("DVD cannot be null");
        items.add(dvd);
        System.out.println("[Static Binding: addItem(DVD)] Cataloged DVD (" + dvd.getDurationMinutes() + " mins): " + dvd.getTitle());
    }

    public void addMember(Member member) {
        if (member == null) throw new IllegalArgumentException("Member cannot be null");
        members.add(member);
    }

    public LibraryItem findItemById(String id) {
        if (id == null || id.isBlank()) return null;
        for (LibraryItem item : items) {
            if (item.getId().equalsIgnoreCase(id.trim())) {
                return item;
            }
        }
        return null;
    }

    public Member findMemberById(String id) {
        if (id == null || id.isBlank()) return null;
        for (Member member : members) {
            if (member.getMemberId().equalsIgnoreCase(id.trim())) {
                return member;
            }
        }
        return null;
    }

    public boolean borrowItem(String itemId, String memberId) {
        LibraryItem item = findItemById(itemId);
        if (item == null) {
            System.out.println("[Borrow Failed] Item ID '" + itemId + "' not found.");
            return false;
        }
        if (item.isBorrowed()) {
            System.out.println("[Borrow Failed] Item \"" + item.getTitle() + "\" is already borrowed.");
            return false;
        }
        Member member = findMemberById(memberId);
        if (member == null) {
            System.out.println("[Borrow Failed] Member ID '" + memberId + "' not found.");
            return false;
        }
        if (!member.canBorrow()) {
            System.out.println("[Borrow Failed] Member " + member.getName() + " has reached borrowing limit of " + member.getMaxBorrowLimit() + ".");
            return false;
        }

        item.setBorrowed(true);
        member.incrementBorrowedCount();
        System.out.println("[Borrow Success] Item \"" + item.getTitle() + "\" loaned to " + member.getName() + " (" + member.getClass().getSimpleName() + ").");
        return true;
    }

    public boolean returnItem(String itemId, String memberId) {
        LibraryItem item = findItemById(itemId);
        if (item == null) {
            System.out.println("[Return Failed] Item ID '" + itemId + "' not found.");
            return false;
        }
        if (!item.isBorrowed()) {
            System.out.println("[Return Failed] Item \"" + item.getTitle() + "\" is not currently on loan.");
            return false;
        }
        Member member = findMemberById(memberId);
        if (member == null) {
            System.out.println("[Return Failed] Member ID '" + memberId + "' not found.");
            return false;
        }

        item.setBorrowed(false);
        member.decrementBorrowedCount();
        System.out.println("[Return Success] Item \"" + item.getTitle() + "\" returned by " + member.getName() + ".");
        return true;
    }

    // ==========================================
    // Dynamic Binding (Runtime Polymorphism)
    // ==========================================

    /**
     * Loops through all items and computes late fee dynamically based on runtime type.
     *
     * @param daysLate number of overdue days
     */
    public void printAllLateFees(int daysLate) {
        System.out.println("\n--- Late Fee Breakdown for " + daysLate + " Overdue Days (Dynamic Binding) ---");
        for (LibraryItem item : items) {
            double fee = item.calculateLateFee(daysLate); // Dynamic method dispatch!
            System.out.printf("  [%-4s] %-25s -> Fee: Rs. %-7.2f (Class: %s)%n",
                    item.getId(), "\"" + item.getTitle() + "\"", fee, item.getClass().getSimpleName());
        }
    }

    // ==========================================
    // Type Checking (instanceof Pattern Matching)
    // ==========================================

    /**
     * Inspects item compatibility and streams DVD multimedia if supported.
     *
     * @param item the item to test
     */
    public void streamIfPossible(LibraryItem item) {
        if (item == null) return;
        if (item instanceof DVD dvd) { // Java pattern matching instanceof
            System.out.println("[Streaming Service] Buffering stream for DVD: \"" + dvd.getTitle() +
                    "\" | Runtime: " + dvd.getDurationMinutes() + " mins. Streaming started!");
        } else {
            System.out.println("[Streaming Service] Streaming unavailable: Item \"" + item.getTitle() +
                    "\" is a physical " + item.getClass().getSimpleName() + ".");
        }
    }

    public void displayCatalog() {
        System.out.println("\n--- Current Catalog (" + items.size() + " items) ---");
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
