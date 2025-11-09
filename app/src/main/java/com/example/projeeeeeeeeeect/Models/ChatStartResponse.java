package com.example.projeeeeeeeeeect.Models;

import com.google.gson.annotations.SerializedName;

public class ChatStartResponse {
    @SerializedName("sessionToken") // The token for this specific chat
    public String sessionToken;
    @SerializedName("channelUrl") // The URL for this chat channel
    public String channelUrl;
}
