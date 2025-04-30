package com.example.studenttrackingapp;

import android.os.Bundle;
import android.widget.*;

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

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, topics);
        listView.setAdapter(adapter);

        addTopicButton.setOnClickListener(v -> {
            EditText input = new EditText(this);
            new android.app.AlertDialog.Builder(this)
                    .setTitle("Add New Topic")
                    .setView(input)
                    .setPositiveButton("Add", (dialog, which) -> {
                        topics.add(input.getText().toString());
                        adapter.notifyDataSetChanged();
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
                                        topics.set(position, input.getText().toString());
                                        adapter.notifyDataSetChanged();
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

