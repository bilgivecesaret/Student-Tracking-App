package com.example.studenttrackingapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.studenttrackingapp.Book;
import com.example.studenttrackingapp.R;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<Book> bookList;

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        public TextView titleView;

        public BookViewHolder(View view) {
            super(view);
            titleView = view.findViewById(R.id.bookTitleTextView);
        }
    }

    public BookAdapter(List<Book> books) {
        this.bookList = books;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_item, parent, false);
        return new BookViewHolder(item);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.titleView.setText(book.getTitle());
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }
}
