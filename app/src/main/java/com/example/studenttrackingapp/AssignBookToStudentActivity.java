/*  Created by Ugur OZKAN(21050161003) && Bahri KESKIN(22050161001) */
package com.example.studenttrackingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studenttrackingapp.DAO.BookDAO;
import com.example.studenttrackingapp.Preferences.AssignmentPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AssignBookToStudentActivity extends AppCompatActivity {

    private TextView studentNameTxt;
    private Button   assignBookBtn;
    private ListView assignedList;

    private BookDAO bookDAO;

    private String studentName;
    private ArrayAdapter<String> adapter;
    private List<String> assignedBooks = new ArrayList<>();

    private AssignmentPreferences assignmentPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_book_to_student);

        studentNameTxt = findViewById(R.id.studentTitle);
        assignBookBtn  = findViewById(R.id.assignBookBtn);
        assignedList   = findViewById(R.id.assignedBookList);

        studentName = getIntent().getStringExtra("student_name");
        studentNameTxt.setText(studentName);

        bookDAO      = new BookDAO(this);
        assignmentPreferences = new AssignmentPreferences(this);

        // Bring the student the previous assigned books
        assignedBooks.addAll(assignmentPreferences.getBooksForStudent(studentName));

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, assignedBooks);
        assignedList.setAdapter(adapter);

        refreshTopicList();

        assignBookBtn.setOnClickListener(v -> {
            showAssignDialog();
        });
        assignedList.setOnItemClickListener((p, v, pos, id) -> {
            String bookTitle = adapter.getItem(pos);
            Intent in = new Intent(this, StudentBookTopicsActivity.class);
            in.putExtra("student_name", studentName);
            in.putExtra("book_title",  bookTitle);
            startActivity(in);
        });

        /* 2️⃣  Long press to delete the lesson */
        assignedList.setOnItemLongClickListener((p, v12, pos, id) -> {
            String toDelete = adapter.getItem(pos);
            if (toDelete != null){
                showDeleteDialog(toDelete);
            }
            return true;
        });
    }

    private void refreshTopicList() {
        adapter.clear();
        assignedBooks = assignmentPreferences.getBooksForStudent(studentName);
        adapter.addAll(assignedBooks);
    }

    /** Selection window from the teacher's book list */
    private void showAssignDialog() {
        /* Retrieve all books from the teacher's database */
        List<String> teacherBooks = bookDAO.getAllBooks()
                .stream()
                .map(String::toString)  // zaten String ise gerek yok
                .collect(Collectors.toList());

        if (teacherBooks.isEmpty()) {
            Toast.makeText(this, "No book found in My Books", Toast.LENGTH_SHORT).show();
            return;
        }

        CharSequence[] items = teacherBooks.toArray(new CharSequence[0]);

        new AlertDialog.Builder(this)
                .setTitle("Select book to assign")
                .setItems(items, (d, which) -> {
                    String chosen = teacherBooks.get(which);
                    if (!assignedBooks.contains(chosen)) {
                        assignmentPreferences.assignBook(studentName, chosen);
                        assignedBooks.add(chosen);
                        refreshTopicList();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    /** Confirmation of deletion */
    private void showDeleteDialog(String book) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Student")
                .setMessage("Delete \"" + book + "\"?")
                .setPositiveButton("Delete", (d, w) -> {
                    assignmentPreferences.unassignBook(studentName,book);
                    assignedBooks.remove(book);
                    refreshTopicList();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
