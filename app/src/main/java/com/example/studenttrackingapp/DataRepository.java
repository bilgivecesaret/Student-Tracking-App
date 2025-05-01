package com.example.studenttrackingapp;

import java.util.*;

public class DataRepository {

    private static DataRepository instance;

    private List<Book> books;
    private Map<String, List<String>> topicsByBook = new HashMap<>();
    private Map<String, List<StudentTopicAssignment>> studentTopicMap = new HashMap<>();

    private DataRepository() {
        books = new ArrayList<>();

        // Örnek kitaplar (isteğe bağlı)
        books.add(new Book("Math", "Author Name"));  // Pass both title and author

    }

    public static DataRepository getInstance() {
        if (instance == null) {
            instance = new DataRepository();
        }
        return instance;
    }


    public static class Book {  // Make Book static
        private String title;
        private String author;

        // Constructor with two parameters
        public Book(String title, String author) {
            this.title = title;
            this.author = author;
        }

        // Getter for title
        public String getTitle() {
            return title;
        }

        // Getter for author
        public String getAuthor() {
            return author;
        }
    }





    public List<Book> getBooks() {
        List<Book> books = new ArrayList<>();
        DataRepository.Book newBook = new DataRepository.Book("Math", "Author Name");
        books.add(newBook);
        return books;
    }

    public void addBook(Book book) {
        books.add(book);
        topicsByBook.putIfAbsent(book.title, new ArrayList<>());
    }

    public void removeBook(int index) {
        if (index >= 0 && index < books.size()) {
            books.remove(index);
        }
    }


    public List<String> getTopicsForBook(String bookName) {
        return topicsByBook.getOrDefault(bookName, new ArrayList<>());
    }

    public void addTopicToBook(String bookName, String topic) {
        topicsByBook.get(bookName).add(topic);
    }

    public void assignTopicToStudent(String studentName, String topic, String book, String dateRange, boolean isCompleted) {
        List<StudentTopicAssignment> assignments = studentTopicMap.getOrDefault(studentName, new ArrayList<>());
        assignments.add(new StudentTopicAssignment(book, topic, dateRange, isCompleted));
        studentTopicMap.put(studentName, assignments);
    }

    public List<StudentTopicAssignment> getAssignmentsForStudent(String studentName) {
        return studentTopicMap.getOrDefault(studentName, new ArrayList<>());
    }

    // Nested class to hold assignment info
    public static class StudentTopicAssignment {
        public String book;
        public String topic;
        public String dateRange;
        public boolean isCompleted;

        public StudentTopicAssignment(String book, String topic, String dateRange, boolean isCompleted) {
            this.book = book;
            this.topic = topic;
            this.dateRange = dateRange;
            this.isCompleted = isCompleted;
        }

        @Override
        public String toString() {
            return topic + " (" + book + ") - " + dateRange + (isCompleted ? " ✓" : " ✗");
        }
    }
}
