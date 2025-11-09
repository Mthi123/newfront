package com.example.projeeeeeeeeeect.Models;



public class Resource {
    private String title;
    private String content; // could also be a URL if linking externally

    public Resource(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() { return title; }
    public String getContent() { return content; }
}
