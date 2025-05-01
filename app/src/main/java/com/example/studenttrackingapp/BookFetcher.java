package com.example.studenttrackingapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BookFetcher extends AsyncTask<Void, Void, List<Book>> {

    private static final String JSON_URL = "https://raw.githubusercontent.com/bilgivecesaret/student-tracking-json/refs/heads/main/books.json";
    private final FetchBooksListener listener;

    public BookFetcher(FetchBooksListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<Book> doInBackground(Void... voids) {
        List<Book> bookList = new ArrayList<>();
        try {
            // JSON veri çekme işlemi (örneğin HttpURLConnection veya başka bir kütüphane kullanabilirsiniz)
            String jsonResponse = HttpHelper.getJsonResponse(JSON_URL);  // HttpHelper'dan gelen JSON string
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray booksArray = jsonObject.getJSONArray("books");

            for (int i = 0; i < booksArray.length(); i++) {
                JSONObject bookObject = booksArray.getJSONObject(i);
                String bookTitle = bookObject.getString("name");
                JSONArray topicsArray = bookObject.getJSONArray("topics");

                List<Topic> topicList = new ArrayList<>();
                for (int j = 0; j < topicsArray.length(); j++) {
                    JSONObject topicObject = topicsArray.getJSONObject(j);
                    String topicName = topicObject.getString("name");
                    JSONArray testsArray = topicObject.getJSONArray("tests");

                    List<String> testList = new ArrayList<>();
                    for (int k = 0; k < testsArray.length(); k++) {
                        testList.add(testsArray.getString(k));
                    }
                    topicList.add(new Topic(topicName, testList));
                }
                bookList.add(new Book(bookTitle, topicList));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bookList;
    }

    @Override
    protected void onPostExecute(List<Book> books) {
        super.onPostExecute(books);
        listener.onBooksFetched(books);
    }

    public interface FetchBooksListener {
        void onBooksFetched(List<Book> books);
    }
}
