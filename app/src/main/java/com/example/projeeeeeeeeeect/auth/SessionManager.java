package com.example.projeeeeeeeeeect.auth;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF_NAME = "AppSession";
    private static final String KEY_AUTH_TOKEN = "auth_token";
    private static final String KEY_USER_ID = "user_id"; // <-- NEW CONSTANT
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    // We'll need the application context
    public SessionManager(Context context) {
        pref = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    // --- NEW METHOD: Save User ID (Needed during login) ---
    public void saveUserId(int userId) {
        editor.putInt(KEY_USER_ID, userId);
        editor.apply();
    }

    public void saveAuthToken(String token) {
        editor.putString(KEY_AUTH_TOKEN, token);
        editor.apply();
    }

    public String getAuthToken() {
        return pref.getString(KEY_AUTH_TOKEN, null);
    }

    // --- NEW METHOD: Get User ID (Needed by ReportDetailActivity) ---
    public int getUserId() {
        // Returns -1 if ID is not found (which means the user is likely anonymous or not logged in).
        return pref.getInt(KEY_USER_ID, -1);
    }
}