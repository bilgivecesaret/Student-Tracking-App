package com.example.studenttrackingapp;

import java.util.List;

public class Topic {
    private String name;
    private List<String> tests;

    public Topic(String name, List<String> tests) {
        this.name = name;
        this.tests = tests;
    }

    public String getName() {
        return name;
    }

    public List<String> getTests() {
        return tests;
    }
}

