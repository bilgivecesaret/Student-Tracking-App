/*  Created by Ugur OZKAN(21050161003) && Bahri KESKIN(22050161001) */
package com.example.studenttrackingapp.Preferences;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.HashSet;
import java.util.Set;

public class AbsencePreferences {
    private static final String PREFS_NAME = "StudentAttendance";
    private static final String KEY_ABSENCES = "absences_";
    private static final String KEY_ATTENDS = "attends_";
    private final SharedPreferences sharedPreferences;

    public AbsencePreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void addAbsence(String studentName, String date) {
        Set<String> absences = getAbsences(studentName);
        absences.add(date);
        sharedPreferences.edit()
                .putStringSet(KEY_ABSENCES + studentName, absences)
                .apply();
    }

    public void addAttend(String studentName, String date) {
        Set<String> attends = getAttends(studentName);
        attends.add(date);
        sharedPreferences.edit()
                .putStringSet(KEY_ATTENDS + studentName, attends)
                .apply();
    }

    public Set<String> getAbsences(String studentName) {
        return sharedPreferences.getStringSet(KEY_ABSENCES + studentName, new HashSet<>());
    }

    public Set<String> getAttends(String studentName) {
        return sharedPreferences.getStringSet(KEY_ATTENDS + studentName, new HashSet<>());
    }

    public int getTotalAbsences(String studentName) {
        return getAbsences(studentName).size();
    }

    public int getTotalAttends(String studentName) {
        return getAttends(studentName).size();
    }
}