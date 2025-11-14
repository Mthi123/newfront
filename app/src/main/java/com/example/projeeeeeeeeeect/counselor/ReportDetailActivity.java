package com.example.projeeeeeeeeeect.counselor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

// Import your new Kotlin Activity
//import com.example.projeeeeeeeeeect.ConversationActivity;

//import com.example.projeeeeeeeeeect.GbvApp;
import com.example.projeeeeeeeeeect.Models.ChatStartRequest;
import com.example.projeeeeeeeeeect.Models.ChatStartResponse;
import com.example.projeeeeeeeeeect.network.ApiService;
import com.example.projeeeeeeeeeect.network.RetrofitClient;

// Stream Imports
//import io.getstream.chat.android.client.ChatClient;
//import io.getstream.chat.android.models.User;
//import io.getstream.result.Error; // Import for error handling
//import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projeeeeeeeeeect.R;
import com.example.projeeeeeeeeeect.Models.Report;
import com.example.projeeeeeeeeeect.auth.SessionManager;

public class ReportDetailActivity extends AppCompatActivity {

    private TextView tvReportId, tvIncidentType, tvLocation, tvDescription, tvDateOfIncident, tvDateReported, tvStatus;
    private Report selectedReport;
    private SessionManager sessionManager;

    public static final String EXTRA_REPORT = "extra_report_object";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reports);

        sessionManager = new SessionManager(this);

        // Initialize TextViews
        tvReportId = findViewById(R.id.tvReportId);
        tvIncidentType = findViewById(R.id.tvIncidentType);
        tvLocation = findViewById(R.id.tvLocation);
        tvDescription = findViewById(R.id.tvDescription);
        tvDateOfIncident = findViewById(R.id.tvDateOfIncident);
        tvDateReported = findViewById(R.id.tvDateReported);
        tvStatus = findViewById(R.id.tvStatus);

        // Get data from Intent
        selectedReport = (Report) getIntent().getSerializableExtra(EXTRA_REPORT);

        if (selectedReport != null) {
            populateView(selectedReport);
        } else {
            Toast.makeText(this, "Error: Report details not found.", Toast.LENGTH_LONG).show();
            finish();
            return; // Return here to avoid a crash if selectedReport is null
        }

        // --- Chat logic is now commented out ---
        /*
        Button btnStartChat = findViewById(R.id.btnStartChat);
        btnStartChat.setOnClickListener(v -> {
            if (selectedReport == null) {
                Toast.makeText(this, "No report loaded", Toast.LENGTH_SHORT).show();
                return;
            }

            // 1. Get IDs for your backend
            int myCounselorId = sessionManager.getUserId();
            int userToChatWithId = selectedReport.getUserId();
            int reportId = selectedReport.getId();

            Log.d("ChatFlow", "Starting chat for report " + reportId + ". Counselor: " + myCounselorId + ", User: " + userToChatWithId);

            // 2. Create the request for YOUR backend
            ChatStartRequest request = new ChatStartRequest(myCounselorId, userToChatWithId, reportId);

            ApiService apiService = RetrofitClient.getApiService(this);
            ChatClient client = ((GbvApp) getApplication()).getChatClient();

            // 3. Call YOUR backend's /api/chat/start endpoint
            apiService.startChat(request).enqueue(new Callback<ChatStartResponse>() {
                @Override
                public void onResponse(Call<ChatStartResponse> call, Response<ChatStartResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        // 4. Get Stream details from your backend's response
                        ChatStartResponse chatResponse = response.body();
                        String streamToken = chatResponse.getSessionToken();
                        String streamId = chatResponse.getStreamId(); // The ID Stream knows this user by
                        String channelId = chatResponse.getChannelId(); // The channel to join

                        Log.d("ChatFlow", "Got Stream token for user: " + streamId);

                        // 5. Create the Stream User object
                        User user = new User.Builder()
                                .withId(streamId)
                                .withName("Counselor Name") // TODO: Get the user's name from SessionManager
                                .build();

                        // 6. Connect to the Stream SDK
                        client.connectUser(user, streamToken).enqueue(result -> {
                            if (result.isSuccess()) {
                                // 7. Success! Open the ConversationActivity
                                Log.i("ChatFlow", "Stream connected! Opening channel: " + channelId);

                                // Start your new Kotlin Activity
                                Intent intent = new Intent(ReportDetailActivity.this, ConversationActivity.class);
                                intent.putExtra("channelId", channelId); // Pass the channelId
                                startActivity(intent);
                            } else {
                                // *** THIS IS THE CORRECTED ERROR HANDLING ***
                                Error error = result.errorOrNull();
                                String errorMessage = (error != null) ? error.getMessage() : "Unknown connection error";

                                Log.e("ChatFlow", "Stream SDK connection failed: " + errorMessage);
                                Toast.makeText(ReportDetailActivity.this, "Chat connection failed.", Toast.LENGTH_SHORT).show();
                            }
                        });

                    } else {
                        Log.e("ChatFlow", "API call failed: " + response.code());
                        Toast.makeText(ReportDetailActivity.this, "Failed to start chat session.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ChatStartResponse> call, Throwable t) {
                    Log.e("ChatFlow", "API call to /api/chat/start failed: " + t.getMessage(), t);
                    Toast.makeText(ReportDetailActivity.this, "Network Error.", Toast.LENGTH_SHORT).show();
                }
            });
        });
        */
    }

    private void populateView(Report report) {
        setTitle("Report #" + report.getId());

        String incidentType = (report.getIncidentType() != null) ? report.getIncidentType().getName() : "N/A";
        String statusType = (report.getStatusType() != null) ? report.getStatusType().getName() : "N/A";

        tvReportId.setText("Report ID: " + report.getId() + " | Filed by User: " + report.getUserId());
        tvIncidentType.setText(incidentType);
        tvLocation.setText(report.getLocation());
        tvDescription.setText(report.getDescription());
        tvDateOfIncident.setText(report.getDateOfIncident());
        tvDateReported.setText(report.getDateReported());
        tvStatus.setText(statusType);
    }
}