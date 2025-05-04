package com.example.studenttrackingapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.studenttrackingapp.Adapter.TeacherPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TeacherActivity extends AppCompatActivity {

    private TabLayout  tabLayout;
    private ViewPager2 viewPager;
    private TeacherPagerAdapter pagerAdapter;
    private TextView   teacherWelcome;
    private String     teacherName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        tabLayout      = findViewById(R.id.tabLayout);
        viewPager      = findViewById(R.id.viewPager);
        teacherWelcome = findViewById(R.id.teacherWelcome);

        teacherName = getIntent().getStringExtra("teacher_name");
        teacherWelcome.setText(teacherWelcome.getText() + "\t" + teacherName + "!");

        pagerAdapter = new TeacherPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, pos) -> {
            tab.setText(pos == 0 ? "My Students" : "My Books");
        }).attach();
    }
}
