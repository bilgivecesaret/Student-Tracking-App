/*  Created by Ugur OZKAN(21050161003) && Bahri KESKIN(22050161001) */
package com.example.studenttrackingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GestureDetectorCompat;
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
    private GestureDetectorCompat gestureDetector;


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

        // Gesture detector
        gestureDetector = new GestureDetectorCompat(requireContext(), new GestureListener());

        listView.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));
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

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float diffY = e2.getY() - e1.getY();
            float diffX = e2.getX() - e1.getX();

            if (Math.abs(diffY) > Math.abs(diffX)) {
                if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    int position = listView.pointToPosition((int) e1.getX(), (int) e1.getY());
                    if (position != AdapterView.INVALID_POSITION) {
                        String bookToDelete = adapter.getItem(position);
                        if (bookToDelete != null) {
                            showDeleteDialog(bookToDelete);
                        }
                    }
                    return true;
                }
            }
            return false;
        }
    }
}
