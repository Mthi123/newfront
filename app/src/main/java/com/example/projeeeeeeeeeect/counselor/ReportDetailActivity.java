package com.example.projeeeeeeeeeect.counselor;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projeeeeeeeeeect.R;
import com.example.projeeeeeeeeeect.Conversation;
import com.example.projeeeeeeeeeect.Models.Report;
import com.example.projeeeeeeeeeect.Models.IncidentType;
import com.example.projeeeeeeeeeect.Models.StatusType;


public class ReportDetailActivity extends AppCompatActivity {

    private TextView tvReportId, tvIncidentType, tvLocation, tvDescription, tvDateOfIncident, tvDateReported, tvStatus;
    private Button btnStartChat;

    // Define the keys used for passing Intent data (Must match CounsilorViewReports.java)
    public static final String EXTRA_REPORT = "extra_report_object";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reports);

        // 1. Initialize TextViews and Button
        tvReportId = findViewById(R.id.tvReportId);
        tvIncidentType = findViewById(R.id.tvIncidentType);
        tvLocation = findViewById(R.id.tvLocation);
        tvDescription = findViewById(R.id.tvDescription);
        tvDateOfIncident = findViewById(R.id.tvDateOfIncident);
        tvDateReported = findViewById(R.id.tvDateReported);
        tvStatus = findViewById(R.id.tvStatus);
        btnStartChat = findViewById(R.id.btnStartChat);

        // 2. Get data from Intent
        Report selectedReport = (Report) getIntent().getSerializableExtra(EXTRA_REPORT);

        if (selectedReport != null) {
            populateView(selectedReport);
        } else {
            Toast.makeText(this, "Error: Report details not found.", Toast.LENGTH_LONG).show();
            finish();
        }

        // 3. Setup Chat Button Listener
        btnStartChat.setOnClickListener(v -> {
            // TODO: Replace with logic to initiate chat API call for a specific report
            Intent chatIntent = new Intent(ReportDetailActivity.this, Conversation.class);

            // Assuming you need counselorName, reportId, and optionally userId to start a real chat
            chatIntent.putExtra("counselorName", "User " + selectedReport.getUserId());
            // You would pass the Report ID to the Conversation Activity to manage the chat session
            // chatIntent.putExtra("reportId", selectedReport.getId());

            startActivity(chatIntent);
        });
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

    // --- API Call to start the chat session ---
    private void startChatSession(Report report) {
        // We use the authenticated user's ID as the counselorId
        int counselorId = sessionManager.getUserId(); //

        if (counselorId == -1) {
            Toast.makeText(this, "Error: Counselor ID not found. Please re-login.", Toast.LENGTH_LONG).show();
            return;
        }

        int userId = report.getUserId();
        int reportId = report.getId();

        // 1. Create the request model
        ChatStartRequest request = new ChatStartRequest(userId, counselorId, reportId); //

        // 2. Get the API service
        ApiService apiService = RetrofitClient.getApiService(this); //

        // 3. Make the network call
        Call<ChatStartResponse> call = apiService.startChat(request); //

        call.enqueue(new Callback<ChatStartResponse>() {
            @Override
            public void onResponse(Call<ChatStartResponse> call, Response<ChatStartResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ChatStartResponse chatResponse = response.body(); //

                    Toast.makeText(ReportDetailActivity.this, "Chat session started!", Toast.LENGTH_SHORT).show();

                    // 4. Launch Conversation Activity with the tokens
                    Intent chatIntent = new Intent(ReportDetailActivity.this, Conversation.class); //
                    chatIntent.putExtra("channelUrl", chatResponse.channelUrl);
                    chatIntent.putExtra("sessionToken", chatResponse.sessionToken);
                    chatIntent.putExtra("counselorName", "User " + userId);

                    startActivity(chatIntent);
                } else {
                    Toast.makeText(ReportDetailActivity.this, "Failed to start chat. Code: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ChatStartResponse> call, Throwable t) {
                Toast.makeText(ReportDetailActivity.this, "Network Error: Could not start chat. " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}