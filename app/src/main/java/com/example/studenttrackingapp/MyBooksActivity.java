package com.example.studenttrackingapp;


import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MyBooksActivity extends AppCompatActivity {

    private ListView listView;
    private Button addBookButton;
    private ArrayList<String> bookList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_books);

        listView = findViewById(R.id.myBookListView);
        addBookButton = findViewById(R.id.addMyBookButton);

        bookList = new ArrayList<>();
        bookList.add("Math");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, bookList);
        listView.setAdapter(adapter);

        addBookButton.setOnClickListener(v -> {
            EditText input = new EditText(this);
            new android.app.AlertDialog.Builder(this)
                    .setTitle("Add New Book")
                    .setView(input)
                    .setPositiveButton("Add", (dialog, which) -> {
                        bookList.add(input.getText().toString());
                        adapter.notifyDataSetChanged();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String bookName = bookList.get(position);
            Intent intent = new Intent(MyBooksActivity.this, MyTopicsActivity.class);
            intent.putExtra("bookName", bookName);
            startActivity(intent);
        });
    }
}
