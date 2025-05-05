/*  Created by Ugur OZKAN(21050161003) && Bahri KESKIN(22050161001) */
package com.example.studenttrackingapp.Preferences;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AssignmentPreferences {

    private static final String PREF  = "student_assignments";
    private SharedPreferences prefs;

    public AssignmentPreferences(Context ctx) {
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

    public void unassignBook(String student, String book) {
        Set<String> set = new HashSet<>(getBooksForStudent(student));
        set.remove(book);
        prefs.edit().putStringSet(student, set).apply();
    }
}
