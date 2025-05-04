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

public class TeacherStudentDetailActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_teacher_student_detail);

        studentNameTxt = findViewById(R.id.studentTitle);
        assignBookBtn  = findViewById(R.id.assignBookBtn);
        assignedList   = findViewById(R.id.assignedBookList);

        studentName = getIntent().getStringExtra("student_name");
        studentNameTxt.setText(studentName);

        bookDAO      = new BookDAO(this);
        assignmentPreferences = new AssignmentPreferences(this);

        // Öğrenciye önceki atanan kitapları getir
        assignedBooks.addAll(assignmentPreferences.getBooksForStudent(studentName));

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, assignedBooks);
        assignedList.setAdapter(adapter);

        assignBookBtn.setOnClickListener(v -> showAssignDialog());
        assignedList.setOnItemClickListener((p, v, pos, id) -> {
            String bookTitle = adapter.getItem(pos);
            Intent in = new Intent(this, StudentBookTopicsActivity.class);
            in.putExtra("student_name", studentName);
            in.putExtra("book_title",  bookTitle);
            startActivity(in);
        });

        /* 2️⃣  Uzun basınca dersi sil */
        assignedList.setOnItemLongClickListener((p, v12, pos, id) -> {
            String toDelete = adapter.getItem(pos);
            if (toDelete != null) showDeleteDialog(toDelete);
            return true;
        });

    }

    /** Öğretmenin kitap listesinden seçim penceresi */
    private void showAssignDialog() {
        /* Öğretmenin SQLite’daki tüm kitaplarını çek */
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
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    /** Silme onayı */
    private void showDeleteDialog(String book) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Student")
                .setMessage("Delete \"" + book + "\"?")
                .setPositiveButton("Delete", (d, w) -> {
                    bookDAO.deleteBook(book);
                    adapter.notifyDataSetChanged();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
