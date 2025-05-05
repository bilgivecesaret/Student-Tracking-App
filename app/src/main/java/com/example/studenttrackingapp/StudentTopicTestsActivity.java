/*  Created by Ugur OZKAN(21050161003) && Bahri KESKIN(22050161001) */
package com.example.studenttrackingapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studenttrackingapp.DAO.TestDAO;
import com.example.studenttrackingapp.Preferences.StudentProgressPreferences;

import java.util.ArrayList;
import java.util.List;

public class StudentTopicTestsActivity extends AppCompatActivity {

    private String student, topic;
    private ArrayAdapter<String> adapter;
    private StudentProgressPreferences studentProgressPreferences;

    @Override protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_student_topic_tests);

        student = getIntent().getStringExtra("student_name");
        topic   = getIntent().getStringExtra("topic_name");

        TestDAO testDAO = new TestDAO(this);
        studentProgressPreferences = new StudentProgressPreferences(this);

        List<String> rows = new ArrayList<>();
        for (String t : testDAO.getTestsByTopicName(topic)) {
            boolean done = studentProgressPreferences.isCompleted(student, topic + "::" + t);
            rows.add((done ? "☑ " : "☐ ") + t);
        }

        ListView lv = findViewById(R.id.testList);
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, rows);
        lv.setAdapter(adapter);

        /* done/undone when touching the test line */
        lv.setOnItemClickListener((p,v,pos,id)->{
            String line   = adapter.getItem(pos);
            String test   = line.substring(2);     // get test name from line
            boolean done  = !studentProgressPreferences.isCompleted(student, topic + "::" + test);
            studentProgressPreferences.setCompleted(student, topic + "::" + test, done);
            adapter.remove(line);
            adapter.insert((done?"☑ ":"☐ ")+test, pos);
            adapter.notifyDataSetChanged();
        });
    }
}
