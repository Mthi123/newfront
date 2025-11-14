package com.example.projeeeeeeeeeect.counselor;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projeeeeeeeeeect.ChatListActivity;
import com.example.projeeeeeeeeeect.ConversationActivity; // Import the Kotlin activity
import com.example.projeeeeeeeeeect.R;

public class CounsilorDashboard extends AppCompatActivity {

    private Button viewReportsBtn, chatWithUsersBtn, publishResourceBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counsilor_dashboard);

        viewReportsBtn = findViewById(R.id.viewReportsBtn);
        chatWithUsersBtn = findViewById(R.id.chatWithUsersBtn);
        publishResourceBtn = findViewById(R.id.publishResourceBtn);

        // View reports submitted by users
        viewReportsBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, CounsilorViewReports.class);
            startActivity(intent);
        });


        chatWithUsersBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, ChatListActivity.class);
            startActivity(intent); // Pass role so ConversationActivity knows sender type
            // TODO: You MUST pass a valid "channelId" here or ConversationActivity will fail
            intent.putExtra("channelId", "some_valid_channel_id");
            startActivity(intent);
        });

        // Publish resources/articles for users
        publishResourceBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, PublishArticle.class);
            startActivity(intent);
        });
    }
}