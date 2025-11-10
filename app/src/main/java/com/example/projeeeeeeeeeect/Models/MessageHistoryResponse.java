package com.example.projeeeeeeeeeect.Models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Model to wrap the list of messages returned by the API for history.
 */
public class MessageHistoryResponse {

    // Assuming the API returns a list of SendBirdMessage objects under a "messages" key.
    @SerializedName("messages")
    public List<SendBirdMessage> messages;

    // You might add fields here for pagination (e.g., "next_token") if the API supports it.
}