package com.example.studenttrackingapp;

import java.util.*;

public class DataRepository {

    private static DataRepository instance;

    private List<Book> books;
    private Map<String, List<String>> topicsByBook = new HashMap<>();
    private Map<String, List<StudentTopicAssignment>> studentTopicMap = new HashMap<>();

    private DataRepository() {
        books = new ArrayList<>();
        books.add(new Book("Math"));  // Örnek kitap
        topicsByBook.put("Math", new ArrayList<>(Arrays.asList("Numbers", "Sets")));
    }

    public static DataRepository getInstance() {
        if (instance == null) {
            instance = new DataRepository();
        }
        return instance;
    }

    // Book class
    public static class Book {
        public String title;

        public Book(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return title;
        }
    }

    // Kitapları döner
    public List<Book> getBooks() {
        return books;
    }

    // Yeni kitap ekler
    public void addBook(Book book) {
        books.add(book);
        topicsByBook.putIfAbsent(book.title, new ArrayList<>());
    }

    // Belirli bir kitaba ait konuları döner
    public List<String> getTopicsForBook(String bookName) {
        return topicsByBook.getOrDefault(bookName, new ArrayList<>());
    }

    // Belirli bir kitaba konu ekler
    public void addTopicToBook(String bookName, String topic) {
        topicsByBook.putIfAbsent(bookName, new ArrayList<>());
        topicsByBook.get(bookName).add(topic);
    }

    // Öğrenciye konu atar
    public void assignTopicToStudent(String studentName, String topic, String book, String dateRange) {
        List<StudentTopicAssignment> assignments = studentTopicMap.getOrDefault(studentName, new ArrayList<>());
        assignments.add(new StudentTopicAssignment(book, topic, dateRange));
        studentTopicMap.put(studentName, assignments);
    }

    // Öğrenciye atanmış konuları döner
    public List<StudentTopicAssignment> getAssignmentsForStudent(String studentName) {
        return studentTopicMap.getOrDefault(studentName, new ArrayList<>());
    }

    // Nested class: Öğrenciye atanan konu bilgisi
    public static class StudentTopicAssignment {
        public String book;
        public String topic;
        public String dateRange;

        public StudentTopicAssignment(String book, String topic, String dateRange) {
            this.book = book;
            this.topic = topic;
            this.dateRange = dateRange;
        }

        @Override
        public String toString() {
            return topic + " (" + book + ") - " + dateRange;
        }
    }
}
