package lms;

import lms.model.Book;
import lms.model.DVD;
import lms.model.Magazine;
import lms.model.Member;
import lms.model.Staff;
import lms.model.Student;
import lms.service.Library;

import java.util.List;

/**
 * Test Driver for Week 4: Abstract Classes, Interfaces, and Comparable Sorting.
 * Demonstrates Loanable/Reservable contracts, abstract calculateLateFee polymorphism,
 * Magazine items, and defensive collection sorting.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("================================================================================");
        System.out.println(" SENG 12233: Library Management System (Week 4 - Abstraction & Interfaces)");
        System.out.println("================================================================================");

        Library library = new Library();

        // 1. Instantiating concrete items under Abstract LibraryItem
        System.out.println("\n[1] Registering Heterogeneous Library Items...");
        Book book = new Book("B100", "Domain-Driven Design", "Eric Evans");
        DVD dvd = new DVD("D200", "Interstellar", 169);
        Magazine magazine = new Magazine("M300", "IEEE Software Nov/Dec", 42);

        library.addItem(book);
        library.addItem(dvd);
        library.addItem(magazine);

        // 2. Registering Members
        System.out.println("\n[2] Registering University Members...");
        Student s1 = new Student("S001", "Kamal Wickramasinghe");
        Student s2 = new Student("S002", "Dulani Fernando");
        Staff st1 = new Staff("E001", "Prof. K. L. Silva");

        library.addMember(s1);
        library.addMember(s2);
        library.addMember(st1);

        library.displayCatalog();

        // 3. Demonstrating Loanable Contract & Business Rules
        System.out.println("\n[3] Testing Loanable Contract Workflows...");
        library.loanItem("B100", "S001");
        library.loanItem("D200", "S001");
        library.loanItem("M300", "E001");

        // Attempting to loan an item that is already borrowed
        System.out.println("\nAttempting loan of already-borrowed item:");
        library.loanItem("B100", "S002");

        // 4. Demonstrating Reservable Contract
        System.out.println("\n[4] Testing Reservable Contract Workflows...");
        // Book is Reservable
        System.out.println("Reserving Book (Reservable):");
        library.reserveItem("B100", "S002");

        // DVD is Non-Reservable
        System.out.println("\nAttempting reservation on DVD (Non-Reservable):");
        library.reserveItem("D200", "S002");

        // Magazine is Non-Reservable
        System.out.println("\nAttempting reservation on Magazine (Non-Reservable):");
        library.reserveItem("M300", "S002");

        // Cancelling Book reservation
        System.out.println("\nCancelling reservation on Book:");
        library.cancelReservation("B100");

        // 5. Demonstrating Abstract Method calculateLateFee Polymorphism
        System.out.println("\n[5] Calculating Overdue Late Fees (Dynamic Dispatch via Abstract Method)...");
        library.printAllLateFees(5);

        // 6. Demonstrating Comparable Sorting by Member Activity
        System.out.println("\n[6] Activity Ranking with Comparable<Member> (Defensive Copy)...");
        System.out.println("Original Member Registration Order in Repository:");
        for (Member m : library.getMembers()) {
            System.out.println("  " + m);
        }

        List<Member> rankedMembers = library.getMembersSortedByActivity();
        System.out.println("\nRanked Members by Activity (Highest active borrows first):");
        int rank = 1;
        for (Member m : rankedMembers) {
            System.out.printf("  #%d: %-25s | Borrows: %d%n", rank++, m.getName(), m.getBorrowedCount());
        }

        // Verify repository immutability
        System.out.println("\nVerifying Repository Internal Structure remains intact:");
        boolean orderPreserved = library.getMembers().get(0).getMemberId().equals("S001");
        System.out.println("Internal repository order intact: " + orderPreserved);

        // 7. Testing Item Return
        System.out.println("\n[7] Returning Loaned Items...");
        library.returnItem("B100");
        library.displayCatalog();

        System.out.println("\n>>> Week 4 Abstraction & Interfaces test completed successfully! <<<");
    }
}
