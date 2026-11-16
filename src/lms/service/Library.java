package lms.service;

import lms.contract.Loanable;
import lms.contract.Reservable;
import lms.model.Book;
import lms.model.DVD;
import lms.model.LibraryItem;
import lms.model.Magazine;
import lms.model.Member;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Service container coordinating LibraryItem assets, contracts (Loanable, Reservable),
 * and Comparable member activity sorting.
 */
public class Library {
    private final List<LibraryItem> items;
    private final List<Member> members;

    public Library() {
        this.items = new ArrayList<>();
        this.members = new ArrayList<>();
    }

    // Overloaded addItem methods demonstrating compile-time static binding
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

    public boolean loanItem(String itemId, String memberId) {
        LibraryItem item = findItemById(itemId);
        if (item == null) {
            System.out.println("[Loan Error] Item ID '" + itemId + "' not found.");
            return false;
        }
        Member member = findMemberById(memberId);
        if (member == null) {
            System.out.println("[Loan Error] Member ID '" + memberId + "' not found.");
            return false;
        }

        try {
            item.loan(member); // Delegates directly to Loanable contract method
            System.out.println("[Loan Success] Item \"" + item.getTitle() + "\" loaned to " + member.getName() + ".");
            return true;
        } catch (IllegalStateException | IllegalArgumentException e) {
            System.out.println("[Loan Denied] " + e.getMessage());
            return false;
        }
    }

    public boolean returnItem(String itemId) {
        LibraryItem item = findItemById(itemId);
        if (item == null) {
            System.out.println("[Return Error] Item ID '" + itemId + "' not found.");
            return false;
        }

        try {
            item.returnItem(); // Delegates directly to Loanable contract method
            System.out.println("[Return Success] Item \"" + item.getTitle() + "\" returned successfully.");
            return true;
        } catch (IllegalStateException e) {
            System.out.println("[Return Denied] " + e.getMessage());
            return false;
        }
    }

    public boolean reserveItem(String itemId, String memberId) {
        LibraryItem item = findItemById(itemId);
        if (item == null) {
            System.out.println("[Reservation Error] Item ID '" + itemId + "' not found.");
            return false;
        }
        Member member = findMemberById(memberId);
        if (member == null) {
            System.out.println("[Reservation Error] Member ID '" + memberId + "' not found.");
            return false;
        }

        if (item instanceof Reservable reservableItem) {
            try {
                reservableItem.reserve(member);
                System.out.println("[Reservation Success] Item \"" + item.getTitle() + "\" reserved for " + member.getName() + ".");
                return true;
            } catch (IllegalStateException e) {
                System.out.println("[Reservation Denied] " + e.getMessage());
                return false;
            }
        } else {
            System.out.println("[Reservation Unsupported] Item \"" + item.getTitle() + "\" (" + item.getClass().getSimpleName() + ") does not support reservations.");
            return false;
        }
    }

    public boolean cancelReservation(String itemId) {
        LibraryItem item = findItemById(itemId);
        if (item == null) {
            System.out.println("[Cancel Error] Item ID '" + itemId + "' not found.");
            return false;
        }

        if (item instanceof Reservable reservableItem) {
            try {
                reservableItem.cancelReservation();
                System.out.println("[Cancel Success] Reservation cancelled for \"" + item.getTitle() + "\".");
                return true;
            } catch (IllegalStateException e) {
                System.out.println("[Cancel Denied] " + e.getMessage());
                return false;
            }
        } else {
            System.out.println("[Cancel Unsupported] Item \"" + item.getTitle() + "\" is not a reservable asset.");
            return false;
        }
    }

    /**
     * Ranks members by borrowing activity (most active first).
     * IMPORTANT: Sorts a defensive copy to prevent unintended mutation of internal lists.
     *
     * @return new sorted List of members
     */
    public List<Member> getMembersSortedByActivity() {
        List<Member> defensiveCopy = new ArrayList<>(this.members);
        Collections.sort(defensiveCopy); // Invokes Member.compareTo
        return defensiveCopy;
    }

    public void printAllLateFees(int daysLate) {
        System.out.println("\n--- Late Fee Evaluation for " + daysLate + " Overdue Days (Dynamic Binding) ---");
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
