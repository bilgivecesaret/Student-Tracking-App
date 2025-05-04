package com.example.studenttrackingapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.studenttrackingapp.R;

import java.util.List;

public class TestAdapter extends BaseAdapter {

    private Context context;
    private List<String> testList;

    public TestAdapter(Context context, List<String> testList) {
        this.context = context;
        this.testList = testList;
    }

    @Override
    public int getCount() {
        return testList.size();
    }

    @Override
    public Object getItem(int position) {
        return testList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_test, parent, false);
        }

        String testName = testList.get(position);

        TextView testNameTextView = convertView.findViewById(R.id.testName);
        testNameTextView.setText(testName);

        return convertView;
    }
}
