package com.example.studenttrackingapp.Adapter;

import android.content.Context;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;

import com.example.studenttrackingapp.Topic;

import java.util.List;

public class TopicAdapter extends ArrayAdapter<Topic> {
    private Context context;
    private List<Topic> topicList;

    public TopicAdapter(Context context, List<Topic> topicList) {
        super(context, 0, topicList);
        this.context = context;
        this.topicList = topicList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        Topic topic = topicList.get(position);
        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(topic.getName());

        return convertView;
    }
}

