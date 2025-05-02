package com.example.studenttrackingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TestDAO {

    private DatabaseHelper dbHelper;

    public TestDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Belirli bir konuya test ekle
    public void addTest(int topicId, String testName) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_TEST_TOPIC_ID, topicId);
        values.put(DatabaseHelper.COLUMN_TEST_NAME, testName);
        db.insert(DatabaseHelper.TABLE_TESTS, null, values);
        db.close();
    }

    // Belirli bir konuya ait testleri getir
    public List<String> getTestsByTopicId(int topicId) {
        List<String> tests = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(DatabaseHelper.TABLE_TESTS,
                new String[]{DatabaseHelper.COLUMN_TEST_NAME},
                DatabaseHelper.COLUMN_TEST_TOPIC_ID + " = ?",
                new String[]{String.valueOf(topicId)},
                null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String test = cursor.getString(0);
                tests.add(test);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return tests;
    }

    // Test adı ve konusuna göre test kontrolü
    public boolean isTestExists(int topicId, String testName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_TESTS,
                new String[]{DatabaseHelper.COLUMN_TEST_ID},
                DatabaseHelper.COLUMN_TEST_TOPIC_ID + "=? AND " + DatabaseHelper.COLUMN_TEST_NAME + "=?",
                new String[]{String.valueOf(topicId), testName},
                null, null, null);
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }

    // Konu adına göre testleri getir
    public List<String> getTestsByTopicName(String topicName) {
        List<String> testList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT s." + DatabaseHelper.COLUMN_TEST_NAME +
                " FROM " + DatabaseHelper.TABLE_TESTS + " s " +
                "JOIN " + DatabaseHelper.TABLE_TOPICS + " t ON s." + DatabaseHelper.COLUMN_TEST_TOPIC_ID +
                " = t." + DatabaseHelper.COLUMN_TOPIC_ID +
                " WHERE t." + DatabaseHelper.COLUMN_TOPIC_NAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{topicName});

        if (cursor.moveToFirst()) {
            do {
                testList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return testList;
    }

    public List<String> getAllTests() {
        List<String> testList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT " + DatabaseHelper.COLUMN_TEST_NAME +
                " FROM " + DatabaseHelper.TABLE_TESTS;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                testList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return testList;
    }


}
