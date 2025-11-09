package com.example.projeeeeeeeeeect.Models;

import com.google.gson.annotations.SerializedName;

// --- API 3: Send Message ---
public class SendMessageRequest {
    @SerializedName("sessionToken")
    String sessionToken;
    @SerializedName("channelUrl")
    String channelUrl;
    @SerializedName("message")
    String message;

    public SendMessageRequest(String sessionToken, String channelUrl, String message) {
        this.sessionToken = sessionToken;
        this.channelUrl = channelUrl;
        this.message = message;
    }
}
