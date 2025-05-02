package com.example.studenttrackingapp;

import java.util.*;


public class DataRepository {

    private static DataRepository instance;

    private List<Book> books;
    private Map<String, List<String>> topicsByBook = new HashMap<>();
    private Map<String, List<StudentTopicAssignment>> studentTopicMap = new HashMap<>();

    private DataRepository() {
        books = new ArrayList<>();
    }

    public static DataRepository getInstance() {
        if (instance == null) {
            instance = new DataRepository();
        }
        return instance;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void addBook(Book book) {
        books.add(book);
        topicsByBook.putIfAbsent(book.getTitle(), new ArrayList<>());
    }

    public void removeBook(int index) {
        if (index >= 0 && index < books.size()) {
            Book removedBook = books.remove(index);
            topicsByBook.remove(removedBook.getTitle());
        }
    }


    public List<String> getTopicsForBook(String bookName) {
        return topicsByBook.getOrDefault(bookName, Collections.emptyList());
    }

    public void addTopicToBook(String bookName, String topic) {
        topicsByBook.putIfAbsent(bookName, new ArrayList<>());
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
