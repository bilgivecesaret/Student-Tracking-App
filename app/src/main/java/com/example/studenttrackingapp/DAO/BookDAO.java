/*  Created by Ugur OZKAN(21050161003) && Bahri KESKIN(22050161001) */
package com.example.studenttrackingapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    private static final String TAG = "BookDAO";

    public BookDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Add a book
    public int addBook(String title) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_BOOK_NAME, title);
        long id = db.insert(DatabaseHelper.TABLE_BOOKS, null, values);
        return (int) id;
    }

    // Bring all the books
    public List<String> getAllBooks() {
        List<String> books = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_BOOKS,
                new String[]{DatabaseHelper.COLUMN_BOOK_NAME},
                null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(0);
                books.add(title);
            } while (cursor.moveToNext());
        }
        Log.d(TAG, "Kitap sayısı: " + books.size());
        cursor.close();
        db.close();
        return books;
    }

    public int getBookId(String title) {
        int bookId = -1;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_BOOKS,
                new String[]{DatabaseHelper.COLUMN_BOOK_ID},
                DatabaseHelper.COLUMN_BOOK_NAME + "=?",
                new String[]{title},
                null, null, null);

        if (cursor.moveToFirst()) {
            bookId = cursor.getInt(0);
        }
        Log.d(TAG, "Kitap id: " + bookId);
        cursor.close();
        db.close();
        return bookId;
    }


    // Delete book (by title)
    public void deleteBook(String title) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(DatabaseHelper.TABLE_BOOKS,
                DatabaseHelper.COLUMN_BOOK_NAME + " = ?",
                new String[]{title});
        Log.d(TAG, "Kitap silindi: " + title);
        db.close();
    }

    public boolean isBookExists(String title) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_BOOKS,
                new String[]{DatabaseHelper.COLUMN_BOOK_ID},
                DatabaseHelper.COLUMN_BOOK_NAME + "=?",
                new String[]{title},
                null, null, null);
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }


}

