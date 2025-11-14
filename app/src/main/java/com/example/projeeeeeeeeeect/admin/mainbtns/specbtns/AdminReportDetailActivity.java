package com.example.projeeeeeeeeeect.admin.mainbtns.specbtns;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projeeeeeeeeeect.R;
import com.example.projeeeeeeeeeect.Models.Report;

import java.io.Serializable;

public class AdminReportDetailActivity extends AppCompatActivity {

    private TextView tvReportId, tvIncidentType, tvLocation, tvDescription, tvDateOfIncident, tvDateReported, tvStatus;
    private Button btnAction;
    private Report selectedReport;

    public static final String EXTRA_REPORT = "extra_report_object";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reports);

        // Initialize TextViews
        tvReportId = findViewById(R.id.tvReportId);
        tvIncidentType = findViewById(R.id.tvIncidentType);
        tvLocation = findViewById(R.id.tvLocation);
        tvDescription = findViewById(R.id.tvDescription);
        tvDateOfIncident = findViewById(R.id.tvDateOfIncident);
        tvDateReported = findViewById(R.id.tvDateReported);
        tvStatus = findViewById(R.id.tvStatus);
        btnAction = findViewById(R.id.btnStartChat); // Reusing the button ID from the layout

        // Get data from Intent
        selectedReport = (Report) getIntent().getSerializableExtra(EXTRA_REPORT);

        if (selectedReport != null) {
            populateView(selectedReport);
        } else {
            Toast.makeText(this, "Error: Report details not found.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // --- ADMIN-SPECIFIC LOGIC: ASSIGN COUNSELLOR ---
        btnAction.setText("Assign to Counsellor");

        btnAction.setOnClickListener(v -> {
            // Open the AssignCounsellorToReportActivity
            Intent intent = new Intent(AdminReportDetailActivity.this, AssignCounsellorToReportActivity.class);
            intent.putExtra("reportId", selectedReport.getId());
            startActivity(intent);
        });
    }

    private void populateView(Report report) {
        setTitle("Admin - Report #" + report.getId());

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