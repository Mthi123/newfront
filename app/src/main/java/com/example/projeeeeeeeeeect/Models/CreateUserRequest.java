package com.example.projeeeeeeeeeect.Models;

public class CreateUserRequest {
    String name;
    String email;
    String phone;
    String role;
    String password;

    public CreateUserRequest(String name, String email, String phone, String role, String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.password = password;
    }
}