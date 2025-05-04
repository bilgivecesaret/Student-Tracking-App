package com.example.studenttrackingapp;

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
    private Button addAbsenceButton;
    private String studentName;
    private AbsencePreferences absencePreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absence);

        // Initialize views
        absenceListView = findViewById(R.id.absenceListView);
        totalAbsencesView = findViewById(R.id.totalAbsencesView);
        addAbsenceButton = findViewById(R.id.addAbsenceButton);

        // Get student name from intent
        studentName = getIntent().getStringExtra("student_name");
        absencePreferences = new AbsencePreferences(this);

        // Load and display absences
        refreshAbsenceList();

        // Set up date picker button
        addAbsenceButton.setOnClickListener(v -> showDatePicker());
    }

    private void refreshAbsenceList() {
        // Get absences and update UI
        int total = absencePreferences.getTotalAbsences(studentName);
        totalAbsencesView.setText("Total Absences: " + total);

        // Create adapter with explicit type arguments
        List<String> absencesList = new ArrayList<>(absencePreferences.getAbsences(studentName));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, absencesList);
        absenceListView.setAdapter(adapter);
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(
                this,
                (view, year, month, day) -> {
                    String date = String.format("%04d-%02d-%02d", year, month + 1, day);
                    absencePreferences.addAbsence(studentName, date);
                    refreshAbsenceList();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        ).show();
    }
}