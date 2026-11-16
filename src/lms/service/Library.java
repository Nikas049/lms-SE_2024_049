package lms.service;

import lms.model.Book;
import lms.model.Member;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Service container representing the Library.
 * Uses composition to hold and manage collections of Book and Member objects.
 */
public class Library {
    private final List<Book> books;
    private final List<Member> members;

    public Library() {
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
    }

    public void addBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        books.add(book);
    }

    public void addMember(Member member) {
        if (member == null) {
            throw new IllegalArgumentException("Member cannot be null");
        }
        members.add(member);
    }

    public Book findBookByIsbn(String isbn) {
        if (isbn == null || isbn.isBlank()) {
            return null;
        }
        for (Book book : books) {
            if (book.getIsbn().equalsIgnoreCase(isbn.trim())) {
                return book;
            }
        }
        return null;
    }

    public Member findMemberById(String memberId) {
        if (memberId == null || memberId.isBlank()) {
            return null;
        }
        for (Member member : members) {
            if (member.getMemberId().equalsIgnoreCase(memberId.trim())) {
                return member;
            }
        }
        return null;
    }

    public boolean borrowBook(String isbn, String memberId) {
        Book book = findBookByIsbn(isbn);
        if (book == null) {
            System.out.println("[Borrow Failed] Book with ISBN " + isbn + " not found.");
            return false;
        }
        if (book.isBorrowed()) {
            System.out.println("[Borrow Failed] Book \"" + book.getTitle() + "\" is already borrowed.");
            return false;
        }
        Member member = findMemberById(memberId);
        if (member == null) {
            System.out.println("[Borrow Failed] Member with ID " + memberId + " not found.");
            return false;
        }

        book.setBorrowed(true);
        member.incrementBorrowedCount();
        System.out.println("[Borrow Success] \"" + book.getTitle() + "\" loaned to " + member.getName() + ".");
        return true;
    }

    public boolean returnBook(String isbn, String memberId) {
        Book book = findBookByIsbn(isbn);
        if (book == null) {
            System.out.println("[Return Failed] Book with ISBN " + isbn + " not found.");
            return false;
        }
        if (!book.isBorrowed()) {
            System.out.println("[Return Failed] Book \"" + book.getTitle() + "\" is not currently borrowed.");
            return false;
        }
        Member member = findMemberById(memberId);
        if (member == null) {
            System.out.println("[Return Failed] Member with ID " + memberId + " not found.");
            return false;
        }

        book.setBorrowed(false);
        member.decrementBorrowedCount();
        System.out.println("[Return Success] \"" + book.getTitle() + "\" returned by " + member.getName() + ".");
        return true;
    }

    public void displayBooks() {
        System.out.println("\n--- Current Library Catalog (" + books.size() + " books) ---");
        for (Book book : books) {
            System.out.println("  " + book);
        }
    }

    public void displayMembers() {
        System.out.println("\n--- Registered Members (" + members.size() + " members) ---");
        for (Member member : members) {
            System.out.println("  " + member);
        }
    }

    public List<Book> getBooks() {
        return Collections.unmodifiableList(books);
    }

    public List<Member> getMembers() {
        return Collections.unmodifiableList(members);
    }
}
