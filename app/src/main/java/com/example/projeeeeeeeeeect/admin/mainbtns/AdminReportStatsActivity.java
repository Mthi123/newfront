package com.example.projeeeeeeeeeect.admin.mainbtns;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projeeeeeeeeeect.R;

public class AdminReportStatsActivity extends AppCompatActivity {

    // Declare the 4 buttons
    private Button viewAllReportsBtn, reportsByTypeBtn, reportsByLocationBtn, reportsByTimeBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Link this Java file to the new XML layout
        setContentView(R.layout.admin_report_stats);

        // Find the buttons by their IDs from the XML
        viewAllReportsBtn = findViewById(R.id.viewAllReportsBtn);
        reportsByTypeBtn = findViewById(R.id.reportsByTypeBtn);
        reportsByLocationBtn = findViewById(R.id.reportsByLocationBtn);
        reportsByTimeBtn = findViewById(R.id.reportsByStatusBtn);

        // --- Set Click Listeners (with placeholder Toasts for now) ---

        viewAllReportsBtn.setOnClickListener(v -> {
            // TODO: Open a new activity to show a list of all reports
            // Example:
            // Intent intent = new Intent(AdminReportStats.this, ViewAllReportsActivity.class);
            // startActivity(intent);
            Toast.makeText(AdminReportStatsActivity.this, "View All Reports clicked", Toast.LENGTH_SHORT).show();
        });

        reportsByTypeBtn.setOnClickListener(v -> {
            // TODO: Open an activity showing reports grouped by type
            Toast.makeText(AdminReportStatsActivity.this, "Reports by Type clicked", Toast.LENGTH_SHORT).show();
        });

        reportsByLocationBtn.setOnClickListener(v -> {
            // TODO: Open an activity showing reports on a map or by location
            Toast.makeText(AdminReportStatsActivity.this, "Reports by Location clicked", Toast.LENGTH_SHORT).show();
        });

        reportsByTimeBtn.setOnClickListener(v -> {
            // TODO: Open an activity with a chart of reports over time
            Toast.makeText(AdminReportStatsActivity.this, "Reports by Time clicked", Toast.LENGTH_SHORT).show();
        });
    }
}
