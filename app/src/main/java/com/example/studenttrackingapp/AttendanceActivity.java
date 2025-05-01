package com.example.studenttrackingapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AttendanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        TextView attendanceView = findViewById(R.id.attendanceStats);

        int totalDays = 100;
        int attendedDays = 85;
        int absences = totalDays - attendedDays;
        int percentage = (attendedDays * 100) / totalDays;

        attendanceView.setText("Toplam Gün: " + totalDays +
                "\nKatılım: " + attendedDays +
                "\nDevamsızlık: " + absences +
                "\nKatılım Yüzdesi: %" + percentage);
    }
}

