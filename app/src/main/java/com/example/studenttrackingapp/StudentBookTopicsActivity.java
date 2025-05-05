/*  Created by Ugur OZKAN(21050161003) && Bahri KESKIN(22050161001) */
package com.example.studenttrackingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studenttrackingapp.DAO.TopicDAO;

import java.util.List;

/**
 * When the teacher clicks on the student's assigned book,
 * this Activity opens → the topics are listed.
 */
public class StudentBookTopicsActivity extends AppCompatActivity {

    private String  studentName;   // selected student
    private String  bookTitle;     // selected book

    private ArrayAdapter<String> adapter;
    private TopicDAO topicDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_book_topics);

        /* --- Intent data --- */
        studentName = getIntent().getStringExtra("student_name");
        bookTitle   = getIntent().getStringExtra("book_title");

        /* --- DAO (Data Access Object) --- */
        topicDAO = new TopicDAO(this);

        /* --- Fill the list --- */
        ListView listView = findViewById(R.id.topicList);
        List<String> topics = topicDAO.getAllTopics(bookTitle); // Method in TopicDAO

        if (topics.isEmpty()) {
            Toast.makeText(this, "No topic found for \"" + bookTitle + "\"",
                    Toast.LENGTH_SHORT).show();
        }

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, topics);
        listView.setAdapter(adapter);

        /* --- Click on the topic to go to the test list --- */
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String topicName = adapter.getItem(position);
            if (topicName != null) {
                Intent in = new Intent(this, StudentTopicTestsActivity.class);
                in.putExtra("student_name", studentName);
                in.putExtra("topic_name",  topicName);
                startActivity(in);
            }
        });
    }
}
