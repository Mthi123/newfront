package com.example.projeeeeeeeeeect.Models;

import com.google.gson.annotations.SerializedName;

// Minimal response model for success confirmation
public class PublishArticleResponse {
    String message;

    // Assuming the API returns a full Resource object or just confirmation
    // If it returns a full Resource object, we could reuse the existing Resource model
    @SerializedName("id")
    int id;

    public String getMessage() {
        return message;
    }
}