package com.example.projeeeeeeeeeect;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import io.getstream.chat.android.ui.feature.channels.list.ChannelListView;

public class ChatListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Set the layout
        setContentView(R.layout.activity_chat_list);

        // 2. Find the ChannelListView
        ChannelListView channelListView = findViewById(R.id.channelListView);

        // 3. Set a click listener. This is the magic!
        // When a user clicks a chat in the list, this code runs.
        channelListView.setChannelItemClickListener(channel -> {

            // Get the ID of the channel that was clicked
            String channelId = channel.getCid();

            // 4. Start ConversationActivity, passing the correct channelId
            // This is what links the list to the chat screen.
            Intent intent = new Intent(ChatListActivity.this, ConversationActivity.class);
            intent.putExtra("channelId", channelId);
            startActivity(intent);
        });
    }
}