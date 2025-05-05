/*  Created by Ugur OZKAN(21050161003) && Bahri KESKIN(22050161001) */
package com.example.studenttrackingapp;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.studenttrackingapp.Preferences.AbsencePreferences;

public class AttendanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        String studentName = getIntent().getStringExtra("student_name");
        AbsencePreferences absencePreferences = new AbsencePreferences(this);
        TextView attendanceView = findViewById(R.id.attendanceStats);

        // Get attendance data from preferences
        int attendedDays = absencePreferences.getTotalAttends(studentName);
        int absences = absencePreferences.getTotalAbsences(studentName);
        int totalDays = attendedDays + absences;
        int percentage = totalDays > 0 ? (attendedDays * 100) / totalDays : 0;

        // Update the view with dynamic data
        attendanceView.setText(
                "Total Days: " + totalDays + "\n" +
                "Attendance: " + attendedDays + "\n" +
                "Absence: " + absences + "\n" +
                "Attendance Percentage: %" + percentage
        );
    }
}