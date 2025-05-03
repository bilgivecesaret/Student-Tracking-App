package com.example.studenttrackingapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Öğretmen – Belirli bir konu içindeki testleri gösterir.
 * Checkbox’lı listede işaretleyip ilerleme kaydeder.
 */
public class StudentTopicTestsActivity extends AppCompatActivity {

    private String studentName, topicName;

    private ArrayAdapter<String> adapter;
    private List<String> tests = new ArrayList<>();

    private TestDAO      testDAO;
    private ProgressDAO  progressDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_topic_tests);

        studentName = getIntent().getStringExtra("student_name");
        topicName   = getIntent().getStringExtra("topic_name");

        testDAO     = new TestDAO(this);
        progressDAO = new ProgressDAO(this);

        ListView listView = findViewById(R.id.testList);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        /* Test adlarını veritabanından al */
        tests.addAll(testDAO.getAllTests(topicName));

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_multiple_choice, tests);
        listView.setAdapter(adapter);

        /* Daha önce tamamlanmış testleri işaretle */
        for (int i = 0; i < tests.size(); i++) {
            if (progressDAO.isCompleted(studentName, topicName, tests.get(i))) {
                listView.setItemChecked(i, true);
            }
        }

        /* Tıklanınca tamam / tamam değil bilgisi güncellenir */
        listView.setOnItemClickListener((p, v, pos, id) -> {
            String test = adapter.getItem(pos);
            boolean checked = listView.isItemChecked(pos);
            progressDAO.setCompleted(studentName, topicName, test, checked);
        });
    }
}
