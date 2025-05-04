package com.example.studenttrackingapp.Preferences;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.HashSet;
import java.util.Set;

public class AbsencePreferences {
    private static final String PREFS_NAME = "StudentAbsences";
    private final SharedPreferences sharedPreferences;

    public AbsencePreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void addAbsence(String studentName, String date) {
        Set<String> absences = getAbsences(studentName);
        absences.add(date);
        sharedPreferences.edit()
                .putStringSet(studentName, absences)
                .apply();
    }

    public Set<String> getAbsences(String studentName) {
        return sharedPreferences.getStringSet(studentName, new HashSet<>());
    }

    public int getTotalAbsences(String studentName) {
        return getAbsences(studentName).size();
    }
}