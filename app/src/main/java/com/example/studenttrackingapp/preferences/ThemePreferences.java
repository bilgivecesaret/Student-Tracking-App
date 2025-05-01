package com.example.studenttrackingapp.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class ThemePreferences {

    private static final String PREF_NAME = "theme_preferences";
    private static final String KEY_THEME = "theme";

    public static void setTheme(Context context, String theme) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_THEME, theme);
        editor.apply();
    }

    public static String getTheme(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_THEME, "light");  // Varsayılan olarak "light" teması
    }
}

