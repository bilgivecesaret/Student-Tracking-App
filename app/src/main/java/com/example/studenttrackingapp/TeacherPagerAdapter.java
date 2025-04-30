package com.example.studenttrackingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TeacherPagerAdapter extends FragmentStateAdapter {

    public TeacherPagerAdapter(@NonNull AppCompatActivity activity) {
        super(activity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

            return new MyStudentsFragment();

    }

    @Override
    public int getItemCount() {
        return 2;
    }
}

