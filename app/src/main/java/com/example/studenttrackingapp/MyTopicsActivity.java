package com.example.studenttrackingapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MyTopicsActivity extends AppCompatActivity {

    private ListView listView;
    private Button addTopicButton;
    private ArrayList<String> topics;
    private ArrayAdapter<String> adapter;
    private String bookName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_topics);

        listView = findViewById(R.id.myTopicListView);
        addTopicButton = findViewById(R.id.addTopicButton);
        bookName = getIntent().getStringExtra("bookName");

        topics = new ArrayList<>();
        topics.add("Numbers");
        topics.add("Sets");

        adapter = new ArrayAdapter<>(this, R.layout.list_item_topic, R.id.topicText, topics);
        listView.setAdapter(adapter);

        addTopicButton.setOnClickListener(v -> {
            EditText input = new EditText(this);
            new AlertDialog.Builder(this)
                    .setTitle("Add New Topic")
                    .setView(input)
                    .setPositiveButton("Add", (dialog, which) -> {
                        String topic = input.getText().toString().trim();
                        if (!topic.isEmpty()) {
                            topics.add(topic);
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(this, "Topic cannot be empty", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            String topic = topics.get(position);

            new AlertDialog.Builder(this)
                    .setTitle("Modify Topic")
                    .setItems(new CharSequence[]{"Edit", "Delete"}, (dialog, which) -> {
                        if (which == 0) { // Edit
                            EditText input = new EditText(this);
                            input.setText(topic);
                            new AlertDialog.Builder(this)
                                    .setTitle("Edit Topic")
                                    .setView(input)
                                    .setPositiveButton("Save", (d, w) -> {
                                        String updatedTopic = input.getText().toString().trim();
                                        if (!updatedTopic.isEmpty()) {
                                            topics.set(position, updatedTopic);
                                            adapter.notifyDataSetChanged();
                                        } else {
                                            Toast.makeText(this, "Topic cannot be empty", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .setNegativeButton("Cancel", null)
                                    .show();
                        } else if (which == 1) { // Delete
                            topics.remove(position);
                            adapter.notifyDataSetChanged();
                        }
                    })
                    .show();
            return true;
        });
    }
}
