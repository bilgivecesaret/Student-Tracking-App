package com.example.studenttrackingapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import java.util.ArrayList;

public class MyStudentsFragment extends Fragment {

    private RecyclerView recyclerView;
    private StudentAdapter adapter;
    private ArrayList<String> students;
    private Button addStudentButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_students, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewStudents);
        addStudentButton = view.findViewById(R.id.addStudentButton);

        students = new ArrayList<>();
        students.add("Mustafa"); // Preload with Mustafa

        adapter = new StudentAdapter(students, name -> {
            Intent intent = new Intent(getActivity(), StudentDetailActivity.class);
            intent.putExtra("studentName", name);
            startActivity(intent);
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        addStudentButton.setOnClickListener(v -> {
            // Future: Open dialog to add new student
        });

        return view;
    }
}

