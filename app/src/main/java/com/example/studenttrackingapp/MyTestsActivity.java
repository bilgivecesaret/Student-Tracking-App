package com.example.studenttrackingapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.studenttrackingapp.Preferences.UserPreferences;
import java.util.ArrayList;

public class MyTestsActivity extends AppCompatActivity {

    private ListView myTestListView;
    private ArrayList<String> testList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tests);

        myTestListView = findViewById(R.id.myTestListView);

        // Kullanıcıyı SharedPreferences'ten al
        String username = UserPreferences.getUsername(this);

        // Test verisini oluşturma (burada statik veri kullanıyoruz, dinamik hale getirebilirsin)
        testList = new ArrayList<>();
        testList.add("Math Test - Score: 80");
        testList.add("Physics Test - Score: 90");
        testList.add("Geometry Test - Score: 75");

        // Adapter ile listeyi bağla
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, testList);
        myTestListView.setAdapter(adapter);
    }
}
