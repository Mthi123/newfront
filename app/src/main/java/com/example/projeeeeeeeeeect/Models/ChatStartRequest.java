package com.example.projeeeeeeeeeect.Models;

import com.google.gson.annotations.SerializedName;

public class ChatStartRequest {

    @SerializedName("userId")
    int userId;

    @SerializedName("otherUserId")
    int otherUserId;

    @SerializedName("reportId")
    int reportId;

    public ChatStartRequest(int userId, int otherUserId, int reportId) {
        this.userId = userId;
        this.otherUserId = otherUserId;
        this.reportId = reportId;
    }
}