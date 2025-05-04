package com.example.studenttrackingapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.studenttrackingapp.DAO.StudentDAO;

import java.util.ArrayList;
import java.util.List;

public class MyStudentsFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private StudentDAO studentDAO;
    private Button addStudentButton;

    /* tutulan veriler */
    private List<String> students = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_students, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        listView         = view.findViewById(R.id.studentsListView);
        addStudentButton = view.findViewById(R.id.addStudentButton);

        studentDAO = new StudentDAO(requireContext());

        adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_list_item_1, students);
        listView.setAdapter(adapter);

        refreshStudentList();

        /* Öğrenci ekleme diyaloğu */
        addStudentButton.setOnClickListener(v -> showAddStudentDialog());

        /* 1️⃣  Öğrenci adına tıklanınca StudentDetailActivity açılır */
        listView.setOnItemClickListener((parent, v1, pos, id) -> {
            String selectedStudent = adapter.getItem(pos);
            if (selectedStudent != null) {
                Intent intent = new Intent(requireContext(), StudentDetailActivity.class);
                intent.putExtra("student_name", selectedStudent);   // öğretmen görünümü
                startActivity(intent);
            }
        });


        /* 2️⃣  Uzun basınca öğrenciyi sil */
        listView.setOnItemLongClickListener((p, v12, pos, id) -> {
            String toDelete = adapter.getItem(pos);
            if (toDelete != null) showDeleteDialog(toDelete);
            return true;
        });
    }

    /** Listeyi DAO’dan yenile */
    private void refreshStudentList() {
        students.clear();
        students.addAll(studentDAO.getAllStudents());
        adapter.notifyDataSetChanged();
    }

    /** Yeni öğrenci ekleme penceresi */
    private void showAddStudentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
                .setTitle("Add New Student");

        LinearLayout layout = new LinearLayout(requireContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 40, 50, 10);

        final EditText nameInput     = new EditText(requireContext());
        final EditText usernameInput = new EditText(requireContext());
        final EditText passwordInput = new EditText(requireContext());

        nameInput.setHint("Name");
        usernameInput.setHint("Username");
        passwordInput.setHint("Password");
        passwordInput.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);

        layout.addView(nameInput);
        layout.addView(usernameInput);
        layout.addView(passwordInput);
        builder.setView(layout);

        builder.setPositiveButton("Add", (d, w) -> {
            String name     = nameInput.getText().toString().trim();
            String username = usernameInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "All fields are required",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            studentDAO.addStudent(name, username, password);
            refreshStudentList();
        });

        builder.setNegativeButton("Cancel", null).show();
    }

    /** Silme onayı */
    private void showDeleteDialog(String username) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Delete Student")
                .setMessage("Delete \"" + username + "\"?")
                .setPositiveButton("Delete", (d, w) -> {
                    studentDAO.deleteStudent(username);
                    refreshStudentList();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
