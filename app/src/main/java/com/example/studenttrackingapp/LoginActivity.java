package com.example.studenttrackingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton;
    private TextView errorMessageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

            if (username.equals("teacher") && password.equals("123")) {
                // If the teacher logs in, they will be directed to TeacherActivity
                startActivity(new Intent(LoginActivity.this, TeacherActivity.class));
            } else if (username.equals("student") && password.equals("123")) {
                // If the student logs in, they will be directed to StudentHomeActivity
                startActivity(new Intent(LoginActivity.this, StudentHomeActivity.class));
            } else {
                // In case of incorrect entry, an error message is displayed.
                errorMessageTextView.setVisibility(View.VISIBLE);
            }
        });
    }
}
