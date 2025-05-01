package com.example.studenttrackingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class TopicsListActivity extends AppCompatActivity {

    private String bookTitle;
    private ListView topicsListView;
    private Button addTopicButton;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics_list);

        topicsListView = findViewById(R.id.topicsListView);
        addTopicButton = findViewById(R.id.addTopicButton);

        bookTitle = getIntent().getStringExtra("bookTitle");

        if (bookTitle == null || bookTitle.isEmpty()) {
            Toast.makeText(this, "Book title missing", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        topicsListView.setAdapter(adapter);

        refreshTopicList();

        addTopicButton.setOnClickListener(v -> showAddTopicDialog());

        topicsListView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedTopic = adapter.getItem(position);
            Toast.makeText(this, "Clicked on: " + selectedTopic, Toast.LENGTH_SHORT).show();
            // Gerekirse bir sonraki aktiviteye geçiş yapılabilir
        });
    }

    private void refreshTopicList() {
        adapter.clear();
        List<String> topics = DataRepository.getInstance().getTopicsForBook(bookTitle);
        adapter.addAll(topics);
    }

    private void showAddTopicDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New Topic");

        final EditText input = new EditText(this);
        input.setHint("Enter topic name");
        builder.setView(input);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String topic = input.getText().toString().trim();
            if (!topic.isEmpty()) {
                DataRepository.getInstance().addTopicToBook(bookTitle, topic);
                refreshTopicList();
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
}
