/*  Created by Ugur OZKAN(21050161003) && Bahri KESKIN(22050161001) */
package com.example.studenttrackingapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.studenttrackingapp.Preferences.AbsencePreferences;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AbsenceActivity extends AppCompatActivity {
    private ListView absenceListView;
    private TextView totalAbsencesView;
    private Button addRecordButton;
    private String studentName;
    private AbsencePreferences absencePreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absence);

        studentName = getIntent().getStringExtra("student_name");
        absencePreferences = new AbsencePreferences(this);

        absenceListView = findViewById(R.id.absenceListView);
        totalAbsencesView = findViewById(R.id.totalAbsencesView);
        addRecordButton = findViewById(R.id.addRecordButton);

        refreshAbsenceList();

        addRecordButton.setOnClickListener(v -> showDatePicker());
    }

    private void refreshAbsenceList() {
        int totalAbsences = absencePreferences.getTotalAbsences(studentName);
        int totalAttends = absencePreferences.getTotalAttends(studentName);

        totalAbsencesView.setText(String.format(
                "Total Absences: %d\nTotal Attends: %d",
                totalAbsences, totalAttends
        ));

        List<String> absenceList = new ArrayList<>(absencePreferences.getAbsences(studentName));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                absenceList
        );
        absenceListView.setAdapter(adapter);
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, day) -> {
                    String date = String.format("%02d-%02d-%04d", day, month + 1, year);
                    showAttendanceDialog(date);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.getDatePicker().setFirstDayOfWeek(Calendar.MONDAY);
        datePickerDialog.show();
    }

    private void showAttendanceDialog(String date) {
        new AlertDialog.Builder(this)
                .setTitle("Attendance Status")
                .setMessage("Was the student present or absent on " + date + "?")
                .setPositiveButton("Attend", (dialog, which) -> {
                    absencePreferences.addAttend(studentName, date);
                    refreshAbsenceList();
                })
                .setNegativeButton("Absence", (dialog, which) -> {
                    absencePreferences.addAbsence(studentName, date);
                    refreshAbsenceList();
                })
                .show();
    }
}