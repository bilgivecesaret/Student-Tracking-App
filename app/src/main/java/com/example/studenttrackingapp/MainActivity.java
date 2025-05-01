package com.example.studenttrackingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        syncBooksData();

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
    private void syncBooksData() {
        // Kitap verilerini senkronize etmek için Worker'ı başlatıyoruz.
        WorkRequest syncRequest = new OneTimeWorkRequest.Builder(BookSyncWorker.class)
                .build();

        // WorkManager ile iş talebini başlatıyoruz.
        WorkManager.getInstance(this).enqueue(syncRequest);
    }

}
