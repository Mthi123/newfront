package com.example.projeeeeeeeeeect.counselor;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

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
        }
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

