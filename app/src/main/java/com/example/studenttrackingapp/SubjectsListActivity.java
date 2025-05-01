package com.example.studenttrackingapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class SubjectsListActivity extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    private String bookTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_list);

        ListView listView = findViewById(R.id.subjectsListView);
        Button addSubjectButton = findViewById(R.id.addSubjectButton);

        // Kitap başlığı alınıyor
        bookTitle = getIntent().getStringExtra("bookTitle");
        if (bookTitle == null) {
            Toast.makeText(this, "No book selected", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);

        refreshSubjects();

        addSubjectButton.setOnClickListener(v -> showAddSubjectDialog());

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedSubject = adapter.getItem(position);
            Intent intent = new Intent(SubjectsListActivity.this, CalendarActivity.class);
            intent.putExtra("bookTitle", bookTitle);
            intent.putExtra("subject", selectedSubject);
            startActivity(intent);
        });
    }

    private void refreshSubjects() {
        adapter.clear();
        List<String> subjects = DataRepository.getInstance().getTopicsForBook(bookTitle);
        adapter.addAll(subjects);
    }

    private void showAddSubjectDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New Subject");

        final EditText input = new EditText(this);
        input.setHint("Subject title");
        builder.setView(input);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String subject = input.getText().toString().trim();
            if (!subject.isEmpty()) {
                DataRepository.getInstance().addTopicToBook(bookTitle, subject);
                refreshSubjects();
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
}

