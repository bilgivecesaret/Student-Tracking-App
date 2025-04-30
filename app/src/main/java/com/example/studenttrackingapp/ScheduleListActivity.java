package com.example.studenttrackingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;

public class ScheduleListActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_CALENDAR = 1001;
    private ListView listView;
    private Button addButton;
    private ArrayList<HashMap<String, String>> scheduleList;
    private String scheduleType;
    private String studentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_list);

        listView = findViewById(R.id.listViewSchedule);
        addButton = findViewById(R.id.addScheduleButton);

        scheduleType = getIntent().getStringExtra("scheduleType");
        studentName = getIntent().getStringExtra("studentName");

        scheduleList = new ArrayList<>();

        // Preload with dummy data (optional)
        if (scheduleType.equals("Yearly")) {
            addSchedule("Math", "01/09/2025 - 30/06/2026", "50%");
        } else if (scheduleType.equals("Weekly")) {
            addSchedule("Science", "01/09/2025 - 07/09/2025", "20%");
        }

        SimpleAdapter adapter = new SimpleAdapter(
                this,
                scheduleList,
                android.R.layout.simple_list_item_2,
                new String[]{"Subject", "Details"},
                new int[]{android.R.id.text1, android.R.id.text2});

        listView.setAdapter(adapter);

        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(ScheduleListActivity.this, CalendarActivity.class);
            intent.putExtra("studentName", studentName);
            intent.putExtra("scheduleType", scheduleType);
            startActivityForResult(intent, REQUEST_CODE_CALENDAR);
        });
    }

    private void addSchedule(String subject, String dateRange, String completion) {
        HashMap<String, String> map = new HashMap<>();
        map.put("Subject", subject);
        map.put("Details", "Date: " + dateRange + " | Completion: " + completion);
        scheduleList.add(map);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_CALENDAR && resultCode == RESULT_OK) {
            String subject = data.getStringExtra("subject");
            String dateRange = data.getStringExtra("dateRange");
            String completion = data.getStringExtra("completion");

            addSchedule(subject, dateRange, completion);

            ((SimpleAdapter) listView.getAdapter()).notifyDataSetChanged();
        }
    }

}

