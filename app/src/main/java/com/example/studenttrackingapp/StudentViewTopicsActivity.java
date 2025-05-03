package com.example.studenttrackingapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class StudentViewTopicsActivity extends AppCompatActivity {

    private String studentName, bookTitle;

    @Override protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_student_view_topics);

        studentName = getIntent().getStringExtra("student_name");
        bookTitle   = getIntent().getStringExtra("book_title");

        TopicDAO            topicDAO = new TopicDAO(this);
        TestDAO             testDAO  = new TestDAO(this);
        StudentProgressDAO  progDAO  = new StudentProgressDAO(this);

        List<String> showRows = new ArrayList<>();
        for (String topic : topicDAO.getAllTopics(bookTitle)) {
            List<String> tests   = testDAO.getTestsByTopicName(topic);
            int done             = 0;
            for (String t : tests)
                if (progDAO.isCompleted(studentName, topic + "::" + t)) done++;

            int percent = tests.isEmpty() ? 0 : Math.round(100f*done/tests.size());
            showRows.add("Topic: " + topic +
                    "\nCompleted: " + done + "/" + tests.size() +
                    " (" + percent + "%)");
        }

        ListView lv = findViewById(R.id.topicProgressList);
        lv.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, showRows));
    }
}
