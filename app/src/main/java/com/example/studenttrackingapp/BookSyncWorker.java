package com.example.studenttrackingapp;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Scanner;

public class BookSyncWorker extends Worker {

    private static final String TAG = "BookSyncWorker";

    public BookSyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            URL url = new URL("https://raw.githubusercontent.com/bilgivecesaret/student-tracking-json/refs/heads/main/books.json");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            String jsonData = scanner.hasNext() ? scanner.next() : "";
            JSONObject booksObject = new JSONObject(jsonData).getJSONObject("books");

            BookDAO bookDAO = new BookDAO(getApplicationContext());
            TopicDAO topicDAO = new TopicDAO(getApplicationContext());
            TestDAO testDAO = new TestDAO(getApplicationContext());

            Iterator<String> bookKeys = booksObject.keys();
            while (bookKeys.hasNext()) {
                String bookTitle = bookKeys.next();

                if (!bookDAO.isBookExists(bookTitle)) {
                    int bookId = bookDAO.addBook(bookTitle);
                    JSONObject topicsObject = booksObject.getJSONObject(bookTitle).getJSONObject("topics");

                    Iterator<String> topicKeys = topicsObject.keys();
                    while (topicKeys.hasNext()) {
                        String topicName = topicKeys.next();

                        if (!topicDAO.isTopicExists(bookId, topicName)) {
                            int topicId = topicDAO.addTopic(bookId, topicName);
                            JSONArray testArray = topicsObject.getJSONArray(topicName);

                            for (int i = 0; i < testArray.length(); i++) {
                                String testName = testArray.getString(i);
                                if (!testDAO.isTestExists(topicId, testName)) {
                                    testDAO.addTest(topicId, testName);
                                }
                            }
                        }
                    }
                }
            }

            Log.d(TAG, "Kitap senkronizasyonu tamamlandı.");
            return Result.success();

        } catch (Exception e) {
            Log.e(TAG, "Kitap senkronizasyonu başarısız: " + e.getMessage());
            return Result.failure();
        }
    }
}
