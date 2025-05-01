package com.example.studenttrackingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button teacherButton = findViewById(R.id.teacherButton);
        Button studentButton = findViewById(R.id.studentButton);

        teacherButton.setOnClickListener(v -> {
            // Öğretmen butonuna tıklanırsa LoginActivity'ye yönlendirilir
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.putExtra("role", "teacher");
            startActivity(intent);
        });

        studentButton.setOnClickListener(v -> {
            // Öğrenci butonuna tıklanırsa LoginActivity'ye yönlendirilir
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.putExtra("role", "student");
            startActivity(intent);
        });
    }
}
