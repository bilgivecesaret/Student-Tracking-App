package com.example.studenttrackingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StudentHomeActivity extends AppCompatActivity {

    /* Ekran öğeleri */
    private TextView studentWelcome;
    private Button   bookProgressBtn, attendanceBtn,
            chartBtn, scheduleBtn, booksBtn;

    /* Veriler */
    private String studentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        /* -------- View bağlama -------- */
        studentWelcome   = findViewById(R.id.studentWelcome);
        bookProgressBtn  = findViewById(R.id.bookProgressButton);
        attendanceBtn    = findViewById(R.id.attendanceButton);
        chartBtn         = findViewById(R.id.chartButton);
        scheduleBtn      = findViewById(R.id.myScheduleButton);
        booksBtn         = findViewById(R.id.myBooksButton);

        /* -------- Öğrenci adı -------- */
        studentName = getIntent().getStringExtra("student_name");
        if (studentName == null) studentName = "Mustafa";   // fallback
        studentWelcome.setText("Welcome " + studentName + "!");

        /* -------- Buton dinleyicileri -------- */

        // 1) Kitap ilerleme ekranı
        bookProgressBtn.setOnClickListener(v -> {
            Intent in = new Intent(this, BookProgressActivity.class);
            in.putExtra("student_name", studentName);
            startActivity(in);
        });

        // 2) Devam/İstatistik örnek butonları
        attendanceBtn.setOnClickListener(v -> {
            Intent in = new Intent(this, AttendanceActivity.class);
            in.putExtra("student_name", studentName);
            startActivity(in);
        });

        chartBtn.setOnClickListener(v -> {
            Intent in = new Intent(this, ProgressChartActivity.class);
            in.putExtra("student_name", studentName);
            startActivity(in);
        });

        // 3) Kişisel haftalık program
        scheduleBtn.setOnClickListener(v -> {
            Intent in = new Intent(this, ScheduleActivity.class);
            in.putExtra("student_name", studentName);
            startActivity(in);
        });

        // 4) Öğrencinin kitap/konu listesi (yalnızca görüntüleme)
        booksBtn.setOnClickListener(v -> {
            Intent in = new Intent(this, StudentAssignedBooksActivity.class);
            in.putExtra("student_name", studentName);
            startActivity(in);
        });
    }
}
