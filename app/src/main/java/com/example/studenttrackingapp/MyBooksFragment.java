package com.example.studenttrackingapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.List;

public class MyBooksFragment extends Fragment {

    private ArrayAdapter<DataRepository.Book> adapter;
    private ListView listView;

    public MyBooksFragment() {
        // Zorunlu boş constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_books, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        listView = view.findViewById(R.id.booksListView);
        Button addBookButton = view.findViewById(R.id.addBookButton);

        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);

        refreshBookList();

        addBookButton.setOnClickListener(v -> showAddBookDialog());

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            DataRepository.Book selectedBook = adapter.getItem(position);
            if (selectedBook != null) {
                Intent intent = new Intent(getActivity(), TopicsListActivity.class);
                intent.putExtra("bookTitle", selectedBook.title);
                startActivity(intent);
            }
        });
    }

    private void refreshBookList() {
        adapter.clear();
        List<DataRepository.Book> books = DataRepository.getInstance().getBooks();
        adapter.addAll(books);
    }

    private void showAddBookDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Add New Book");

        final EditText input = new EditText(requireContext());
        input.setHint("Book title");
        builder.setView(input);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String title = input.getText().toString().trim();
            if (!title.isEmpty()) {
                DataRepository.Book newBook = new DataRepository.Book(title);
                DataRepository.getInstance().addBook(newBook);
                refreshBookList();
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
}
