/*  Created by Ugur OZKAN(21050161003) && Bahri KESKIN(22050161001) */
package com.example.studenttrackingapp.Preferences;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/** 🟢  Only keeps the student's test completion information */
public class StudentProgressPreferences {

    private static final String PREF = "student_completed_tests";
    private final SharedPreferences prefs;

    public StudentProgressPreferences(Context ctx) {
        prefs = ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE);
    }

    /** We use the key "student_name::test_name" */
    private String key(String student, String test) {
        return student + "::" + test;
    }

    /* ----------  API  ---------- */

    /** Is the test marked? */
    public boolean isCompleted(String student, String test) {
        return prefs.getBoolean(key(student, test), false);
    }

    /** Mark/unmark test */
    public void setCompleted(String student, String test, boolean done) {
        prefs.edit().putBoolean(key(student, test), done).apply();
    }

    /** all test names completed by the student  */
    public List<String> getAllCompleted(String student) {
        Set<String> allKeys = prefs.getAll().keySet();
        List<String> result = new ArrayList<>();
        for (String k : allKeys)
            if (k.startsWith(student + "::") && prefs.getBoolean(k, false))
                result.add(k.split("::",2)[1]);
        return result;
    }
}
