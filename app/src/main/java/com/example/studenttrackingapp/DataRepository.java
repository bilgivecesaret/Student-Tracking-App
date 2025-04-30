package com.example.studenttrackingapp;

import java.util.*;

public class DataRepository {

    private static DataRepository instance;

    private List<String> books = new ArrayList<>();
    private Map<String, List<String>> topicsByBook = new HashMap<>();
    private Map<String, List<StudentTopicAssignment>> studentTopicMap = new HashMap<>();

    public static DataRepository getInstance() {
        if (instance == null) {
            instance = new DataRepository();
        }
        return instance;
    }

    public List<String> getBooks() {
        return books;
    }

    public void addBook(String bookName) {
        books.add(bookName);
        topicsByBook.putIfAbsent(bookName, new ArrayList<>());
    }

    public List<String> getTopicsForBook(String bookName) {
        return topicsByBook.getOrDefault(bookName, new ArrayList<>());
    }

    public void addTopicToBook(String bookName, String topic) {
        topicsByBook.get(bookName).add(topic);
    }

    public void assignTopicToStudent(String studentName, String topic, String book, String dateRange) {
        List<StudentTopicAssignment> assignments = studentTopicMap.getOrDefault(studentName, new ArrayList<>());
        assignments.add(new StudentTopicAssignment(book, topic, dateRange));
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

