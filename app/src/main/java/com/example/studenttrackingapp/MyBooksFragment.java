package com.example.studenttrackingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.studenttrackingapp.DAO.BookDAO;

import java.util.List;

public class MyBooksFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private BookDAO bookDAO;
    private Button addBookButton;
    private String selectedBook, bookToDelete, title;
    private List<String> books;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_books, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        listView = view.findViewById(R.id.booksListView);
        addBookButton = view.findViewById(R.id.addBookButton);

        bookDAO = new BookDAO(requireContext());

        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);

        refreshBookList();

        addBookButton.setOnClickListener(v -> showAddBookDialog());

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            selectedBook = adapter.getItem(position);
            if (selectedBook != null) {
                Intent intent = new Intent(getActivity(), TopicsListActivity.class);
                intent.putExtra("bookTitle", selectedBook);
                intent.putExtra("bookId", bookDAO.getBookId(selectedBook));
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener((parent, view12, position, id) -> {
            bookToDelete = adapter.getItem(position);
            if (bookToDelete != null) {
                showDeleteDialog(bookToDelete);
            }
            return true;
        });
    }

    private void refreshBookList() {
        adapter.clear();
        books = bookDAO.getAllBooks();
        adapter.addAll(books);
    }

    private void showAddBookDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Add New Book");

        final EditText input = new EditText(requireContext());
        input.setHint("Book title");
        builder.setView(input);

        builder.setPositiveButton("Add", (dialog, which) -> {
            title = input.getText().toString().trim();
            if (!title.isEmpty()) {
                bookDAO.addBook(title);
                refreshBookList();
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void showDeleteDialog(String bookTitle) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Delete Book")
                .setMessage("Are you sure you want to delete \"" + bookTitle + "\"?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    bookDAO.deleteBook(bookTitle);
                    refreshBookList();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void getBookId() {
        bookDAO = new BookDAO(requireContext());

    }
}
