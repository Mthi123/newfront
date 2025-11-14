package com.example.projeeeeeeeeeect.Models;

import com.google.gson.annotations.SerializedName;

public class ChatStartResponse {

    @SerializedName("channelId")
    public String channelId;

    @SerializedName("sessionToken")
    public String sessionToken;

    @SerializedName("streamId")
    public String streamId;

    @SerializedName("streamApiKey")
    public String streamApiKey;

    // Add Getters for these fields
    public String getChannelId() {
        return channelId;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public String getStreamId() {
        return streamId;
    }

    public String getStreamApiKey() {
        return streamApiKey;
    }
}