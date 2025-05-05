/*  Created by Ugur OZKAN(21050161003) && Bahri KESKIN(22050161001) */
package com.example.studenttrackingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.example.studenttrackingapp.DAO.StudentDAO;
import com.example.studenttrackingapp.Preferences.UserPreferences;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton;
    private TextView errorMessageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        syncBooksData();

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        errorMessageTextView = findViewById(R.id.errorMessageTextView);

        // Hide error message initially
        errorMessageTextView.setVisibility(View.INVISIBLE);

        // Clicking on the login button
        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            StudentDAO studentDAO = new StudentDAO(LoginActivity.this);

            if (username.equals("teacher") && password.equals("123")) {
                Intent intent = new Intent(LoginActivity.this, TeacherActivity.class);
                intent.putExtra("teacher_name", "Kemal");
                startActivity(intent);
            } else if (studentDAO.validateStudentLogin(username, password)) {
                // If login is successful, save session information
                UserPreferences.setUsername(LoginActivity.this, username);
                UserPreferences.setLoggedIn(LoginActivity.this, true);

                Intent intent = new Intent(LoginActivity.this, StudentHomeActivity.class);
                String name = studentDAO.getStudentName(username);
                intent.putExtra("student_name", name);
                startActivity(intent);
            } else {
                errorMessageTextView.setVisibility(View.VISIBLE);
            }
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
