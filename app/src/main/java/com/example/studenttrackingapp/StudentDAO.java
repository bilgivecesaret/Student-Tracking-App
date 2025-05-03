package com.example.studenttrackingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    private static final String TAG = "StudentDAO";

    public StudentDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public int addStudent(String name, String username, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_STUDENT_NAME, name);
        values.put(DatabaseHelper.COLUMN_STUDENT_USERNAME, username);
        values.put(DatabaseHelper.COLUMN_STUDENT_PASSWORD, password);
        long id = db.insert(DatabaseHelper.TABLE_STUDENTS, null, values);
        return (int) id;
    }

    public List<String> getAllStudents() {
        List<String> students = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_STUDENTS,
                new String[]{DatabaseHelper.COLUMN_STUDENT_NAME},
                null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(0);
                students.add(title);
            } while (cursor.moveToNext());
        }
        Log.d(TAG, "Students number: " + students.size());
        cursor.close();
        db.close();
        return students;
    }

    public int getStudentId(String title) {
        int StudentId = -1;
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(DatabaseHelper.TABLE_STUDENTS,
                new String[]{DatabaseHelper.COLUMN_STUDENT_ID},
                DatabaseHelper.COLUMN_STUDENT_NAME + "=?",
                new String[]{title},
                null, null, null);

        if (cursor.moveToFirst()) {
            StudentId = cursor.getInt(0);
        }

        cursor.close();
        db.close();
        return StudentId;
    }

    public void deleteStudent(String title) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_STUDENTS,
                DatabaseHelper.COLUMN_STUDENT_NAME + " = ?",
                new String[]{title});
        db.close();
    }

    public int addStudentAndReturnId(String title) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_STUDENT_NAME, title);
        long id = db.insert(DatabaseHelper.TABLE_STUDENTS, null, values);
        db.close();
        return (int) id;
    }

    public boolean isBookExists(String title) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_STUDENTS,
                new String[]{DatabaseHelper.COLUMN_STUDENT_ID},
                DatabaseHelper.COLUMN_STUDENT_NAME + "=?",
                new String[]{title},
                null, null, null);
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }

}

