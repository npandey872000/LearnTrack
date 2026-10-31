package learntrack.library;

import java.util.List;
import java.util.Scanner;

/** Text menu for trying the student library management system. */
public final class LibraryManagementApp {
    private final Library library = new Library();
    private final Scanner scanner = new Scanner(System.in);

    private LibraryManagementApp() {
        addSampleData();
    }

    public static void main(String[] args) {
        new LibraryManagementApp().run();
    }

    private void addSampleData() {
        library.addBook(new Book("978-0143127741", "The Martian", "Andy Weir"));
        library.addBook(new Book("978-0061120084", "To Kill a Mockingbird", "Harper Lee"));
        library.addBook(new Book("978-0743273565", "The Great Gatsby", "F. Scott Fitzgerald"));
        library.registerStudent(new Student("S1001", "Avery Patel"));
        library.registerStudent(new Student("S1002", "Morgan Chen"));
    }

    private void run() {
        System.out.println("Student Library Management System");
        boolean running = true;
        while (running) {
            showMenu();
            String choice = read("Choose an option: ");
            try {
                switch (choice) {
                    case "1": listBooks(library.getBooksSortedByTitle(), "title"); break;
                    case "2": listBooks(library.getBooksSortedByAuthor(), "author"); break;
                    case "3": addBook(); break;
                    case "4": registerStudent(); break;
                    case "5": issueBook(); break;
                    case "6": returnBook(); break;
                    case "7": listBorrowedBooks(); break;
                    case "0": running = false; break;
                    default: System.out.println("Please choose a listed option.");
                }
            } catch (IllegalArgumentException exception) {
                System.out.println("Could not complete that action: " + exception.getMessage());
            }
            System.out.println();
        }
        System.out.println("Goodbye!");
    }

    private void showMenu() {
        System.out.println("1. List books by title");
        System.out.println("2. List books by author");
        System.out.println("3. Add a book");
        System.out.println("4. Register a student");
        System.out.println("5. Issue a book");
        System.out.println("6. Return a book");
        System.out.println("7. List borrowed books");
        System.out.println("0. Exit");
    }

    private void addBook() {
        library.addBook(new Book(read("ISBN: "), read("Title: "), read("Author: ")));
        System.out.println("Book added.");
    }

    private void registerStudent() {
        library.registerStudent(new Student(read("Student ID: "), read("Student name: ")));
        System.out.println("Student registered.");
    }

    private void issueBook() {
        library.issueBook(read("Book ISBN: "), read("Student ID: "));
        System.out.println("Book issued.");
    }

    private void returnBook() {
        library.returnBook(read("Book ISBN: "), read("Student ID: "));
        System.out.println("Book returned.");
    }

    private void listBorrowedBooks() {
        List<Book> borrowedBooks = library.getBorrowedBooks();
        if (borrowedBooks.isEmpty()) {
            System.out.println("No books are currently checked out.");
        } else {
            for (Book book : borrowedBooks) {
                System.out.println(book.getTitle() + " (ISBN: " + book.getIsbn()
                        + ") — student " + book.getBorrowedByStudentId());
            }
        }
    }

    private void listBooks(List<Book> books, String sortedBy) {
        System.out.println("Books sorted by " + sortedBy + ":");
        for (Book book : books) {
            String status = book.isAvailable()
                    ? "Available" : "Checked out by " + book.getBorrowedByStudentId();
            System.out.println(book.getTitle() + " — " + book.getAuthor()
                    + " (ISBN: " + book.getIsbn() + ") — " + status);
        }
    }

    private String read(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
