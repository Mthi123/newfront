package com.example.projeeeeeeeeeect.Models;

import com.google.gson.annotations.SerializedName;

public class ChatStartRequest {
    @SerializedName("userId") // ID of the user starting the chat (you)
    int userId;
    @SerializedName("counselorId") // ID of the counselor
    int counselorId;
    @SerializedName("reportId")
    int reportId;

    public ChatStartRequest(int userId, int counselorId, int reportId) {
        this.userId = userId;
        this.counselorId = counselorId;
        this.reportId = reportId;
    }
}
