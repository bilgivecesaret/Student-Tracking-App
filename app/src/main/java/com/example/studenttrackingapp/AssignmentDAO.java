package com.example.studenttrackingapp;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AssignmentDAO {

    private static final String PREF  = "student_assignments";
    private SharedPreferences prefs;

    public AssignmentDAO(Context ctx) {
        prefs = ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE);
    }

    public void assignBook(String student, String book) {
        Set<String> set = new HashSet<>(getBooksForStudent(student));
        set.add(book);
        prefs.edit().putStringSet(student, set).apply();
    }

    public List<String> getBooksForStudent(String student) {
        return new ArrayList<>(prefs.getStringSet(student, new HashSet<>()));
    }
}
