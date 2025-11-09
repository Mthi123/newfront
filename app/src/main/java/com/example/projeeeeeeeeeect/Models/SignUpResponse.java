package com.example.projeeeeeeeeeect.Models;

import com.google.gson.annotations.SerializedName;

public class SignUpResponse {
    @SerializedName("message")
    private String message;

    @SerializedName("userId")
    private int userId;

    @SerializedName("token")
    private String token;

    public String getMessage() { return message; }
    public int getUserId() { return userId; }
    public String getToken() { return token; }
}

