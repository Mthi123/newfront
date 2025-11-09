package com.example.projeeeeeeeeeect.Models;

public class UserLoginResponse {
    String token;
    String message;
    User user;

    public User getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }
}
