package com.example.studenttrackingapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity {

    private EditText subjectEditText, startDateEditText, endDateEditText;
    private Button saveButton;
    private Calendar calendar;
    private String studentName, scheduleType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        String preSubject = getIntent().getStringExtra("preSubject");
        if (preSubject != null) {
            subjectEditText.setText(preSubject);
        }

        subjectEditText = findViewById(R.id.editTextSubject);
        startDateEditText = findViewById(R.id.editTextStartDate);
        endDateEditText = findViewById(R.id.editTextEndDate);
        saveButton = findViewById(R.id.saveScheduleButton);

        calendar = Calendar.getInstance();

        studentName = getIntent().getStringExtra("studentName");
        scheduleType = getIntent().getStringExtra("scheduleType");

        startDateEditText.setOnClickListener(v -> showDatePicker(startDateEditText));
        endDateEditText.setOnClickListener(v -> showDatePicker(endDateEditText));

        saveButton.setOnClickListener(v -> {
            String subject = subjectEditText.getText().toString();
            String startDate = startDateEditText.getText().toString();
            String endDate = endDateEditText.getText().toString();

            String dateRange = startDate + " to " + endDate;

            // After selecting topic "Numbers" from book "Math" for "Mustafa"
            String selectedTopic = "Numbers";
            String selectedBook = "Math";
            dateRange = "2025-05-01 to 2025-05-07";

            DataRepository.getInstance().assignTopicToStudent("Mustafa", selectedTopic, selectedBook, dateRange, true);


            // Pass back to ScheduleListActivity
            Intent resultIntent = new Intent();
            resultIntent.putExtra("subject", subject);
            resultIntent.putExtra("dateRange", dateRange);
            resultIntent.putExtra("completion", "0%"); // Default
            setResult(RESULT_OK);
            finish();
        });
    }

    private void showDatePicker(final EditText targetEditText) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (DatePicker view, int selectedYear, int selectedMonth, int selectedDay) -> {
                    String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    targetEditText.setText(selectedDate);
                }, year, month, day);

        datePickerDialog.show();
    }
}

