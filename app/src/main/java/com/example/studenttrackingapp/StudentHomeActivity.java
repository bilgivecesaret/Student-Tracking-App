package com.example.studenttrackingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StudentHomeActivity extends AppCompatActivity {
    Button bookProgressButton, attendanceButton, chartButton, myScheduleButton, myBooksButton;
    String studentName;
    TextView studentWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        myScheduleButton = findViewById(R.id.myScheduleButton);
        myBooksButton = findViewById(R.id.myBooksButton);
        bookProgressButton = findViewById(R.id.bookProgressButton);
        attendanceButton = findViewById(R.id.attendanceButton);
        chartButton = findViewById(R.id.chartButton);
        studentWelcome = findViewById(R.id.studentWelcome);


        studentName = getIntent().getStringExtra("student_name");
        studentName = studentWelcome.getText() + "\t" + studentName + "!";
        studentWelcome.setText(studentName);


        bookProgressButton.setOnClickListener(v ->
                startActivity(new Intent(this, BookProgressActivity.class)));

        attendanceButton.setOnClickListener(v ->
                startActivity(new Intent(this, AttendanceActivity.class)));

        chartButton.setOnClickListener(v ->
                startActivity(new Intent(this, ProgressChartActivity.class)));


        myScheduleButton.setOnClickListener(v -> {
            Intent intent = new Intent(StudentHomeActivity.this, WeeklyScheduleActivity.class);
            intent.putExtra("student_name", "Mustafa"); // Dinamik yapabilirsiniz
            startActivity(intent);
        });

        myBooksButton.setOnClickListener(v -> {
            Intent intent = new Intent(StudentHomeActivity.this, TopicsListActivity.class);
            intent.putExtra("book_name", "Math");
            startActivity(intent);
        });
    }
}

