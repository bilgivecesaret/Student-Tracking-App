/*  Created by Ugur OZKAN(21050161003) && Bahri KESKIN(22050161001) */
package com.example.studenttrackingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StudentHomeActivity extends AppCompatActivity {

    private TextView studentWelcome;
    private Button   bookProgressBtn, attendanceBtn,
            chartBtn, scheduleBtn, booksBtn;

    private String studentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        /* -------- View binding -------- */
        studentWelcome   = findViewById(R.id.studentWelcome);
        bookProgressBtn  = findViewById(R.id.bookProgressButton);
        attendanceBtn    = findViewById(R.id.attendanceButton);
        chartBtn         = findViewById(R.id.chartButton);
        scheduleBtn      = findViewById(R.id.myScheduleButton);
        booksBtn         = findViewById(R.id.myBooksButton);

        /* -------- Student Name -------- */
        studentName = getIntent().getStringExtra("student_name");
        if (studentName == null) studentName = "Mustafa";   // fallback
        studentWelcome.setText("Welcome " + studentName + "!");

        /* -------- Button listeners -------- */

        // 1) Book progress screen
        bookProgressBtn.setOnClickListener(v -> {
            Intent in = new Intent(this, BookProgressActivity.class);
            in.putExtra("student_name", studentName);
            startActivity(in);
        });

        // 2) Attendance/Absence screen
        attendanceBtn.setOnClickListener(v -> {
            Intent in = new Intent(this, AttendanceActivity.class);
            in.putExtra("student_name", studentName);
            startActivity(in);
        });

        // 3) Chart screen
        chartBtn.setOnClickListener(v -> {
            Intent in = new Intent(this, ProgressChartActivity.class);
            in.putExtra("student_name", studentName);
            startActivity(in);
        });

        // 4) Schedule screen
        scheduleBtn.setOnClickListener(v -> {
            Intent in = new Intent(this, ScheduleActivity.class);
            in.putExtra("student_name", studentName);
            startActivity(in);
        });

        // 5) Student's book/topic list (view only)
        booksBtn.setOnClickListener(v -> {
            Intent in = new Intent(this, StudentAssignedBooksActivity.class);
            in.putExtra("student_name", studentName);
            startActivity(in);
        });
    }
}
