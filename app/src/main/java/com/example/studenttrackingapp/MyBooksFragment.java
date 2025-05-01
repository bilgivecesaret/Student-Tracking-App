package com.example.studenttrackingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.*;

import java.util.List;

public class MyBooksFragment extends Fragment {

    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private List<DataRepository.Book> bookList;

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
        recyclerView = view.findViewById(R.id.booksRecyclerView);
        Button addBookButton = view.findViewById(R.id.addBookButton);

        bookList = DataRepository.getInstance().getBooks();
        adapter = new BookAdapter(bookList, book -> {
            Intent intent = new Intent(getActivity(), TopicsListActivity.class);
            intent.putExtra("bookTitle", book.getTitle());
            startActivity(intent);
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Swipe-to-delete desteği
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                DataRepository.getInstance().removeBook(position);
                adapter.notifyItemRemoved(position);
                Toast.makeText(getContext(), "Book deleted", Toast.LENGTH_SHORT).show();
            }
        };
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);

        addBookButton.setOnClickListener(v -> showAddBookDialog());
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
                DataRepository.Book newBook = new DataRepository.Book(title, "Author Name");
                DataRepository.getInstance().addBook(newBook);
                adapter.notifyItemInserted(bookList.size() - 1);
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
}
