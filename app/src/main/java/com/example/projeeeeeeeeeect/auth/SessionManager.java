package com.example.projeeeeeeeeeect.auth;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF_NAME = "AppSession";
    private static final String KEY_AUTH_TOKEN = "auth_token";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_ROLE_ID = "role_id";

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        pref = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    // --- Auth Token ---
    public void saveAuthToken(String token) {
        editor.putString(KEY_AUTH_TOKEN, token);
        editor.apply();
    }

    public String getAuthToken() {
        return pref.getString(KEY_AUTH_TOKEN, null);
    }

    // --- User ID ---
    public void saveUserId(int userId) {
        editor.putInt(KEY_USER_ID, userId);
        editor.apply();
    }

    public int getUserId() {
        return pref.getInt(KEY_USER_ID, -1); // -1 means not set
    }

    // --- Role ID (optional) ---
    public void saveRoleId(int roleId) {
        editor.putInt(KEY_ROLE_ID, roleId);
        editor.apply();
    }

    public int getRoleId() {
        return pref.getInt(KEY_ROLE_ID, -1); // -1 means not set
    }

    // --- Clear Session ---
    public void clearSession() {
        editor.clear();
        editor.apply();
    }
}