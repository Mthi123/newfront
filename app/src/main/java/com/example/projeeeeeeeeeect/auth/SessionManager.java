package com.example.projeeeeeeeeeect.auth;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF_NAME = "AppSession";
    private static final String KEY_AUTH_TOKEN = "auth_token";
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    // We'll need the application context
    public SessionManager(Context context) {
        pref = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void saveAuthToken(String token) {
        editor.putString(KEY_AUTH_TOKEN, token);
        editor.apply();
    }

    public String getAuthToken() {
        return pref.getString(KEY_AUTH_TOKEN, null);
    }
}