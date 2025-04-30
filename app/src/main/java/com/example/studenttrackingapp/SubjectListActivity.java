package com.example.studenttrackingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class SubjectListActivity extends AppCompatActivity {

    private ListView listView;
    private Button addSubjectButton;
    private ArrayList<String> subjects;
    private ArrayAdapter<String> adapter;
    private String bookName, studentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_list);

        listView = findViewById(R.id.subjectListView);
        addSubjectButton = findViewById(R.id.addSubjectButton);

        bookName = getIntent().getStringExtra("bookName");
        studentName = getIntent().getStringExtra("studentName");

        subjects = new ArrayList<>();
        subjects.add("Numbers");
        subjects.add("Sets");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, subjects);
        listView.setAdapter(adapter);

        addSubjectButton.setOnClickListener(v -> {
            EditText input = new EditText(this);
            new android.app.AlertDialog.Builder(this)
                    .setTitle("Add New Subject")
                    .setView(input)
                    .setPositiveButton("Add", (dialog, which) -> {
                        subjects.add(input.getText().toString());
                        adapter.notifyDataSetChanged();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String subject = subjects.get(position);
            Intent intent = new Intent(SubjectListActivity.this, CalendarActivity.class);
            intent.putExtra("studentName", studentName);
            intent.putExtra("scheduleType", "Weekly");
            intent.putExtra("preSubject", subject);  // Auto-fill subject field
            startActivity(intent);
        });
    }
}
