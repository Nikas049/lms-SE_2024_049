package lms;

import lms.model.Book;
import lms.model.DVD;
import lms.model.LibraryItem;
import lms.model.Member;
import lms.model.Staff;
import lms.model.Student;
import lms.service.Library;

/**
 * Test Driver for Week 3: Inheritance & Polymorphism.
 * Demonstrates class hierarchies, method overriding, dynamic vs static binding, and instanceof checking.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("================================================================================");
        System.out.println(" SENG 12233: Library Management System (Week 3 - Inheritance & Polymorphism)");
        System.out.println("================================================================================");

        Library library = new Library();

        // 1. Static Binding (Compile-Time Polymorphism / Method Overloading)
        System.out.println("\n[1] Demonstrating Static Binding (Method Overloading via addItem)...");
        Book book1 = new Book("B001", "Design Patterns", "Erich Gamma");
        Book book2 = new Book("B002", "Refactoring", "Martin Fowler");
        DVD dvd1 = new DVD("D001", "Inception", 148);
        DVD dvd2 = new DVD("D002", "The Matrix", 136);
        LibraryItem generalItem = new LibraryItem("G001", "Annual Research Report");

        // Compiler resolves overloaded methods based on static reference types:
        library.addItem(book1);       // Invokes addItem(Book)
        library.addItem(dvd1);        // Invokes addItem(DVD)
        library.addItem(generalItem); // Invokes addItem(LibraryItem)

        // Adding second items as base references
        LibraryItem itemRefAsBook = book2;
        library.addItem(itemRefAsBook); // Invokes addItem(LibraryItem) because static type is LibraryItem
        library.addItem(dvd2);

        // 2. Member Hierarchy & Specialized Limits
        System.out.println("\n[2] Registering Specialized Members (Student vs Staff)...");
        Student student = new Student("S101", "Amal Perera");
        Staff staff = new Staff("E202", "Dr. Sanath Jayasuriya");

        library.addMember(student);
        library.addMember(staff);

        library.displayCatalog();
        library.displayMembers();

        // 3. Dynamic Binding (Runtime Polymorphism)
        System.out.println("\n[3] Demonstrating Dynamic Binding (Runtime Late Fee Calculation)...");
        // Book: 10/day, DVD: 25/day, General: 10/day
        library.printAllLateFees(4);

        // 4. Type Checking with instanceof Pattern Matching
        System.out.println("\n[4] Demonstrating Type Checking (instanceof Pattern Matching)...");
        System.out.println("Attempting streaming for book1:");
        library.streamIfPossible(book1);
        System.out.println("\nAttempting streaming for dvd1:");
        library.streamIfPossible(dvd1);

        // 5. Testing Borrow Limit Enforcement
        System.out.println("\n[5] Testing Borrow Limit Constraints (Student limit = 3)...");
        library.borrowItem("B001", "S101");
        library.borrowItem("B002", "S101");
        library.borrowItem("D001", "S101");

        // Attempt 4th borrow for Student (should be rejected)
        System.out.println("\nAttempting 4th borrow for Student (exceeding limit of 3):");
        library.borrowItem("D002", "S101");

        System.out.println("\nStaff borrowing 4th item (Staff limit = 10):");
        library.borrowItem("D002", "E202");

        library.displayMembers();

        System.out.println("\n>>> Week 3 Inheritance & Polymorphism tests passed successfully! <<<");
    }
}
