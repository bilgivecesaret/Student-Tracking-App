package com.example.studenttrackingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

        // Giriş butonuna tıklama işlemi
        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (username.equals("teacher") && password.equals("test")) {
                // Öğretmen giriş yaparsa TeacherActivity'ye yönlendirilir
                startActivity(new Intent(LoginActivity.this, TeacherActivity.class));
            } else if (username.equals("123") && password.equals("student")) {
                // Öğrenci giriş yaparsa StudentHomeActivity'ye yönlendirilir
                startActivity(new Intent(LoginActivity.this, StudentHomeActivity.class));
            } else {
                // Hatalı giriş durumunda hata mesajı gösterilir
                errorMessageTextView.setVisibility(View.VISIBLE);
            }
        });
    }
}
