/*  Created by Ugur OZKAN(21050161003) && Bahri KESKIN(22050161001) */
package com.example.studenttrackingapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private DatabaseHelper dbHelper;
    private static final String TAG = "StudentDAO";

    public StudentDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public boolean validateStudentLogin(String username, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(DatabaseHelper.TABLE_STUDENTS,
                new String[]{DatabaseHelper.COLUMN_STUDENT_ID},
                DatabaseHelper.COLUMN_STUDENT_USERNAME + "=? AND " +
                        DatabaseHelper.COLUMN_STUDENT_PASSWORD + "=?",
                new String[]{username, password},
                null, null, null);

        boolean isValid = cursor.moveToFirst();
        cursor.close();
        db.close();
        return isValid;
    }

    public String getStudentName(String username) {
        String name = "";
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(DatabaseHelper.TABLE_STUDENTS,
                new String[]{DatabaseHelper.COLUMN_STUDENT_NAME},
                DatabaseHelper.COLUMN_STUDENT_USERNAME + "=?",
                new String[]{username},
                null, null, null);

        if (cursor.moveToFirst()) {
            name = cursor.getString(0);
        }

        cursor.close();
        db.close();
        return name;
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

