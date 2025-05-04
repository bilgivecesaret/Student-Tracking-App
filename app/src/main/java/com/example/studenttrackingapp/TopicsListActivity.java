package com.example.studenttrackingapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import com.example.studenttrackingapp.DAO.TopicDAO;

import java.util.List;

public class TopicsListActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private TopicDAO topicDAO;
    private Button addTopicButton;
    private String selectedTopic, topicToDelete, title;
    private List<String> topics;
    private static final String TAG = "TopicsListActivity";
    private GestureDetectorCompat gestureDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics_list);

        listView = findViewById(R.id.topicsListView);
        addTopicButton = findViewById(R.id.addTopicButton);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);

        refreshTopicList();

        addTopicButton.setOnClickListener(v -> showAddTopicDialog());

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            selectedTopic = adapter.getItem(position);
            if (selectedTopic != null) {
                Intent intent = new Intent(this, TestActivity.class);
                intent.putExtra("topicTitle", selectedTopic);
                intent.putExtra("topicId", topicDAO.getTopicId(selectedTopic));
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

        // Gesture detector
        gestureDetector = new GestureDetectorCompat(this, new GestureListener());

        listView.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));
    }

    private void refreshTopicList() {
        adapter.clear();
        String bookTitle = getIntent().getStringExtra("bookTitle").toString();
        topicDAO = new TopicDAO(this);
        topics = topicDAO.getAllTopics(bookTitle);
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
                int bookId = getIntent().getIntExtra("bookId", -1);
                if (bookId == -1) {
                    Log.e(TAG, "bookId is missing!");
                    return;
                }
                Log.d(TAG, "bookId: " + bookId);
                topicDAO.addTopic(bookId, title);
                Toast.makeText(this, "Topic added", Toast.LENGTH_SHORT).show();
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


    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float diffY = e2.getY() - e1.getY();
            float diffX = e2.getX() - e1.getX();

            if (Math.abs(diffY) > Math.abs(diffX)) {
                if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    int position = listView.pointToPosition((int) e1.getX(), (int) e1.getY());
                    if (position != AdapterView.INVALID_POSITION) {
                        String topicToDelete = adapter.getItem(position);
                        if (topicToDelete != null) {
                            showDeleteDialog(topicToDelete);
                        }
                    }
                    return true;
                }
            }
            return false;
        }
    }
}
