/*  Created by Ugur OZKAN(21050161003) && Bahri KESKIN(22050161001) */
package com.example.studenttrackingapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studenttrackingapp.DAO.TestDAO;
import com.example.studenttrackingapp.DAO.TopicDAO;
import com.example.studenttrackingapp.Preferences.AssignmentPreferences;
import com.example.studenttrackingapp.Preferences.ProgressPreferences;
import com.example.studenttrackingapp.Preferences.StudentProgressPreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * The student sees how many tests he has completed
 * from the books assigned by his teacher.
 * Only read; the student cannot mark.
 */
public class BookProgressActivity extends AppCompatActivity {

    private String studentName;
    private AssignmentPreferences assignmentPreferences;
    private TopicDAO topicDAO;
    private TestDAO testDAO;
    private ProgressPreferences bookProgressPreferences;
    private StudentProgressPreferences topicProgressPreferences;
    private ListView lv;

    @Override
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_book_progress);

        studentName = getIntent().getStringExtra("student_name");
        assignmentPreferences = new AssignmentPreferences(this);
        List<String> myBooks = assignmentPreferences.getBooksForStudent(studentName);
        if (myBooks.isEmpty())
            Toast.makeText(this, "No assigned book yet.", Toast.LENGTH_SHORT).show();
        topicDAO = new TopicDAO(this);
        testDAO = new TestDAO(this);
        topicProgressPreferences = new StudentProgressPreferences(this);
        bookProgressPreferences = new ProgressPreferences(this);


        List<String> showRows = new ArrayList<>();
        for (String bookTitle : myBooks) {
            int totalTests = 0;
            int doneTests = 0;
            for (String topic : topicDAO.getAllTopics(bookTitle)) {
                List<String> tests = testDAO.getTestsByTopicName(topic);
                totalTests += tests.size();
                int counter = 0;
                for (String t : tests) {
                    if (topicProgressPreferences.isCompleted(studentName, topic + "::" + t)) {
                        counter++;
                        bookProgressPreferences.setCompleted(studentName, topic, t, true);
                    }
                }
                doneTests += counter;
            }
            int percent = Math.round(100f * doneTests / totalTests);
            showRows.add("Book: " + bookTitle +
                    "\nCompleted: " + doneTests + "/" + totalTests +
                    " (" + percent + "%)");
        }

        lv = findViewById(R.id.bookProgressListView);
        lv.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, showRows));
    }
}
