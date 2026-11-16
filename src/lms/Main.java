package lms;

import lms.model.Book;
import lms.model.Member;
import lms.service.Library;

/**
 * Test Driver for Week 2: Classes, Objects, and Encapsulation.
 * Validates encapsulation constraints, mock entity composition, and safe state transitions.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("================================================================================");
        System.out.println("     SENG 12233: Library Management System (Week 2 - Encapsulation)");
        System.out.println("================================================================================");

        Library library = new Library();

        // 1. Adding valid Books and Members
        System.out.println("\n[1] Registering Valid Entities...");
        Book book1 = new Book("Clean Code", "Robert C. Martin", "978-0132350884");
        Book book2 = new Book("Effective Java", "Joshua Bloch", "978-0134685991");
        Book book3 = new Book("Design Patterns", "Erich Gamma et al.", "978-0201633610");

        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);

        Member mem1 = new Member("M001", "Kasun Perera");
        Member mem2 = new Member("M002", "Nimasha Silva");

        library.addMember(mem1);
        library.addMember(mem2);

        library.displayBooks();
        library.displayMembers();

        // 2. Demonstrating input validation and encapsulation enforcement
        System.out.println("\n[2] Demonstrating Input Validation & Encapsulation Guards...");

        // Test blank book title
        try {
            System.out.print("Testing blank Book title: ");
            new Book("   ", "Valid Author", "978-0000000000");
        } catch (IllegalArgumentException e) {
            System.out.println("CAUGHT EXPECTED EXCEPTION -> " + e.getMessage());
        }

        // Test null book author
        try {
            System.out.print("Testing null Book author: ");
            new Book("Valid Title", null, "978-0000000000");
        } catch (IllegalArgumentException e) {
            System.out.println("CAUGHT EXPECTED EXCEPTION -> " + e.getMessage());
        }

        // Test blank member ID
        try {
            System.out.print("Testing blank Member ID: ");
            new Member("", "Valid Name");
        } catch (IllegalArgumentException e) {
            System.out.println("CAUGHT EXPECTED EXCEPTION -> " + e.getMessage());
        }

        // Test negative borrowed count
        try {
            System.out.print("Testing negative Member borrowed count: ");
            mem1.setBorrowedCount(-5);
        } catch (IllegalArgumentException e) {
            System.out.println("CAUGHT EXPECTED EXCEPTION -> " + e.getMessage());
        }

        // 3. Testing Borrow and Return operations
        System.out.println("\n[3] Testing Lending Workflows & State Updates...");
        library.borrowBook("978-0132350884", "M001");
        // Attempt duplicate borrow
        library.borrowBook("978-0132350884", "M002");

        library.displayBooks();
        library.displayMembers();

        // Return book
        System.out.println("\n[4] Testing Item Return...");
        library.returnBook("978-0132350884", "M001");

        library.displayBooks();
        library.displayMembers();

        System.out.println("\n>>> Week 2 Encapsulation test completed successfully! <<<");
    }
}
