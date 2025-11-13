package com.example.projeeeeeeeeeect;

import android.app.Application;
import io.getstream.chat.android.client.ChatClient;
import io.getstream.chat.android.client.logger.ChatLogLevel;

public class GbvApp extends Application {
    private ChatClient chatClient;

    @Override
    public void onCreate() {
        super.onCreate();

        chatClient = new ChatClient.Builder("dds7ycmumf68", this)
                .logLevel(ChatLogLevel.ALL) // Optional: for debugging
                .build();
    }

    public ChatClient getChatClient() {
        return chatClient;
    }
}