package com.example.projeeeeeeeeeect.Models;



public class Message {
    private String text;
    private boolean isSentByUser;
    private long timestamp;

    public Message(String text, boolean isSentByUser, long timestamp) {
        this.text = text;
        this.isSentByUser = isSentByUser;
        this.timestamp = timestamp;
    }

    public String getText() {
        return text;
    }

    public boolean isSentByUser() {
        return isSentByUser;
    }

    public long getTimestamp() {
        return timestamp;
    }
}

