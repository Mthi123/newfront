package com.example.projeeeeeeeeeect.Models;

import com.example.projeeeeeeeeeect.Models.SendBirdUser;
import com.google.gson.annotations.SerializedName;

/**
 * Represents the main "message" object in the response
 */
public class SendBirdMessage {
    @SerializedName("message_id")
    public long messageId;

    @SerializedName("message")
    public String message; // "Hello, this is a test message, 2"

    @SerializedName("created_at")
    public long createdAt; // 1762638062869

    @SerializedName("user")
    public SendBirdUser user;
}
