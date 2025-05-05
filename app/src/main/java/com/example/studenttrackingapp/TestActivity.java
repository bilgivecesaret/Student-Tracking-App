/*  Created by Ugur OZKAN(21050161003) && Bahri KESKIN(22050161001) */
package com.example.studenttrackingapp;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studenttrackingapp.DAO.TestDAO;

import java.util.ArrayList;
import java.util.List;


public class TestActivity extends AppCompatActivity {

    private String title, testToDelete;
    private ListView listView;
    private TestDAO testDAO;
    private Button addTestButton;
    private List<String> topics;
    private ArrayAdapter<String> adapter;
    private static final String TAG = "TestActivity";
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        listView = findViewById(R.id.testListView);
        addTestButton = findViewById(R.id.addTestButton);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);

        refreshTopicList();

        addTestButton.setOnClickListener(v -> showAddTestDialog());

        listView.setOnItemLongClickListener((parent, view12, position, id) -> {
            testToDelete = adapter.getItem(position);
            if (testToDelete != null) {
                showDeleteDialog(testToDelete);
            }
            return true;
        });

        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();

                // Yalnızca yukarı veya aşağı kaydırma (dikey swipe)
                if (Math.abs(diffY) > Math.abs(diffX)) {
                    if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        int position = listView.pointToPosition((int) e1.getX(), (int) e1.getY());
                        if (position != ListView.INVALID_POSITION) {
                            testToDelete = adapter.getItem(position);
                            if (testToDelete != null) {
                                showDeleteDialog(testToDelete);
                            }
                        }
                        return true;
                    }
                }
                return false;
            }
        });

        /* Listen for ListView touch event */
        listView.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));
    }

    private void refreshTopicList() {
        adapter.clear();
        String topicTitle = getIntent().getStringExtra("topicTitle").toString();
        testDAO = new TestDAO(this);
        topics = testDAO.getAllTests(topicTitle);
        adapter.addAll(topics);
    }

    private void showAddTestDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New Test");

        final EditText input = new EditText(this);
        input.setHint("Test title");
        builder.setView(input);

        builder.setPositiveButton("Add", (dialog, which) -> {
            title = input.getText().toString().trim();
            if (!title.isEmpty()) {
                int bookId = getIntent().getIntExtra("topicId", -1);
                if (bookId == -1) {
                    Log.e(TAG, "topicId is missing!");
                    return;
                }
                Log.d(TAG, "bookId: " + bookId);
                testDAO.addTest(bookId, title);
                Toast.makeText(this, "Test added", Toast.LENGTH_SHORT).show();
                refreshTopicList();
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void showDeleteDialog(String testTitle) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Test")
                .setMessage("Are you sure you want to delete \"" + testTitle + "\"?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    testDAO.deleteTest(testTitle);
                    refreshTopicList();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
