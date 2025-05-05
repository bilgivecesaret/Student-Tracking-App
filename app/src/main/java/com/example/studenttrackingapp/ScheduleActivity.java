package com.example.studenttrackingapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studenttrackingapp.Preferences.SchedulePreferences;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class ScheduleActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> schedules = new ArrayList<>();
    private SchedulePreferences schedulePreferences;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    private Calendar calendar = Calendar.getInstance();
    private String studentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        studentName = getIntent().getStringExtra("student_name");

        // Initialize views
        listView = findViewById(R.id.scheduleListView);
        Button addButton = findViewById(R.id.addNewScheduleButton);
        schedulePreferences = new SchedulePreferences(this);

        // Set up adapter
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, schedules);
        listView.setAdapter(adapter);

        //  Load saved schedules
        loadSchedules(studentName);

        // Add new schedule
        addButton.setOnClickListener(v -> showAddScheduleDialog());

        // Long click to manage
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            showManagementDialog(position);
            return true;
        });
    }

    private void showAddScheduleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New Study Plan");

        // Inflate custom layout
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_study_schedule, null);
        builder.setView(dialogView);

        // Get input fields
        EditText bookInput = dialogView.findViewById(R.id.editBook);
        EditText topicInput = dialogView.findViewById(R.id.editTopic);
        EditText unitInput = dialogView.findViewById(R.id.editUnit);
        EditText startDateInput = dialogView.findViewById(R.id.editStartDate);
        EditText endDateInput = dialogView.findViewById(R.id.editEndDate);

        // Set today's date as default in dd-MM-yyyy format
        String today = dateFormat.format(new Date());
        startDateInput.setText(today);
        endDateInput.setText(today);

        // Date pickers
        startDateInput.setOnClickListener(v -> showDatePicker(startDateInput));
        endDateInput.setOnClickListener(v -> showDatePicker(endDateInput));

        builder.setPositiveButton("Save", (dialog, which) -> {
            String book = bookInput.getText().toString().trim();
            String topic = topicInput.getText().toString().trim();
            String unit = unitInput.getText().toString().trim();
            String startDate = startDateInput.getText().toString().trim();
            String endDate = endDateInput.getText().toString().trim();

            if (validateInputs(book, topic, unit, startDate, endDate)) {
                String schedule = formatSchedule(book, topic, unit, startDate, endDate);
                addSchedule(schedule);
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void showDatePicker(final EditText dateField) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    calendar.set(year, month, dayOfMonth);
                    dateField.setText(dateFormat.format(calendar.getTime()));
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.getDatePicker().setFirstDayOfWeek(Calendar.MONDAY);
        datePickerDialog.show();
    }

    private boolean validateInputs(String book, String topic, String unit, String startDate, String endDate) {
        if (book.isEmpty() || topic.isEmpty() || unit.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            Date start = dateFormat.parse(startDate);
            Date end = dateFormat.parse(endDate);
            if (start.after(end)) {
                Toast.makeText(this, "End date must be after start date", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (Exception e) {
            Toast.makeText(this, "Invalid date format", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private String formatSchedule(String book, String topic, String unit, String startDate, String endDate) {
        return String.format(Locale.getDefault(),
                "Book: %s | Topic: %s | Unit: %s | Dates: %s - %s",
                book, topic, unit, startDate, endDate
        );
    }

    private void addSchedule(String schedule) {
        schedules.add(schedule);
        schedulePreferences.saveSchedule(studentName,schedules);
        adapter.notifyDataSetChanged();
        Toast.makeText(this, "Schedule added", Toast.LENGTH_SHORT).show();
    }

    private void showManagementDialog(final int position) {
        new AlertDialog.Builder(this)
                .setTitle("Manage Schedule")
                .setItems(new CharSequence[]{"Edit", "Delete"}, (dialog, which) -> {
                    if (which == 0) {
                        showEditDialog(position);
                    } else {
                        deleteSchedule(position);
                    }
                })
                .show();
    }

    private void showEditDialog(final int position) {
        String currentSchedule = schedules.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Schedule");

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_study_schedule, null);
        builder.setView(dialogView);

        // Parse current schedule
        String[] parts = currentSchedule.split("\\|");
        String book = parts[0].replace("Book:", "").trim();
        String topic = parts[1].replace("Topic:", "").trim();
        String unit = parts[2].replace("Unit:", "").trim();
        String[] dates = parts[3].replace("Dates:", "").trim().split("-");
        String startDate = dates[0].trim();
        String endDate = dates[1].trim();

        // Set current values
        EditText bookInput = dialogView.findViewById(R.id.editBook);
        EditText topicInput = dialogView.findViewById(R.id.editTopic);
        EditText unitInput = dialogView.findViewById(R.id.editUnit);
        EditText startDateInput = dialogView.findViewById(R.id.editStartDate);
        EditText endDateInput = dialogView.findViewById(R.id.editEndDate);

        bookInput.setText(book);
        topicInput.setText(topic);
        unitInput.setText(unit);
        startDateInput.setText(startDate);
        endDateInput.setText(endDate);

        // Date pickers
        startDateInput.setOnClickListener(v -> showDatePicker(startDateInput));
        endDateInput.setOnClickListener(v -> showDatePicker(endDateInput));

        builder.setPositiveButton("Update", (dialog, which) -> {
            String newBook = bookInput.getText().toString().trim();
            String newTopic = topicInput.getText().toString().trim();
            String newUnit = unitInput.getText().toString().trim();
            String newStartDate = startDateInput.getText().toString().trim();
            String newEndDate = endDateInput.getText().toString().trim();

            if (validateInputs(newBook, newTopic, newUnit, newStartDate, newEndDate)) {
                String updatedSchedule = formatSchedule(newBook, newTopic, newUnit, newStartDate, newEndDate);
                updateSchedule(position, updatedSchedule);
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void updateSchedule(int position, String updatedSchedule) {
        schedules.set(position, updatedSchedule);
        schedulePreferences.saveSchedule(studentName,schedules);
        adapter.notifyDataSetChanged();
        Toast.makeText(this, "Schedule updated", Toast.LENGTH_SHORT).show();
    }

    private void deleteSchedule(int position) {
        schedules.remove(position);
        schedulePreferences.saveSchedule(studentName,schedules);
        adapter.notifyDataSetChanged();
        Toast.makeText(this, "Schedule deleted", Toast.LENGTH_SHORT).show();
    }

    private void loadSchedules(String studentName) {
        Set<String> scheduleSet = schedulePreferences.getSchedules(studentName);
        schedules.clear();
        schedules.addAll(scheduleSet);
        adapter.notifyDataSetChanged();
    }
}