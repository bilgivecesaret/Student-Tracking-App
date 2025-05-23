/*  Created by Ugur OZKAN(21050161003) && Bahri KESKIN(22050161001) */
package com.example.studenttrackingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

public class StudentDetailActivity extends AppCompatActivity {

    private ListView listView;
    private String[] options = {"Schedule", "Books", "Absence"};
    private String studentName;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);

        listView = findViewById(R.id.listViewStudentDetails);
        studentName = getIntent().getStringExtra("student_name");

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, options);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            switch (position) {
                case 0:
                    startScheduleActivity();
                    break;
                case 1:
                    startStudentBooksActivity();
                    break;
                case 2:
                    startAbsenceActivity();
                    break;
            }
        });
    }

    private void startScheduleActivity() {
        Intent intent = new Intent(this, ScheduleActivity.class);
        intent.putExtra("student_name", studentName);
        startActivity(intent);
    }

    private void startStudentBooksActivity() {
        Intent intent = new Intent(this, AssignBookToStudentActivity.class);
        intent.putExtra("student_name", studentName);
        startActivity(intent);
    }

    private void startAbsenceActivity() {
        Intent intent = new Intent(this, AbsenceActivity.class);
        intent.putExtra("student_name", studentName);
        startActivity(intent);
    }
}
