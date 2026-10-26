# LearnTrack

LearnTrack is a Core Java console application for maintaining student records, courses, and course enrollments. It uses in-memory repositories, so data lasts only for the current run.

## Requirements

- Java Development Kit (JDK) 8 or newer

## Run

From the repository root, compile the source files and run the application:

```sh
mkdir -p out
javac -d out $(find src -name '*.java')
java -cp out com.airtribe.learntrack.Main
```

On Windows PowerShell, compile with `javac -d out (Get-ChildItem -Recurse src -Filter *.java | ForEach-Object FullName)` and run with `java -cp out com.airtribe.learntrack.Main`.

## Features

- Add and list students.
- Add and list courses, including capacity.
- Enroll a student in a course, while preventing duplicate enrollments and full courses.
- List enrollments and withdraw a student.
- Validate entered values and report missing records with clear errors.

## Class diagram

```mermaid
classDiagram
    class Person {
        <<abstract>>
        -int id
        -String name
        -String email
    }
    class Student {
        -String studentNumber
    }
    class Course {
        -String title
        -int capacity
        -CourseStatus status
    }
    class Enrollment {
        -int id
        -Student student
        -Course course
        -EnrollmentStatus status
    }
    class StudentRepository
    class CourseRepository
    class EnrollmentRepository
    class StudentService
    class CourseService
    class EnrollmentService
    class Main
    Person <|-- Student
    Student "1" --> "0..*" Enrollment
    Course "1" --> "0..*" Enrollment
    StudentService --> StudentRepository
    CourseService --> CourseRepository
    EnrollmentService --> EnrollmentRepository
    EnrollmentService --> StudentService
    EnrollmentService --> CourseService
    Main --> StudentService
    Main --> CourseService
    Main --> EnrollmentService
```

## Project layout

```text
src/com/airtribe/learntrack/
├── Main.java
├── entity/       Person, Student, Course, Enrollment
├── repository/   In-memory repositories
├── service/      Application rules
├── exception/    Domain exceptions
├── util/         ID generation and input validation
└── enums/        Course and enrollment states
```

## Notes

This project is a simple learning example. Replace the in-memory repositories with persistent storage if records need to survive between runs.
