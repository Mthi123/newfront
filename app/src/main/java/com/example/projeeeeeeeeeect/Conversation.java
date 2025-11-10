package com.example.projeeeeeeeeeect;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projeeeeeeeeeect.Adapters.ChatAdapter;
import com.example.projeeeeeeeeeect.auth.SessionManager;
import com.example.projeeeeeeeeeect.Models.Message;
import com.example.projeeeeeeeeeect.Models.MessageHistoryResponse;
import com.example.projeeeeeeeeeect.Models.SendBirdMessage;
import com.example.projeeeeeeeeeect.Models.SendMessageRequest;
import com.example.projeeeeeeeeeect.Models.SendMessageResponse;
import com.example.projeeeeeeeeeect.network.ApiService;
import com.example.projeeeeeeeeeect.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Conversation extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText etMessage;
    private Button btnSend;
    private ChatAdapter chatAdapter;
    private List<Message> messageList;
    private String counselorName;

    private SessionManager sessionManager;
    private String sessionToken;
    private String channelUrl;
    private int currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        sessionManager = new SessionManager(this);
        currentUserId = sessionManager.getUserId();

        recyclerView = findViewById(R.id.recyclerChat);
        etMessage = findViewById(R.id.etMessage);
        btnSend = findViewById(R.id.btnSend);

        // Retrieve chat tokens/URL from Intent
        sessionToken = getIntent().getStringExtra("sessionToken");
        channelUrl = getIntent().getStringExtra("channelUrl");

        counselorName = getIntent().getStringExtra("counselorName");
        setTitle("Chat with " + (counselorName != null ? counselorName : "Counselor"));

        messageList = new ArrayList<>();
        chatAdapter = new ChatAdapter(messageList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(chatAdapter);

        // --- DYNAMIC/MOCK LOADING LOGIC ---
        if (channelUrl != null && sessionToken != null) {
            // Attempt to load real data
            loadMessageHistory(channelUrl);
            btnSend.setEnabled(true);
        } else {
            // Fallback to minimal mock conversation setup
            loadMockConversation();
            btnSend.setEnabled(true); // <--- ENABLED FOR RESPONSIVE MOCKING
        }

        // Send button logic - handles real API call or mock reply
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = etMessage.getText().toString().trim();

                if (TextUtils.isEmpty(messageText)) {
                    return;
                }

                // If real tokens exist, attempt API send
                if (sessionToken != null && channelUrl != null) {
                    sendMessageApi(messageText);
                } else {
                    // DEMO MODE: Handle response immediately
                    messageList.add(new Message(messageText, true, System.currentTimeMillis()));
                    chatAdapter.notifyItemInserted(messageList.size() - 1);
                    recyclerView.scrollToPosition(messageList.size() - 1);
                    handleMockReply(messageText);
                }
                etMessage.setText("");
            }
        });
    }

    // --- NEW: Minimal Mock Conversation for Initial Display ---
    private void loadMockConversation() {
        messageList.clear();
        setTitle("Chat with Counselor Alex (Demo)");
        Toast.makeText(this, "Demo Mode: Responsive mock chat enabled.", Toast.LENGTH_LONG).show();
    }

    // --- NEW: Contextual Mock Reply Handler ---
    private void handleMockReply(String userMessage) {
        String reply;
        String lowerCaseMessage = userMessage.toLowerCase();

        if (lowerCaseMessage.contains("hi") || lowerCaseMessage.contains("hello")) {
            reply = "Hello! I'm Counselor Alex. Thank you for reaching out to us. How can I help you today?";
        } else if (lowerCaseMessage.contains("scared") || lowerCaseMessage.contains("afraid")) {
            reply = "I understand. Please know that you are safe here. We can discuss your feelings without pressure.";
        } else if (lowerCaseMessage.contains("report") || lowerCaseMessage.contains("case")) {
            reply = "I see your case details. We can walk through the next steps together, whether it's medical, legal, or emotional support.";
        } else if (lowerCaseMessage.contains("thanks") || lowerCaseMessage.contains("thank you")) {
            reply = "You're welcome. Taking this step is incredibly brave.";
        } else {
            reply = "I'm listening closely. Please tell me more about what's on your mind right now.";
        }

        long now = System.currentTimeMillis();
        // Simulate a slight delay for realism
        new android.os.Handler().postDelayed(() -> {
            messageList.add(new Message(reply, false, now));
            chatAdapter.notifyItemInserted(messageList.size() - 1);
            recyclerView.scrollToPosition(messageList.size() - 1);
        }, 800); // 0.8 second delay
    }
    // -------------------------------------------------------------

    private void loadMessageHistory(String channelUrl) {
        ApiService apiService = RetrofitClient.getApiService(this);
        Call<MessageHistoryResponse> call = apiService.getMessageHistory(channelUrl);

        call.enqueue(new Callback<MessageHistoryResponse>() {
            @Override
            public void onResponse(Call<MessageHistoryResponse> call, Response<MessageHistoryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<SendBirdMessage> history = response.body().messages;

                    for (SendBirdMessage apiMessage : history) {
                        boolean isSentByCurrentUser = apiMessage.user.userId.equals(String.valueOf(currentUserId));

                        messageList.add(new Message(
                                apiMessage.message,
                                isSentByCurrentUser,
                                apiMessage.createdAt
                        ));
                    }

                    chatAdapter.notifyDataSetChanged();
                    recyclerView.scrollToPosition(messageList.size() - 1);

                } else {
                    Toast.makeText(Conversation.this, "Failed to load chat history. Code: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MessageHistoryResponse> call, Throwable t) {
                Toast.makeText(Conversation.this, "Network Error loading history: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void sendMessageApi(String messageText) {
        SendMessageRequest request = new SendMessageRequest(sessionToken, channelUrl, messageText);
        ApiService apiService = RetrofitClient.getApiService(this);
        Call<SendMessageResponse> call = apiService.sendMessage(request);

        call.enqueue(new Callback<SendMessageResponse>() {
            @Override
            public void onResponse(Call<SendMessageResponse> call, Response<SendMessageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    messageList.add(new Message(messageText, true, System.currentTimeMillis()));
                    chatAdapter.notifyItemInserted(messageList.size() - 1);
                    recyclerView.scrollToPosition(messageList.size() - 1);

                    Toast.makeText(Conversation.this, "Message sent.", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(Conversation.this, "Failed to send message. Code: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SendMessageResponse> call, Throwable t) {
                Toast.makeText(Conversation.this, "Network Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}