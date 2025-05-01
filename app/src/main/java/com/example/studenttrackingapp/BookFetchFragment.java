package com.example.studenttrackingapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookFetchFragment extends Fragment implements BookFetcher.FetchBooksListener {

    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Fragment layout
        View view = inflater.inflate(R.layout.fragment_book_fetch, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Kitapları çekme işlemini başlat
        new BookFetcher(this).execute();

        return view;
    }

    @Override
    public void onBooksFetched(List<Book> books) {
        // Veriler geldikten sonra RecyclerView adapter'ını ayarlayalım
        bookAdapter = new BookAdapter(books);
        recyclerView.setAdapter(bookAdapter);
    }
}
