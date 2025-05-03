package com.example.studenttrackingapp;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** 🟢  Sadece öğrencinin test bitirme bilgilerini tutar */
public class StudentProgressDAO {

    private static final String PREF = "student_completed_tests";
    private final SharedPreferences prefs;

    public StudentProgressDAO(Context ctx) {
        prefs = ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE);
    }

    /** "öğrenci_adı::test_adı" anahtarı kullanıyoruz */
    private String key(String student, String test) {
        return student + "::" + test;
    }

    /* ----------  API  ---------- */

    /** Test işaretli mi? */
    public boolean isCompleted(String student, String test) {
        return prefs.getBoolean(key(student, test), false);
    }

    /** Testi işaretle / kaldır */
    public void setCompleted(String student, String test, boolean done) {
        prefs.edit().putBoolean(key(student, test), done).apply();
    }

    /** Öğrencinin tamamladığı tüm test adları  */
    public List<String> getAllCompleted(String student) {
        Set<String> allKeys = prefs.getAll().keySet();
        List<String> result = new ArrayList<>();
        for (String k : allKeys)
            if (k.startsWith(student + "::") && prefs.getBoolean(k, false))
                result.add(k.split("::",2)[1]);
        return result;
    }
}
