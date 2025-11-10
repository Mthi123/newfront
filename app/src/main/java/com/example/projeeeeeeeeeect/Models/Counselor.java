package com.example.projeeeeeeeeeect.Models;

import java.io.Serializable;

/**
 * Represents a Counselor/User object for assignment purposes.
 * Note: We rely on the existing User model structure but define this separately for clarity.
 */
public class Counselor implements Serializable {
    public int id;
    public String name;
    public String email;

    // Default constructor for Gson
    public Counselor() {}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // Required for the spinner to display the counselor's name
    @Override
    public String toString() {
        return name + " (" + email + ")";
    }
}