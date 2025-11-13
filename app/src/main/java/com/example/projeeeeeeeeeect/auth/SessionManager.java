package com.example.projeeeeeeeeeect.auth;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.core.util.Consumer; // Import Consumer for the callback

import com.example.projeeeeeeeeeect.GbvApp;
import com.example.projeeeeeeeeeect.Models.StreamUser; // Your custom user model

import java.util.HashMap;
import java.util.Map;

import io.getstream.chat.android.client.ChatClient;
import io.getstream.chat.android.models.User; // <-- CORRECTED IMPORT for connecting the user
import io.getstream.chat.android.models.ConnectionData;
import io.getstream.chat.android.client.token.TokenProvider;
import io.getstream.result.Result; // The current Result class
import io.getstream.result.call.Call;

public class SessionManager {

    private static final String PREF_NAME = "AppSession";
    private static final String KEY_AUTH_TOKEN = "auth_token";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_ROLE_ID = "role_id";

    private final Context context;
    private final SharedPreferences pref;
    private final SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        this.context = context.getApplicationContext();
        this.pref = this.context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.editor = pref.edit();
    }

    // --- Auth Token ---
    public void saveAuthToken(String token) {
        editor.putString(KEY_AUTH_TOKEN, token);
        editor.apply();
        Log.d("SessionManager", "New Stream token saved.");
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
        // Returns -1 if no user ID is saved.
        return pref.getInt(KEY_USER_ID, -1);
    }

    // --- Role ID ---
    public void saveRoleId(int roleId) {
        editor.putInt(KEY_ROLE_ID, roleId);
        editor.apply();
    }

    public int getRoleId() {
        // Returns -1 if no role ID is saved.
        return pref.getInt(KEY_ROLE_ID, -1);
    }

    // --- Connect to Stream Chat (Updated Method) ---
    public void connectStreamUser(StreamUser streamUser) {
        ChatClient chatClient = ((GbvApp) context).getChatClient();

        // 1. Prepare extra metadata
        Map<String, Object> extraData = new HashMap<>();
        extraData.put("role_id", streamUser.getRoleId());
        extraData.put("is_anonymous", streamUser.isAnonymous());
        if (streamUser.getMetadata() != null) {
            extraData.putAll(streamUser.getMetadata());
        }

        // 2. Create the User object directly (no more UserInfo)
        User user = new User.Builder()
                .withId("id")
                .withName("name")
                .withImage("http://...")
                .withExtraData(extraData)
                .build();


        // 3. Implement the TokenProvider
        TokenProvider tokenProvider = () -> {
            Log.d("TokenProvider", "SDK requested a token. Loading from session.");
            String token = getAuthToken();
            return (token != null) ? token : ""; // Must not return null
        };

        // 4. Connect the user with the User object and TokenProvider
        Call<ConnectionData> call = chatClient.connectUser(user, tokenProvider);
        call.enqueue(result -> {
            if (result.isSuccess()) {
                ConnectionData data = result.getOrNull();
                if (data != null) {
                    User loggedInUser = data.getUser();
                    String connectionId = data.getConnectionId();
                    Log.d("StreamConnect", "Successfully connected as " + loggedInUser.getName() + " with cid: " + connectionId);
                }
            } else {
                Log.e("StreamConnect", "Initial connection failed: " + result.errorOrNull());
            }
        });
    }

    // --- Disconnect from Stream Chat ---
    public void disconnectStreamUser() {
        ChatClient chatClient = ChatClient.instance();
        chatClient.disconnect(true).enqueue(result -> {
            if (result.isSuccess()) {
                Log.d("StreamConnect", "Successfully disconnected.");
            } else {
                Log.e("StreamConnect", "Failed to disconnect: " + result.errorOrNull().getMessage());
            }
        });
    }

    // --- Clear Session ---
    public void clearSession() {
        editor.clear();
        editor.apply();
    }
}
