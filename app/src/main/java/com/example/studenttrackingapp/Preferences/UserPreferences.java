package com.example.studenttrackingapp.Preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPreferences {

    private static final String PREF_NAME = "user_preferences";
    private static final String KEY_USERNAME = "username"; // Kullanıcı adı için key
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";

    // Kullanıcı adını kaydet
    public static void setUsername(Context context, String username) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERNAME, username);
        editor.apply();
    }

    // Kullanıcı adı al
    public static String getUsername(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, "Unknown");  // Varsayılan olarak "Unknown" döner
    }

    // Kullanıcı girişi durumu
    public static void setLoggedIn(Context context, boolean isLoggedIn) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.apply();
    }

    public static boolean isLoggedIn(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }
}
