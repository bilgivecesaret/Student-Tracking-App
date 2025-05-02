package com.example.studenttrackingapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class TopicsListActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private TopicDAO topicDAO;
    private Button addTopicButton;
    private String selectedTopic, topicToDelete, title;
    private List<String> topics;
    private static final String TAG = "TopicsListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics_list);

        listView = findViewById(R.id.topicsListView);
        addTopicButton = findViewById(R.id.addTopicButton);

        topicDAO = new TopicDAO(this);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);

        refreshTopicList();

        addTopicButton.setOnClickListener(v -> showAddTopicDialog());

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            selectedTopic = adapter.getItem(position);
            if (selectedTopic != null) {
                Intent intent = new Intent(this, TestActivity.class);
                intent.putExtra("topicTitle", selectedTopic);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener((parent, view12, position, id) -> {
            topicToDelete = adapter.getItem(position);
            if (topicToDelete != null) {
                showDeleteDialog(topicToDelete);
            }
            return true;
        });
    }

    private void refreshTopicList() {
        adapter.clear();
        topics = topicDAO.getAllTopics();
        adapter.addAll(topics);
    }

    private void showAddTopicDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New Topic");

        final EditText input = new EditText(this);
        input.setHint("Topic title");
        builder.setView(input);

        builder.setPositiveButton("Add", (dialog, which) -> {
            title = input.getText().toString().trim();
            if (!title.isEmpty()) {
                int bookId = Integer.parseInt(getIntent().getStringExtra("bookId"));
                topicDAO.addTopic(bookId,title);
                refreshTopicList();
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void showDeleteDialog(String topicTitle) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Topic")
                .setMessage("Are you sure you want to delete \"" + topicTitle + "\"?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    topicDAO.deleteTopic(topicTitle);
                    refreshTopicList();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
