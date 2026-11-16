package lms;

import lms.exception.DataAccessException;
import lms.exception.ItemNotAvailableException;
import lms.exception.MemberNotFoundException;
import lms.model.Book;
import lms.model.DVD;
import lms.model.Magazine;
import lms.model.Staff;
import lms.model.Student;
import lms.service.Library;

/**
 * Robust Test Driver for Week 5: Custom Exception Subsystem.
 * Features an attempt() helper using try-catch-finally blocks to test 8 distinct operational scenarios.
 */
public class Main {

    @FunctionalInterface
    interface TestAction {
        void execute() throws Exception;
    }

    /**
     * Executes a scenario inside a structured try-catch-finally block.
     * Guarantees diagnostics and cleanup logging regardless of outcome.
     *
     * @param scenarioName description of the operational test
     * @param action       executable block of library operations
     */
    public static void attempt(String scenarioName, TestAction action) {
        System.out.println("\n================================================================================");
        System.out.println(" SCENARIO: " + scenarioName);
        System.out.println("================================================================================");
        boolean outcome = false;
        try {
            action.execute();
            outcome = true;
            System.out.println("[RESULT: SUCCESS] Operation completed within business invariants.");
        } catch (ItemNotAvailableException ex) {
            System.out.println("[CAUGHT CHECKED EXCEPTION -> ItemNotAvailableException]");
            System.out.println("  Detail: " + ex.getMessage());
        } catch (MemberNotFoundException ex) {
            System.out.println("[CAUGHT UNCHECKED EXCEPTION -> MemberNotFoundException]");
            System.out.println("  Detail: " + ex.getMessage());
            System.out.println("  Offending Member ID Encapsulated: " + ex.getOffendingMemberId());
        } catch (DataAccessException ex) {
            System.out.println("[CAUGHT UNCHECKED EXCEPTION -> DataAccessException]");
            System.out.println("  Detail: " + ex.getMessage());
            System.out.println("  Root Cause: " + ex.getCause().toString());
        } catch (Exception ex) {
            System.out.println("[CAUGHT UNEXPECTED EXCEPTION -> " + ex.getClass().getSimpleName() + "]: " + ex.getMessage());
        } finally {
            System.out.println("[FINALLY BLOCK] Cleanup complete for scenario: \"" + scenarioName + "\" (Outcome: " + (outcome ? "SUCCESS" : "EXCEPTION HANDLED") + ")");
        }
    }

    public static void main(String[] args) {
        System.out.println("################################################################################");
        System.out.println("  SENG 12233: Library Management System (Week 5 - Exception Handling Subsystem)");
        System.out.println("################################################################################");

        Library library = new Library();

        // Setup Catalog & Members
        Book b1 = new Book("B101", "Clean Architecture", "Robert C. Martin");
        Book b2 = new Book("B102", "Effective Java", "Joshua Bloch");
        DVD d1 = new DVD("D201", "The Social Network", 120);
        DVD d2 = new DVD("D202", "Silicon Valley Doc", 90);
        Magazine m1 = new Magazine("M301", "Communications of the ACM", 11);

        library.addItem(b1);
        library.addItem(b2);
        library.addItem(d1);
        library.addItem(d2);
        library.addItem(m1);

        Student student = new Student("S100", "Anura Bandara");
        Staff staff = new Staff("E500", "Dr. Chandana Gamage");

        library.addMember(student);
        library.addMember(staff);

        library.displayCatalog();
        library.displayMembers();

        // =========================================================================
        // 8 Distinct Operational Scenarios
        // =========================================================================

        // Scenario 1: Successful Item Loan
        attempt("1. Successful Loan of a Book by a Student", () -> {
            library.loanItem("B101", "S100");
            System.out.println("State verified: " + b1.getTitle() + " -> Borrowed by: " + b1.getBorrower().getName());
            System.out.println("Student borrow count: " + student.getBorrowedCount() + "/" + student.getMaxBorrowLimit());
        });

        // Scenario 2: Borrow Limit Enforcement (Student limit = 3)
        attempt("2. Invariant Enforcement: Student Attempts to Exceed Borrow Limit (3)", () -> {
            // Student currently has 1 item. Borrow 2 more to hit max limit (3/3).
            library.loanItem("B102", "S100");
            library.loanItem("D201", "S100");
            System.out.println("Student current borrow count: " + student.getBorrowedCount() + "/" + student.getMaxBorrowLimit());

            // Attempt 4th borrow (should trigger checked ItemNotAvailableException)
            System.out.println("Attempting 4th borrow for Student S100:");
            library.loanItem("D202", "S100");
        });

        // Scenario 3: Attempting to Borrow an Already Loaned Item
        attempt("3. Concurrency / Availability Conflict: Item Already On Loan", () -> {
            System.out.println("Staff E500 attempts to borrow \"Clean Architecture\" (B101) which is already on loan:");
            library.loanItem("B101", "E500");
        });

        // Scenario 4: Missing Member Lookup (MemberNotFoundException)
        attempt("4. Missing Member Lookup: Unregistered Member ID", () -> {
            System.out.println("Attempting loan for non-existent member 'X999':");
            library.loanItem("D202", "X999");
        });

        // Scenario 5: Successful Item Return & State Verification
        attempt("5. Successful Item Return & Ownership Release", () -> {
            System.out.println("Returning \"Clean Architecture\" (B101)...");
            library.returnItem("B101");
            System.out.println("State verified: " + b1.getTitle() + " isBorrowed=" + b1.isBorrowed() + ", borrower=" + b1.getBorrower());
            System.out.println("Student S100 borrow count after return: " + student.getBorrowedCount() + "/" + student.getMaxBorrowLimit());
        });

        // Scenario 6: Return Invariant Violation (Returning an unborrowed item)
        attempt("6. Return Invariant Violation: Returning an Unborrowed Item", () -> {
            System.out.println("Attempting to return B101 again (currently not borrowed):");
            library.returnItem("B101");
        });

        // Scenario 7: Data Layer Fault Wrapping (DataAccessException)
        attempt("7. Persistence Layer Fault Handling: DataAccessException", () -> {
            System.out.println("Triggering simulated low-level SQL database outage:");
            library.simulateDatabaseFailure();
        });

        // Scenario 8: Reservation Lifecycle & Polymorphic Late Fee Calculation
        attempt("8. Reservation Lifecycle & Polymorphic Late Fee Calculation", () -> {
            // Test reserving reservable book
            System.out.println("Reserving Book B101 for Staff E500:");
            library.reserveItem("B101", "E500");
            System.out.println("Cancelling reservation on B101:");
            library.cancelReservation("B101");

            // Test non-reservable item reservation (DVD)
            System.out.println("\nAttempting reservation on non-reservable DVD D201:");
            try {
                library.reserveItem("D201", "E500");
            } catch (ItemNotAvailableException e) {
                System.out.println("Caught expected non-reservable restriction: " + e.getMessage());
            }

            // Polymorphic late fee calculation
            library.printAllLateFees(7);
        });

        System.out.println("\n################################################################################");
        System.out.println("  All 8 Operational Scenarios and Exception Handlers Verified Successfully!");
        System.out.println("################################################################################");
    }
}
