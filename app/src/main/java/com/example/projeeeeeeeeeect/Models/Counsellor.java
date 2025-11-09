package com.example.projeeeeeeeeeect.Models;

import com.google.gson.annotations.SerializedName;

public class Counsellor {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return name; // For spinner display
    }
}
