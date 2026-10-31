# Student Library Management System

A beginner friendly, menu driven Java console program for managing a small school library. It demonstrates classes, collections, input validation, and sorting.

## Features

- Add books with a title, author, and ISBN.
- Register students by ID and name.
- List books alphabetically by title or author.
- Issue an available book to a registered student.
- Return an issued book.
- View books that are currently checked out.

The program keeps its data in memory, so the sample data resets when it exits.

## Requirements

- Java 8 or later

## Run

From this directory:

```sh
javac -d out src/main/java/learntrack/library/*.java
java -cp out learntrack.library.LibraryManagementApp
```

Choose an option from the menu. The program starts with a few sample books and students so the issue and return options can be tried right away.

## How sorting works

The book list is copied and sorted with Java's `Collections.sort` and a `Comparator`. Choose title or author sorting from the menu. The library's stored list is not rearranged by displaying a sorted copy.
