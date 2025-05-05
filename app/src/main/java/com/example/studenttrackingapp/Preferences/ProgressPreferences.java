/*  Created by Ugur OZKAN(21050161003) && Bahri KESKIN(22050161001) */
package com.example.studenttrackingapp.Preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class ProgressPreferences {

    private static final String PREF = "completed_tests";
    private final SharedPreferences prefs;

    public ProgressPreferences(Context ctx) {
        prefs = ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE);
    }

    private String key(String student, String topic, String test) {
        return student + "_" + topic + "_" + test;
    }

    public boolean isCompleted(String student, String topic, String test) {
        return prefs.getBoolean(key(student, topic, test), false);
    }

    public void setCompleted(String student, String topic, String test, boolean done) {
        prefs.edit().putBoolean(key(student, topic, test), done).apply();
    }
}
