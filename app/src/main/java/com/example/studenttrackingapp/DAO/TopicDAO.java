/*  Created by Ugur OZKAN(21050161003) && Bahri KESKIN(22050161001) */
package com.example.studenttrackingapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class TopicDAO {

    private DatabaseHelper dbHelper;
    private static final String TAG = "TopicDAO";

    public TopicDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public List<String> getAllTopics(String bookTitle) {
        List<String> topics = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // First get book ID by book title
        Cursor bookCursor = db.query(DatabaseHelper.TABLE_BOOKS,
                new String[]{DatabaseHelper.COLUMN_BOOK_ID},
                DatabaseHelper.COLUMN_BOOK_NAME + " = ?",
                new String[]{bookTitle}, null, null, null);

        if (bookCursor.moveToFirst()) {
            int bookId = bookCursor.getInt(0);
            bookCursor.close();

            // Now fetch topics by bookId
            Cursor topicCursor = db.query(DatabaseHelper.TABLE_TOPICS,
                    new String[]{DatabaseHelper.COLUMN_TOPIC_NAME},
                    DatabaseHelper.COLUMN_TOPIC_BOOK_ID + " = ?",
                    new String[]{String.valueOf(bookId)}, null, null, null);

            if (topicCursor.moveToFirst()) {
                do {
                    String topicName = topicCursor.getString(0);
                    topics.add(topicName);
                } while (topicCursor.moveToNext());
            }
            topicCursor.close();
        } else {
            bookCursor.close();
            Log.e(TAG, "The specified book was not found: " + bookTitle);
        }

        db.close();
        return topics;
    }

    // Add topic to a specific book
    public int addTopic(int bookId, String topicName) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_TOPIC_BOOK_ID, bookId);  // Doğru sütun adı
        values.put(DatabaseHelper.COLUMN_TOPIC_NAME, topicName);
        long id = db.insert(DatabaseHelper.TABLE_TOPICS, null, values);
        Log.d(TAG, topicName + " added: " + topicName + " - Book ID: " + id);
        db.close();
        return (int) id;
    }

    public void deleteTopic(String title) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_TOPICS,
                DatabaseHelper.COLUMN_TOPIC_NAME + " = ?",
                new String[]{title});
        db.close();
    }

    public List<String> getTopicsByBookName(String bookName) {
        List<String> topicList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT t." + DatabaseHelper.COLUMN_TOPIC_NAME +
                " FROM " + DatabaseHelper.TABLE_TOPICS + " t " +
                "JOIN " + DatabaseHelper.TABLE_BOOKS + " b ON t." + DatabaseHelper.COLUMN_BOOK_ID +
                " = b." + DatabaseHelper.COLUMN_BOOK_ID +
                " WHERE b." + DatabaseHelper.COLUMN_BOOK_NAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{bookName});

        if (cursor.moveToFirst()) {
            do {
                topicList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return topicList;
    }

    // Get topics related to a specific book
    public List<String> getTopicsByBookId(int bookId) {
        List<String> topics = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(DatabaseHelper.TABLE_TOPICS,
                new String[]{DatabaseHelper.COLUMN_TOPIC_NAME},
                DatabaseHelper.COLUMN_TOPIC_BOOK_ID + " = ?",
                new String[]{String.valueOf(bookId)},
                null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String topic = cursor.getString(0);
                topics.add(topic);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return topics;
    }

    // Get topic ID (with book name + topic name)
    public int getTopicId(String title) {
        int topicId = -1;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_TOPICS,
                new String[]{DatabaseHelper.COLUMN_TOPIC_ID},
                DatabaseHelper.COLUMN_TOPIC_NAME + "=?",
                new String[]{title},
                null, null, null);

        if (cursor.moveToFirst()) {
            topicId = cursor.getInt(0);
        }
        Log.d(TAG, "Topic id: " + topicId);
        cursor.close();
        db.close();
        return topicId;
    }

    public boolean isTopicExists(int bookId, String topicName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_TOPICS,
                new String[]{DatabaseHelper.COLUMN_TOPIC_ID},
                DatabaseHelper.COLUMN_BOOK_ID + "=? AND " + DatabaseHelper.COLUMN_TOPIC_NAME + "=?",
                new String[]{String.valueOf(bookId), topicName},
                null, null, null);
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }

}
