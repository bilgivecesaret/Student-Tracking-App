package com.example.studenttrackingapp;

import java.util.List;


public class Topic {
    private int id;        // Konunun ID'si
    private String name;   // Konunun ismi

    // Constructor
    public Topic(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getter ve Setter'lar
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

