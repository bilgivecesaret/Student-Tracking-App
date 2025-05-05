/*  Created by Ugur OZKAN(21050161003) && Bahri KESKIN(22050161001) */
package com.example.studenttrackingapp.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "student_tracking.db";
    public static final int DATABASE_VERSION = 1;

    // Table names
    public static final String TABLE_STUDENTS = "students";
    public static final String TABLE_BOOKS = "books";
    public static final String TABLE_TOPICS = "topics";
    public static final String TABLE_TESTS = "tests";

    // Student columns
    public static final String COLUMN_STUDENT_ID = "id";
    public static final String COLUMN_STUDENT_NAME = "name";
    public static final String COLUMN_STUDENT_USERNAME = "username";
    public static final String COLUMN_STUDENT_PASSWORD = "password";


    // Book columns
    public static final String COLUMN_BOOK_ID = "id";
    public static final String COLUMN_BOOK_NAME = "name";

    // Topic columns
    public static final String COLUMN_TOPIC_ID = "id";
    public static final String COLUMN_TOPIC_BOOK_ID = "book_id";
    public static final String COLUMN_TOPIC_NAME = "name";

    // Test columns
    public static final String COLUMN_TEST_ID = "id";
    public static final String COLUMN_TEST_TOPIC_ID = "topic_id";
    public static final String COLUMN_TEST_NAME = "name";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_STUDENTS + " (" +
                COLUMN_STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_STUDENT_NAME + " TEXT, " + COLUMN_STUDENT_USERNAME + " TEXT, " +
                COLUMN_STUDENT_PASSWORD + " TEXT)");

        db.execSQL("CREATE TABLE " + TABLE_BOOKS + " (" +
                COLUMN_BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_BOOK_NAME + " TEXT)");

        db.execSQL("CREATE TABLE " + TABLE_TOPICS + " (" +
                COLUMN_TOPIC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TOPIC_BOOK_ID + " INTEGER, " +
                COLUMN_TOPIC_NAME + " TEXT, " +
                "FOREIGN KEY(" + COLUMN_TOPIC_BOOK_ID + ") REFERENCES " + TABLE_BOOKS + "(" + COLUMN_BOOK_ID + "))");

        db.execSQL("CREATE TABLE " + TABLE_TESTS + " (" +
                COLUMN_TEST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TEST_TOPIC_ID + " INTEGER, " +
                COLUMN_TEST_NAME + " TEXT, " +
                "FOREIGN KEY(" + COLUMN_TEST_TOPIC_ID + ") REFERENCES " + TABLE_TOPICS + "(" + COLUMN_TOPIC_ID + "))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TESTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOPICS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS);
        onCreate(db);
    }
}







