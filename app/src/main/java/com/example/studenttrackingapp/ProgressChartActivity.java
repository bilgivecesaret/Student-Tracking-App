package com.example.studenttrackingapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studenttrackingapp.DAO.TestDAO;
import com.example.studenttrackingapp.DAO.TopicDAO;
import com.example.studenttrackingapp.Preferences.AssignmentPreferences;
import com.example.studenttrackingapp.Preferences.StudentProgressPreferences;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.*;

import java.util.*;

public class ProgressChartActivity extends AppCompatActivity {

    private PieChart pieChart;
    private String studentName;
    private StudentProgressPreferences studentProgressPreferences;
    private AssignmentPreferences assignmentPreferences;
    private TopicDAO topicDAO;
    private TestDAO testDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_chart);

        pieChart = findViewById(R.id.pieChart);
        studentName = getIntent().getStringExtra("student_name");
        assignmentPreferences = new AssignmentPreferences(this);
        studentProgressPreferences = new StudentProgressPreferences(this);
        topicDAO = new TopicDAO(this);
        testDAO  = new TestDAO(this);
        List<String> bookTitles = assignmentPreferences.getBooksForStudent(studentName);

        int done = 0;
        int toDo = 0;

        // Öğrenciye özel ilerlemeyi al
        for (String bookTitle : bookTitles) {
            for (String topic : topicDAO.getAllTopics(bookTitle)) {
                List<String> tests = testDAO.getTestsByTopicName(topic);
                for (String t : tests)
                    if (studentProgressPreferences.isCompleted(studentName, topic + "::" + t)) {
                        done++;
                    } else {
                        toDo++;
                    }
            }
        }
        int total = done + toDo;
        int percentDone = total == 0 ? 0 : Math.round(100f*done/total);
        int percentToDo = total == 0 ? 0 : Math.round(100f*toDo/total);
        // Pie chart verisi
        ArrayList<PieEntry> entries = new ArrayList<>();
        if (done > 0) entries.add(new PieEntry(percentDone, "Done"));
        if (toDo > 0) entries.add(new PieEntry(percentToDo, "To Do"));


        PieDataSet dataSet = new PieDataSet(entries, "Progress");
        dataSet.setColors(new int[]{R.color.teal_700, R.color.purple_200}, this);
        dataSet.setValueTextSize(14f);

        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Overall Progress");
        pieChart.setCenterTextSize(18f);
        pieChart.invalidate();  // Grafiği yenile
    }
}
