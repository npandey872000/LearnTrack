package com.airtribe.learntrack;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/** Console entry point for the LearnTrack student and course manager. */
public class Main {
    private final Scanner input = new Scanner(System.in);
    private final StudentService students = new StudentService();
    private final CourseService courses = new CourseService();
    private final EnrollmentService enrollments = new EnrollmentService(students, courses);

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        System.out.println("LearnTrack - Student and Course Management");
        boolean running = true;
        while (running) {
            System.out.println("\n1 Add student  2 List students  3 Add course  4 List courses");
            System.out.println("5 Enroll student  6 List enrollments  7 Withdraw  0 Exit");
            String choice = prompt("Choose: " );
            try {
                switch (choice) {
                    case "1": addStudent(); break;
                    case "2": students.all().forEach(System.out::println); break;
                    case "3": addCourse(); break;
                    case "4": courses.all().forEach(System.out::println); break;
                    case "5": enroll(); break;
                    case "6": enrollments.all().forEach(System.out::println); break;
                    case "7": enrollments.withdraw(readInt("Enrollment ID: ")); break;
                    case "0": running = false; break;
                    default: System.out.println("Choose one of the listed options.");
                }
            } catch (IllegalArgumentException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
        System.out.println("Goodbye.");
    }

    private void addStudent() {
        String name = prompt("Name: " );
        String email = prompt("Email: " );
        Student student = students.add(name, email);
        System.out.println("Added " + student);
    }

    private void addCourse() {
        String title = prompt("Course title: " );
        int capacity = readInt("Capacity: " );
        System.out.println("Added " + courses.add(title, capacity));
    }

    private void enroll() {
        int studentId = readInt("Student ID: " );
        int courseId = readInt("Course ID: " );
        System.out.println("Enrolled: " + enrollments.enroll(studentId, courseId));
    }

    private int readInt(String label) {
        try { return Integer.parseInt(prompt(label)); }
        catch (NumberFormatException ex) { throw new IllegalArgumentException("Enter a whole number."); }
    }

    private String prompt(String label) {
        System.out.print(label);
        return input.nextLine().trim();
    }

    public abstract static class Person {
        private final int id;
        private final String name;
        private final String email;
        protected Person(int id, String name, String email) {
            this.id = id; this.name = required(name, "Name"); this.email = validEmail(email);
        }
        public int getId() { return id; }
        public String getName() { return name; }
        public String getEmail() { return email; }
        @Override public String toString() { return id + " - " + name + " (" + email + ")"; }
    }

    public static final class Student extends Person {
        private final String studentNumber;
        private Student(int id, String name, String email) { super(id, name, email); this.studentNumber = "STU-" + id; }
        @Override public String toString() { return super.toString() + " [" + studentNumber + "]"; }
    }

    public static final class Course {
        private final int id; private final String title; private final int capacity;
        private CourseStatus status = CourseStatus.OPEN;
        private Course(int id, String title, int capacity) {
            this.id = id; this.title = required(title, "Course title");
            if (capacity < 1) throw new IllegalArgumentException("Capacity must be at least 1.");
            this.capacity = capacity;
        }
        public int getId() { return id; }
        public int getCapacity() { return capacity; }
        public CourseStatus getStatus() { return status; }
        private void closeIfFull(int active) { status = active >= capacity ? CourseStatus.FULL : CourseStatus.OPEN; }
        @Override public String toString() { return id + " - " + title + " (" + status + ", capacity " + capacity + ")"; }
    }

    public enum CourseStatus { OPEN, FULL }
    public enum EnrollmentStatus { ACTIVE, WITHDRAWN }

    public static final class Enrollment {
        private final int id; private final Student student; private final Course course;
        private EnrollmentStatus status = EnrollmentStatus.ACTIVE;
        private Enrollment(int id, Student student, Course course) { this.id = id; this.student = student; this.course = course; }
        public int getId() { return id; }
        public void withdraw() { status = EnrollmentStatus.WITHDRAWN; }
        public boolean isActive() { return status == EnrollmentStatus.ACTIVE; }
        @Override public String toString() { return id + " - " + student.getName() + " / course " + course.getId() + " (" + status + ")"; }
    }

    public static final class StudentRepository {
        private final Map<Integer, Student> data = new LinkedHashMap<Integer, Student>();
        public void save(Student student) { data.put(student.getId(), student); }
        public Student find(int id) { Student value = data.get(id); if (value == null) throw new IllegalArgumentException("Student " + id + " was not found."); return value; }
        public List<Student> all() { return new ArrayList<Student>(data.values()); }
    }

    public static final class CourseRepository {
        private final Map<Integer, Course> data = new LinkedHashMap<Integer, Course>();
        public void save(Course course) { data.put(course.getId(), course); }
        public Course find(int id) { Course value = data.get(id); if (value == null) throw new IllegalArgumentException("Course " + id + " was not found."); return value; }
        public List<Course> all() { return new ArrayList<Course>(data.values()); }
    }

    public static final class EnrollmentRepository {
        private final Map<Integer, Enrollment> data = new LinkedHashMap<Integer, Enrollment>();
        public void save(Enrollment enrollment) { data.put(enrollment.getId(), enrollment); }
        public Enrollment find(int id) { Enrollment value = data.get(id); if (value == null) throw new IllegalArgumentException("Enrollment " + id + " was not found."); return value; }
        public List<Enrollment> all() { return new ArrayList<Enrollment>(data.values()); }
        public boolean hasActive(int studentId, int courseId) {
            for (Enrollment e : data.values()) if (e.isActive() && e.student.getId() == studentId && e.course.getId() == courseId) return true;
            return false;
        }
        public int countForCourse(int courseId) {
            int count = 0; for (Enrollment e : data.values()) if (e.isActive() && e.course.getId() == courseId) count++; return count;
        }
    }

    public static final class StudentService {
        private final StudentRepository repository = new StudentRepository();
        private int nextId = 1;
        public Student add(String name, String email) { Student s = new Student(nextId++, name, email); repository.save(s); return s; }
        public Student get(int id) { return repository.find(id); }
        public List<Student> all() { return repository.all(); }
    }

    public static final class CourseService {
        private final CourseRepository repository = new CourseRepository();
        private int nextId = 1;
        public Course add(String title, int capacity) { Course c = new Course(nextId++, title, capacity); repository.save(c); return c; }
        public Course get(int id) { return repository.find(id); }
        public List<Course> all() { return repository.all(); }
    }

    public static final class EnrollmentService {
        private final StudentService students; private final CourseService courses;
        private final EnrollmentRepository repository = new EnrollmentRepository(); private int nextId = 1;
        private EnrollmentService(StudentService students, CourseService courses) { this.students = students; this.courses = courses; }
        public Enrollment enroll(int studentId, int courseId) {
            Student student = students.get(studentId); Course course = courses.get(courseId);
            if (repository.hasActive(studentId, courseId)) throw new IllegalArgumentException("Student is already enrolled in that course.");
            if (repository.countForCourse(courseId) >= course.getCapacity()) throw new IllegalArgumentException("Course is full.");
            Enrollment e = new Enrollment(nextId++, student, course); repository.save(e);
            course.closeIfFull(repository.countForCourse(courseId)); return e;
        }
        public void withdraw(int id) { Enrollment e = repository.find(id); e.withdraw(); e.course.closeIfFull(repository.countForCourse(e.course.getId())); System.out.println("Enrollment withdrawn."); }
        public List<Enrollment> all() { return repository.all(); }
    }

    private static String required(String value, String label) {
        if (value == null || value.trim().isEmpty()) throw new IllegalArgumentException(label + " cannot be empty.");
        return value.trim();
    }
    private static String validEmail(String value) {
        String email = required(value, "Email");
        if (!email.matches("[^\\s@]+@[^\\s@]+\\.[^\\s@]+")) throw new IllegalArgumentException("Enter a valid email address.");
        return email;
    }
}
