package com.example.studenttrackingapp;

import java.util.List;

public class Book {
    private String title;
    private List<Topic> topics;

    public Book(String title, List<Topic> topics) {
        this.title = title;
        this.topics = topics;
    }

    public String getTitle() {
        return title;
    }

    public List<Topic> getTopics() {
        return topics;
    }
}


