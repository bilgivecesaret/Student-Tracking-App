package com.example.studenttrackingapp.Adapter;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.studenttrackingapp.MyBooksFragment;
import com.example.studenttrackingapp.MyStudentsFragment;

public class TeacherPagerAdapter extends FragmentStateAdapter {

    public TeacherPagerAdapter(@NonNull AppCompatActivity activity) {
        super(activity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0)
            return new MyStudentsFragment();
        else
            return new MyBooksFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}


