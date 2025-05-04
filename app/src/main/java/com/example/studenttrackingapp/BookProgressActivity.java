package com.example.studenttrackingapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studenttrackingapp.DAO.TestDAO;
import com.example.studenttrackingapp.DAO.TopicDAO;
import com.example.studenttrackingapp.Preferences.AssignmentPreferences;
import com.example.studenttrackingapp.Preferences.ProgressPreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Öğrenci – Öğretmeninin atadığı kitaplarda ne kadar test bitirdiğini görür.
 * Sadece okunur; öğrenci işaretleme yapamaz.
 */
public class BookProgressActivity extends AppCompatActivity {

    private ArrayAdapter<String> adapter;

    private String        student;          // oturum açan öğrenci
    private AssignmentPreferences assignmentPreferences;
    private TopicDAO topicDAO;
    private TestDAO testDAO;
    private ProgressPreferences progressPreferences;

    @Override
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_book_progress);

        /* INTENT’ten öğrenci adı */
        student = getIntent().getStringExtra("student_name");
        if (student == null) student = "Mustafa";   // varsayılan

        /* DAO’lar */
        assignmentPreferences = new AssignmentPreferences(this);
        topicDAO      = new TopicDAO(this);
        testDAO       = new TestDAO(this);
        progressPreferences = new ProgressPreferences(this);

        /* ListView hazırlığı */
        ListView lv = findViewById(R.id.bookProgressListView);
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, new ArrayList<>());
        lv.setAdapter(adapter);

        loadProgress();
    }

    /** Her atanan kitap için ilerleme yüzdesini hesapla ve listede göster */
    private void loadProgress() {
        adapter.clear();

        /* 1 – Bu öğrenciye atanmış kitaplar */
        List<String> myBooks = assignmentPreferences.getBooksForStudent(student);
        if (myBooks.isEmpty()) {
            adapter.add("You have no assigned books yet.");
            return;
        }

        for (String book : myBooks) {
            int totalTests = 0;
            int doneTests  = 0;

            /* 2 – Kitabın konuları */
            List<String> topics = topicDAO.getAllTopics(book);

            for (String topic : topics) {
                /* 3 – O konudaki testler */
                List<String> tests = testDAO.getAllTests(topic);
                totalTests += tests.size();

                for (String test : tests) {
                    if (progressPreferences.isCompleted(student, topic, test)) doneTests++;
                }
            }

            String pct = totalTests == 0 ? "0%" :
                    (doneTests * 100 / totalTests) + "%";

            adapter.add("Book: " + book +
                    "\nCompleted: " + doneTests + "/" + totalTests +
                    " (" + pct + ")");
        }
    }
}
