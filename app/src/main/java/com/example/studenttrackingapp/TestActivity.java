package com.example.studenttrackingapp;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;


public class TestActivity extends AppCompatActivity {

    private List<String> testList = new ArrayList<>();
    private TestAdapter testAdapter;
    private ListView testListView;
    private DatabaseHelper dbHelper;
    private TestDAO testDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        testListView = findViewById(R.id.testListView);
        testDAO = new TestDAO(this);

        testList = testDAO.getAllTests();
        testAdapter = new TestAdapter(this, testList);
        testListView.setAdapter(testAdapter);

        // Test öğesine tıklanma olayını işliyoruz
        testListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parentView, View view, int position, long id) {
                String selectedTest = testList.get(position);
                // Seçilen test ile yapılacak işlemi burada tanımlayabilirsiniz
                // Örneğin, yeni bir activity açabilirsiniz veya testin detaylarını gösterebilirsiniz
            }
        });
    }
}
