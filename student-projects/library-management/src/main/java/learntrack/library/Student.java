package learntrack.library;

/** A student who may borrow books. */
public final class Student {
    private final String id;
    private final String name;

    public Student(String id, String name) {
        this.id = requireText(id, "Student ID");
        this.name = requireText(name, "Student name");
    }

    private static String requireText(String value, String label) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(label + " must not be blank.");
        }
        return value.trim();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
