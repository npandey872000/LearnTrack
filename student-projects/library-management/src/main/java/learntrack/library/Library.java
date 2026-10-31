package learntrack.library;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/** Stores books and students and applies the library's borrowing rules. */
public final class Library {
    private final List<Book> books = new ArrayList<Book>();
    private final List<Student> students = new ArrayList<Student>();

    public void addBook(Book book) {
        if (findBook(book.getIsbn()) != null) {
            throw new IllegalArgumentException("A book with that ISBN already exists.");
        }
        books.add(book);
    }

    public void registerStudent(Student student) {
        if (findStudent(student.getId()) != null) {
            throw new IllegalArgumentException("A student with that ID is already registered.");
        }
        students.add(student);
    }

    public List<Book> getBooksSortedByTitle() {
        return sortedBooks(new Comparator<Book>() {
            @Override
            public int compare(Book left, Book right) {
                return left.getTitle().compareToIgnoreCase(right.getTitle());
            }
        });
    }

    public List<Book> getBooksSortedByAuthor() {
        return sortedBooks(new Comparator<Book>() {
            @Override
            public int compare(Book left, Book right) {
                int byAuthor = left.getAuthor().compareToIgnoreCase(right.getAuthor());
                return byAuthor != 0 ? byAuthor
                        : left.getTitle().compareToIgnoreCase(right.getTitle());
            }
        });
    }

    private List<Book> sortedBooks(Comparator<Book> comparator) {
        List<Book> result = new ArrayList<Book>(books);
        Collections.sort(result, comparator);
        return result;
    }

    public List<Book> getBorrowedBooks() {
        List<Book> result = new ArrayList<Book>();
        for (Book book : books) {
            if (!book.isAvailable()) {
                result.add(book);
            }
        }
        return result;
    }

    public void issueBook(String isbn, String studentId) {
        Book book = requireBook(isbn);
        if (findStudent(studentId) == null) {
            throw new IllegalArgumentException("No student is registered with that ID.");
        }
        if (!book.isAvailable()) {
            throw new IllegalArgumentException("That book is already checked out.");
        }
        book.issueTo(studentId);
    }

    public void returnBook(String isbn, String studentId) {
        Book book = requireBook(isbn);
        if (book.isAvailable()) {
            throw new IllegalArgumentException("That book is not checked out.");
        }
        if (!book.getBorrowedByStudentId().equals(studentId)) {
            throw new IllegalArgumentException("That book is checked out to a different student.");
        }
        book.returnBook();
    }

    private Book requireBook(String isbn) {
        Book book = findBook(isbn);
        if (book == null) {
            throw new IllegalArgumentException("No book was found with that ISBN.");
        }
        return book;
    }

    private Book findBook(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equalsIgnoreCase(isbn)) {
                return book;
            }
        }
        return null;
    }

    private Student findStudent(String id) {
        for (Student student : students) {
            if (student.getId().equalsIgnoreCase(id)) {
                return student;
            }
        }
        return null;
    }
}
