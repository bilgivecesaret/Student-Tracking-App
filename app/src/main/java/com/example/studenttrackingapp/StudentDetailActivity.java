package com.example.studenttrackingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

public class StudentDetailActivity extends AppCompatActivity {

    private ListView listView;
    private String[] options = {"Yearly Schedule", "Weekly Schedule", "Books", "Absence"};
    private String studentName;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);

        listView = findViewById(R.id.listViewStudentDetails);
        studentName = getIntent().getStringExtra("studentName");

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, options);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            switch (position) {
                case 0:
                    startScheduleActivity("Yearly");
                    break;
                case 1:
                    startScheduleActivity("Weekly");
                    break;
                case 2:
                    startStudentBooksActivity();
                    break;
                case 3:
                    startAbsenceActivity();
                    break;
            }
        });
    }

    private void startScheduleActivity(String type) {
        Intent intent = new Intent(this, ScheduleListActivity.class);
        intent.putExtra("scheduleType", type);
        intent.putExtra("studentName", studentName);
        startActivity(intent);
    }

    private void startStudentBooksActivity() {
        Intent intent = new Intent(this, BookListActivity.class);
        intent.putExtra("owner", "student");
        intent.putExtra("studentName", studentName);
        startActivity(intent);
    }

    private void startAbsenceActivity() {
        Intent intent = new Intent(this, AbsenceActivity.class);
        intent.putExtra("studentName", studentName);
        startActivity(intent);
    }
}
