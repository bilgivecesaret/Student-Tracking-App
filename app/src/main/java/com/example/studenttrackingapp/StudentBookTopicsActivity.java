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
 * Öğretmen:  Öğrencinin atanmış kitabına tıklayınca
 *            bu Activity açılır → konular listelenir.
 */
public class StudentBookTopicsActivity extends AppCompatActivity {

    private String  studentName;   // seçilen öğrenci
    private String  bookTitle;     // seçilen kitap

    private ArrayAdapter<String> adapter;
    private TopicDAO topicDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_book_topics);

        /* --- Intent verileri --- */
        studentName = getIntent().getStringExtra("student_name");
        bookTitle   = getIntent().getStringExtra("book_title");

        /* --- DAO --- */
        topicDAO = new TopicDAO(this);

        /* --- Listeyi doldur --- */
        ListView listView = findViewById(R.id.topicList);
        List<String> topics = topicDAO.getAllTopics(bookTitle); // TopicDAO’daki metot

        if (topics.isEmpty()) {
            Toast.makeText(this, "No topic found for \"" + bookTitle + "\"",
                    Toast.LENGTH_SHORT).show();
        }

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, topics);
        listView.setAdapter(adapter);

        /* --- Konuya tıklanınca test listesine geç --- */
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
