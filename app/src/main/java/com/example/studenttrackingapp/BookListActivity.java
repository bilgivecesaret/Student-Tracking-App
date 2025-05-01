package com.example.studenttrackingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class BookListActivity extends AppCompatActivity {

    private ListView listView;
    private Button addBookButton;
    private ArrayList<String> books;
    private ArrayAdapter<String> adapter;
    private String studentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        listView = findViewById(R.id.bookListView);
        addBookButton = findViewById(R.id.addBookButton);
        studentName = getIntent().getStringExtra("studentName");

        books = new ArrayList<>();
        books.add("Math");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, books);
        listView.setAdapter(adapter);

        addBookButton.setOnClickListener(v -> {
            EditText input = new EditText(this);
            new android.app.AlertDialog.Builder(this)
                    .setTitle("Add New Book")
                    .setView(input)
                    .setPositiveButton("Add", (dialog, which) -> {
                        books.add(input.getText().toString());
                        adapter.notifyDataSetChanged();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String bookName = books.get(position);
            Intent intent = new Intent(BookListActivity.this, SubjectsListActivity.class);
            intent.putExtra("bookName", bookName);
            intent.putExtra("studentName", studentName);
            startActivity(intent);
        });
    }
}

