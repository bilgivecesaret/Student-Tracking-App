package com.example.studenttrackingapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.*;

import java.util.*;

public class ProgressChartActivity extends AppCompatActivity {

    private PieChart pieChart;
    private String studentName = "Mustafa";  // Dinamik hale getirilebilir

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_chart);

        pieChart = findViewById(R.id.pieChart);

        // Öğrenciye özel ilerlemeyi al
        List<DataRepository.StudentTopicAssignment> assignments =
                DataRepository.getInstance().getAssignmentsForStudent(studentName);

        int completed = 0;
        int notCompleted = 0;

        for (DataRepository.StudentTopicAssignment a : assignments) {
            if (a.isCompleted) {
                completed++;
            } else {
                notCompleted++;
            }
        }

        // Pie chart verisi
        ArrayList<PieEntry> entries = new ArrayList<>();
        if (completed > 0) entries.add(new PieEntry(completed, "Tamamlanan"));
        if (notCompleted > 0) entries.add(new PieEntry(notCompleted, "Tamamlanmayan"));

        PieDataSet dataSet = new PieDataSet(entries, "İlerleme Durumu");
        dataSet.setColors(new int[]{R.color.teal_700, R.color.purple_200}, this);
        dataSet.setValueTextSize(14f);

        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Konu İlerlemesi");
        pieChart.setCenterTextSize(18f);
        pieChart.invalidate();  // Grafiği yenile
    }
}
