package com.example.studenttrackingapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RemoteBookAdapter extends RecyclerView.Adapter<RemoteBookAdapter.ViewHolder> {

    private List<RemoteBook> bookList;

    public RemoteBookAdapter(List<RemoteBook> bookList) {
        this.bookList = bookList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleText;

        public ViewHolder(View view) {
            super(view);
            titleText = view.findViewById(R.id.titleTextView);
        }
    }

    @NonNull
    @Override
    public RemoteBookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.remote_book_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RemoteBook book = bookList.get(position);
        holder.titleText.setText(book.getTitle());
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }
}

