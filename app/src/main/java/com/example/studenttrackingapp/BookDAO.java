package com.example.studenttrackingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    private DatabaseHelper dbHelper;

    public BookDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Kitap ekle
    public void addBook(String title) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_TITLE, title);
        db.insert(DatabaseHelper.TABLE_BOOKS, null, values);
        db.close();
    }

    // Tüm kitapları getir
    public List<String> getAllBooks() {
        List<String> books = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_BOOKS,
                new String[]{DatabaseHelper.COLUMN_TITLE},
                null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(0);
                books.add(title);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return books;
    }

    // Kitap sil (başlığa göre)
    public void deleteBook(String title) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_BOOKS,
                DatabaseHelper.COLUMN_TITLE + " = ?",
                new String[]{title});
        db.close();
    }
}
