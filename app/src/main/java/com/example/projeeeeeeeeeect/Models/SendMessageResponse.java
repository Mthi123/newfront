package com.example.projeeeeeeeeeect.Models;

import com.google.gson.annotations.SerializedName;

/**
 * (UPDATED!) This is the top-level response class for sending a message.
 * It contains the nested "message" object.
 */
public class SendMessageResponse {
    @SerializedName("message")
    public SendBirdMessage message;
}
