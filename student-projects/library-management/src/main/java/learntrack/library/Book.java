package learntrack.library;

/** A book in the school library. */
public final class Book {
    private final String isbn;
    private final String title;
    private final String author;
    private String borrowedByStudentId;

    public Book(String isbn, String title, String author) {
        this.isbn = requireText(isbn, "ISBN");
        this.title = requireText(title, "Title");
        this.author = requireText(author, "Author");
    }

    private static String requireText(String value, String label) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(label + " must not be blank.");
        }
        return value.trim();
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return borrowedByStudentId == null;
    }

    public String getBorrowedByStudentId() {
        return borrowedByStudentId;
    }

    void issueTo(String studentId) {
        borrowedByStudentId = studentId;
    }

    void returnBook() {
        borrowedByStudentId = null;
    }
}
