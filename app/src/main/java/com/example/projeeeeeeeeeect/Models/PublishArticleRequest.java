package com.example.projeeeeeeeeeect.Models;

public class PublishArticleRequest {
    String title;
    String content;
    // Assuming the API expects the resource to be published by the logged-in user
    // or implicitly derives the counselor_id from the session token.

    public PublishArticleRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}