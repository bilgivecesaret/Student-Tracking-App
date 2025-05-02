package com.example.studenttrackingapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "student_tracking.db";
    public static final int DATABASE_VERSION = 1;

    // Tablo adları
    public static final String TABLE_BOOKS = "books";
    public static final String TABLE_TOPICS = "topics";
    public static final String TABLE_TESTS = "tests";

    // Book sütunları
    public static final String COLUMN_BOOK_ID = "id";
    public static final String COLUMN_BOOK_NAME = "name";

    // Topic sütunları
    public static final String COLUMN_TOPIC_ID = "id";
    public static final String COLUMN_TOPIC_BOOK_ID = "book_id";
    public static final String COLUMN_TOPIC_NAME = "name";

    // Test sütunları
    public static final String COLUMN_TEST_ID = "id";
    public static final String COLUMN_TEST_TOPIC_ID = "topic_id";
    public static final String COLUMN_TEST_NAME = "name";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TESTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOPICS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS);
        onCreate(db);
    }
}







