package com.example.studenttrackingapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TeacherActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager;
    TeacherPagerAdapter pagerAdapter;
    TextView teacherWelcome;
    String teacherName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        teacherWelcome = findViewById(R.id.teacherWelcome);

        teacherName = getIntent().getStringExtra("teacher_name");
        teacherName = teacherWelcome.getText() + "\t" + teacherName + "!";
        teacherWelcome.setText(teacherName);

        pagerAdapter = new TeacherPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);


        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            if (position == 0) tab.setText("My Students");
            else tab.setText("My Books");
        }).attach();
    }
}

