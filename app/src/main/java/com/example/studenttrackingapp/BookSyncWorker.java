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
import java.util.ArrayList;
import java.util.List;
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
            URL url = new URL("https://raw.githubusercontent.com/kullaniciadi/projeadi/main/books.json");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            String jsonData = scanner.hasNext() ? scanner.next() : "";
            JSONArray jsonArray = new JSONArray(jsonData);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject bookObj = jsonArray.getJSONObject(i);
                String title = bookObj.getString("title");

                // Konuları ayıkla
                JSONArray topicsArray = bookObj.getJSONArray("topics");
                List<Topic> topics = new ArrayList<>();

                for (int j = 0; j < topicsArray.length(); j++) {
                    JSONObject topicObj = topicsArray.getJSONObject(j);
                    String topicName = topicObj.getString("name");

                    JSONArray testsArray = topicObj.getJSONArray("tests");
                    List<String> tests = new ArrayList<>();
                    for (int k = 0; k < testsArray.length(); k++) {
                        tests.add(testsArray.getString(k));
                    }

                    topics.add(new Topic(topicName, tests));
                }

                // Book nesnesi oluştur ve ekle
                Book book = new Book(title, topics);
                DataRepository.getInstance().addBook(book);
            }

            Log.d(TAG, "Kitap senkronizasyonu tamamlandı.");
            return Result.success();

        } catch (Exception e) {
            Log.e(TAG, "Kitap senkronizasyonu başarısız: " + e.getMessage());
            return Result.failure();
        }
    }

}

