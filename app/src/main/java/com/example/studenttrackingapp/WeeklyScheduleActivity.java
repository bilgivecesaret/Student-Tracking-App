package com.example.studenttrackingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class WeeklyScheduleActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private String studentName = "Mustafa";  // You could pass this dynamically
    //private ... assignments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_schedule);

        Button addNewScheduleButton = findViewById(R.id.addNewScheduleButton);
        addNewScheduleButton.setOnClickListener(v -> {
            Intent intent = new Intent(WeeklyScheduleActivity.this, CalendarActivity.class);
            startActivityForResult(intent, 100);  // 100 is requestCode
        });


        listView = findViewById(R.id.weeklyScheduleListView);

        // assignments = .....(studentName);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        /*for (...  : assignments) {
            adapter.add("Subject: " + assignment.topic +
                    "\nBook: " + assignment.book +
                    "\nDate Range: " + assignment.dateRange);
        }*/
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            // .... assignment = assignments.get(position);

            new AlertDialog.Builder(this)
                    .setTitle("Modify Assignment")
                    .setItems(new CharSequence[]{"Edit Date", "Delete"}, (dialog, which) -> {
                        if (which == 0) { // Edit
                            EditText input = new EditText(this);
                            input.setHint("Enter new date range");
                            //input.setText(assignment.dateRange);

                            new AlertDialog.Builder(this)
                                    .setTitle("Edit Date Range")
                                    .setView(input)
                                    .setPositiveButton("Save", (d, w) -> {
                                        //assignment.dateRange = input.getText().toString();
                                        adapter.notifyDataSetChanged();
                                    })
                                    .setNegativeButton("Cancel", null)
                                    .show();

                        } else if (which == 1) { // Delete
                            //assignments.remove(position);
                            adapter.notifyDataSetChanged();
                        }
                    })
                    .show();

            return true;
        });

    }
    private void refreshAssignments() {
        //assignments = ....(studentName);

        adapter.clear();
     /*   for (... assignment : assignments) {
            adapter.add("Subject: " + assignment.topic +
                    "\nBook: " + assignment.book +
                    "\nDate Range: " + assignment.dateRange);
        }*/
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK) {
            refreshAssignments();  // custom method we'll define
        }
    }

}

