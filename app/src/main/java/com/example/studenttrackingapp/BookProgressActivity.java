package com.example.studenttrackingapp;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class BookProgressActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private String studentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_progress);

        listView = findViewById(R.id.bookProgressListView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        studentName = getIntent().getStringExtra("student_name");
        if (studentName == null) studentName = "Mustafa"; // fallback

        List<DataRepository.StudentTopicAssignment> assignments =
                DataRepository.getInstance().getAssignmentsForStudent(studentName);

        for (DataRepository.StudentTopicAssignment a : assignments) {
            adapter.add("Kitap: " + a.book +
                    "\nKonu: " + a.topic +
                    "\nTarih: " + a.dateRange +
                    "\nTamamlanma: %50"); // Örnek: ilerleme verisi sabit
        }

        listView.setAdapter(adapter);
    }
}

