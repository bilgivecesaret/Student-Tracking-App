package com.example.studenttrackingapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;
import java.util.List;

public class MyStudentsFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private StudentDAO studentDAO;
    private Button addStudentButton;
    private String selectedStudent, studentToDelete, name;
    private List<String> students;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_students, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        listView = view.findViewById(R.id.studentsListView);
        addStudentButton = view.findViewById(R.id.addStudentButton);


        studentDAO = new StudentDAO(requireContext());

        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);

        refreshStudentList();

        addStudentButton.setOnClickListener(v -> showAddBookDialog());

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            selectedStudent = adapter.getItem(position);
            if (selectedStudent != null) {
                Toast.makeText(requireContext(), selectedStudent, Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemLongClickListener((parent, view12, position, id) -> {
            studentToDelete = adapter.getItem(position);
            if (studentToDelete != null) {
                showDeleteDialog(studentToDelete);
            }
            return true;
        });
    }

    private void refreshStudentList() {
        adapter.clear();
        students = studentDAO.getAllStudents();
        adapter.addAll(students);
    }

    private void showAddBookDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Add New Student");

        // LinearLayout ile birden fazla EditText eklenmesi
        LinearLayout layout = new LinearLayout(requireContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 40, 50, 10); // biraz iç boşluk

        final EditText nameInput = new EditText(requireContext());
        nameInput.setHint("Name");
        layout.addView(nameInput);

        final EditText usernameInput = new EditText(requireContext());
        usernameInput.setHint("Username");
        layout.addView(usernameInput);

        final EditText passwordInput = new EditText(requireContext());
        passwordInput.setHint("Password");
        passwordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        layout.addView(passwordInput);

        builder.setView(layout);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String name = nameInput.getText().toString().trim();
            String username = usernameInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (!name.isEmpty() && !username.isEmpty() && !password.isEmpty()) {
                studentDAO.addStudent(name, username, password);
                refreshStudentList();
            } else {
                Toast.makeText(requireContext(), "All fields are required", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }


    private void showDeleteDialog(String username) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Delete Book")
                .setMessage("Are you sure you want to delete \"" + username + "\"?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    studentDAO.deleteStudent(username);
                    refreshStudentList();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

}
