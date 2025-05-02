package com.example.studenttrackingapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

/**
 * Konu listesini gösterir, ekler, düzenler ve siler.
 *  - Uzun basma: Edit / Delete menüsü
 *  - Kısa tıklama: Bilgilendirme toast’ı
 */
public class TopicsListActivity extends AppCompatActivity {

    private String bookTitle;
    private ListView topicsListView;
    private Button addTopicButton;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics_list);

        topicsListView = findViewById(R.id.topicsListView);
        addTopicButton  = findViewById(R.id.addTopicButton);

        bookTitle = getIntent().getStringExtra("bookTitle");
        if (bookTitle == null || bookTitle.isEmpty()) {
            Toast.makeText(this, "Book title missing", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Özel satır tasarımı kullan → long‑click sorunsuz çalışır
        adapter = new ArrayAdapter<>(this,
                R.layout.list_item_topic,               // satır layout
                R.id.topicText);                        // TextView id
        topicsListView.setAdapter(adapter);
        topicsListView.setLongClickable(true);          // garanti

        refreshTopicList();

        addTopicButton.setOnClickListener(v -> showAddTopicDialog());

        // ↪︎ Kısa tıklama (bilgi amaçlı)
        topicsListView.setOnItemClickListener((p, v, pos, id) ->
                Toast.makeText(this, "Clicked: " + adapter.getItem(pos), Toast.LENGTH_SHORT).show());

        // ↪︎ Uzun tıklama (Edit / Delete)
        topicsListView.setOnItemLongClickListener((p, v, pos, id) -> {
            String topic = adapter.getItem(pos);
            new AlertDialog.Builder(this)
                    .setTitle("Modify Topic")
                    .setItems(new CharSequence[]{"Edit", "Delete"}, (dlg, which) -> {
                        if (which == 0) {                // Edit
                            EditText in = new EditText(this);
                            in.setText(topic);
                            new AlertDialog.Builder(this)
                                    .setTitle("Edit Topic")
                                    .setView(in)
                                    .setPositiveButton("Save", (d, w) -> {
                                        String newName = in.getText().toString().trim();
                                        if (!newName.isEmpty()) {
                                            DataRepository.getInstance()
                                                    .updateTopic(bookTitle, topic, newName);
                                            refreshTopicList();
                                        }
                                    })
                                    .setNegativeButton("Cancel", null)
                                    .show();
                        } else {                         // Delete
                            DataRepository.getInstance()
                                    .deleteTopic(bookTitle, topic);
                            refreshTopicList();
                        }
                    }).show();
            return true;
        });
    }

    /** Listeyi DAO’dan (DataRepository) tazeleyip adapter’a aktarır */
    private void refreshTopicList() {
        adapter.clear();
        List<String> topics = DataRepository.getInstance()
                .getTopicsForBook(bookTitle);
        adapter.addAll(topics);
    }

    /** Yeni konu ekleme diyaloğu */
    private void showAddTopicDialog() {
        EditText input = new EditText(this);
        input.setHint("Enter topic name");

        new AlertDialog.Builder(this)
                .setTitle("Add New Topic")
                .setView(input)
                .setPositiveButton("Add", (d, w) -> {
                    String topic = input.getText().toString().trim();
                    if (!topic.isEmpty()) {
                        DataRepository.getInstance()
                                .addTopicToBook(bookTitle, topic);
                        refreshTopicList();
                    } else
                        Toast.makeText(this,"Topic cannot be empty",
                                Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
