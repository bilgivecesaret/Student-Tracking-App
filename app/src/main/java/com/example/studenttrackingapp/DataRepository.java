package com.example.studenttrackingapp;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.*;

/** Uygulama genelinde veri tutar + SharedPreferences ile kalıcı saklar. */
public class DataRepository {

    // ────────── Singleton ──────────
    private static DataRepository instance;
    private SharedPreferences prefs;
    private static final String PREF_NAME = "student_tracking_prefs";

    public static void init(Context ctx) {   // TeacherActivity → onCreate’de çağır
        if (instance == null) instance = new DataRepository(ctx.getApplicationContext());
    }
    public static DataRepository getInstance() { return instance; }

    private DataRepository(Context ctx) { prefs = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE); }

    // ────────── Kitap listesi (in‑memory) ──────────
    private final List<Book> books = new ArrayList<>();

    // =========================================================
    //  KİTAP İŞLEMLERİ
    // =========================================================
    public List<Book> getBooks() { return books; }

    public void addBook(Book book) {
        if (!books.contains(book)) books.add(book);
        // kitap için prefs anahtarı yoksa boş set oluştur
        prefs.edit().putStringSet(topicsKey(book.getTitle()), new HashSet<>()).apply();
    }

    public void removeBook(int index) {
        if (index >= 0 && index < books.size()) {
            Book removed = books.remove(index);
            prefs.edit().remove(topicsKey(removed.getTitle())).apply();
        }
    }

    // =========================================================
    //  TOPIC İŞLEMLERİ (Kalıcı)
    // =========================================================
    public List<String> getTopicsForBook(String book) {
        Set<String> set = prefs.getStringSet(topicsKey(book), new HashSet<>());
        return new ArrayList<>(set);              // ListView için kopya Liste
    }

    public void addTopicToBook(String book, String topic) {
        modifyTopics(book, set -> set.add(topic));
    }

    public void updateTopic(String book, String oldName, String newName) {
        modifyTopics(book, set -> {
            if (set.remove(oldName)) set.add(newName);
        });
    }

    public void deleteTopic(String book, String topic) {
        modifyTopics(book, set -> set.remove(topic));
    }

    // =========================================================
    //  Yardımcı ↓
    // =========================================================
    private String topicsKey(String book) { return "topics_" + book; }

    /** SharedPreferences içindeki Set<String>’i okur, lambda ile değiştirir, geri kaydeder */
    private void modifyTopics(String book, java.util.function.Consumer<Set<String>> action) {
        String key = topicsKey(book);
        // prefs’ten MUTABLE kopya almak için yeni HashSet oluştur
        Set<String> set = new HashSet<>(prefs.getStringSet(key, new HashSet<>()));
        action.accept(set);
        prefs.edit().putStringSet(key, set).apply();
    }

    // =========================================================
    //  Öğrenci‑Konu eşlemesi (mevcut kodun aynısı)
    // =========================================================
    private final Map<String, List<StudentTopicAssignment>> studentTopicMap = new HashMap<>();

    public void assignTopicToStudent(String student, String topic,
                                     String book, String dateRange, boolean completed) {
        List<StudentTopicAssignment> list = studentTopicMap.getOrDefault(student, new ArrayList<>());
        list.add(new StudentTopicAssignment(book, topic, dateRange, completed));
        studentTopicMap.put(student, list);
    }
    public List<StudentTopicAssignment> getAssignmentsForStudent(String student) {
        return studentTopicMap.getOrDefault(student, new ArrayList<>());
    }

    // ────────── Nested sınıf değişmedi ──────────
    public static class StudentTopicAssignment {
        public String book, topic, dateRange; public boolean isCompleted;
        public StudentTopicAssignment(String b,String t,String d,boolean c){
            book=b; topic=t; dateRange=d; isCompleted=c;
        }
        @Override public String toString() {
            return topic+" ("+book+") - "+dateRange+(isCompleted?" ✓":" ✗");
        }
    }
}
