package com.example.studenttrackingapp;

import android.os.Bundle;
import android.util.Log;
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

    private List<String> testList = new ArrayList<>();
    private String title, testToDelete;
    private ListView listView;
    private TestDAO testDAO;
    private Button addTestButton;
    private List<String> topics;
    private ArrayAdapter<String> adapter;
    private static final String TAG = "TestActivity";

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
