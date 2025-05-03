package com.example.studenttrackingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

/** Öğrenci: kendisine atanmış kitap listesini görür */
public class StudentAssignedBooksActivity extends AppCompatActivity {

    private String studentName;
    private ArrayAdapter<String> adapter;
    private AssignmentDAO assignDAO;

    @Override protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_student_assigned_books);

        studentName = getIntent().getStringExtra("student_name");
        assignDAO   = new AssignmentDAO(this);

        List<String> books = assignDAO.getBooksForStudent(studentName);
        if (books.isEmpty())
            Toast.makeText(this,"No assigned book yet.",Toast.LENGTH_SHORT).show();

        ListView lv = findViewById(R.id.assignedBookList);
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, books);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener((p,v,pos,id)->{
            String book = adapter.getItem(pos);
            Intent in = new Intent(this, StudentViewTopicsActivity.class);
            in.putExtra("student_name", studentName);
            in.putExtra("book_title",  book);
            startActivity(in);
        });
    }
}
