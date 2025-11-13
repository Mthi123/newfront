package com.example.projeeeeeeeeeect.Models;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

public class StreamUser {

    @SerializedName("user_id")
    private String userId;

    @SerializedName("nickname")
    private String nickname;

    @SerializedName("role_id")
    private int roleId;

    @SerializedName("is_anonymous")
    private boolean isAnonymous;

    @SerializedName("avatar_url")
    private String avatarUrl;

    @SerializedName("metadata")
    private Map<String, Object> metadata;

    // --- Getters (Fetchers) ---
    public String getUserId() {
        return userId;
    }

    public String getNickname() {
        return nickname;
    }

    public int getRoleId() {
        return roleId;
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }
}