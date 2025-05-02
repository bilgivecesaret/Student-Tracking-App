package com.example.studenttrackingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        syncBooksData();

        Button teacherButton = findViewById(R.id.teacherButton);
        Button studentButton = findViewById(R.id.studentButton);

        teacherButton.setOnClickListener(v -> {
            // If you click on the teacher button, you will be directed to LoginActivity.
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.putExtra("role", "teacher");
            startActivity(intent);
        });

        studentButton.setOnClickListener(v -> {
            // If the student button is clicked, it will be directed to LoginActivity.
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.putExtra("role", "student");
            startActivity(intent);
        });

    }
    private void syncBooksData() {
        // We start the Worker to synchronize the book data.
        WorkRequest syncRequest = new OneTimeWorkRequest.Builder(BookSyncWorker.class)
                .build();

        // We start the job request with WorkManager.
        WorkManager.getInstance(this).enqueue(syncRequest);
    }

}
