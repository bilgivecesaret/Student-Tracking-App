/*  Created by Ugur OZKAN(21050161003) && Bahri KESKIN(22050161001) */
package com.example.studenttrackingapp.Preferences;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SchedulePreferences {
    private static final String PREFS_NAME = "StudySchedules";
    private static final String SCHEDULE_KEY = "study_items_";
    private final SharedPreferences sharedPreferences;

    public SchedulePreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public Set<String> getSchedules(String studentName) {
        return sharedPreferences.getStringSet(SCHEDULE_KEY + studentName, new HashSet<>());
    }

    public void saveSchedule(String studentName, List<String> scheduleList) {
        Set<String> scheduleSet = new HashSet<>(scheduleList);
        sharedPreferences.edit()
                .putStringSet(SCHEDULE_KEY + studentName, scheduleSet)
                .apply();
    }

}
