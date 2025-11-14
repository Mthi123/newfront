package com.example.projeeeeeeeeeect.admin.mainbtns;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projeeeeeeeeeect.R;
import com.example.projeeeeeeeeeect.admin.mainbtns.specbtns.AdminReportDetailActivity;
import com.example.projeeeeeeeeeect.admin.mainbtns.specbtns.AdminReportsByLocationActivity;
import com.example.projeeeeeeeeeect.admin.mainbtns.specbtns.AdminReportsByStatusActivity;
import com.example.projeeeeeeeeeect.admin.mainbtns.specbtns.AdminReportsByTypeActivity;
import com.example.projeeeeeeeeeect.admin.mainbtns.specbtns.AdminViewReportActivity;

public class AdminReportStatsActivity extends AppCompatActivity {

    // Declare the 4 buttons
    private Button viewAllReportsBtn, reportsByTypeBtn, reportsByLocationBtn, reportsByStatusBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Link this Java file to the new XML layout
        setContentView(R.layout.admin_report_stats);

        // Find the buttons by their IDs from the XML
        viewAllReportsBtn = findViewById(R.id.viewAllReportsBtn);
        reportsByTypeBtn = findViewById(R.id.reportsByTypeBtn);
        reportsByLocationBtn = findViewById(R.id.reportsByLocationBtn);
        reportsByStatusBtn = findViewById(R.id.reportsByStatusBtn);

        // --- Set Click Listeners (with placeholder Toasts for now) ---

        viewAllReportsBtn.setOnClickListener(v -> {
            Intent intent = new Intent(AdminReportStatsActivity.this, AdminViewReportActivity.class);
            startActivity(intent);
            Toast.makeText(AdminReportStatsActivity.this, "View All Reports clicked", Toast.LENGTH_SHORT).show();
        });

        reportsByTypeBtn.setOnClickListener(v -> {
            Intent intent = new Intent(AdminReportStatsActivity.this, AdminReportsByTypeActivity.class);
            startActivity(intent);
            Toast.makeText(AdminReportStatsActivity.this, "Reports by Type clicked", Toast.LENGTH_SHORT).show();
        });

        reportsByLocationBtn.setOnClickListener(v -> {
            Intent intent = new Intent(AdminReportStatsActivity.this, AdminReportsByLocationActivity.class);
            startActivity(intent);
            Toast.makeText(AdminReportStatsActivity.this, "Reports by Location clicked", Toast.LENGTH_SHORT).show();
        });

        reportsByStatusBtn.setOnClickListener(v -> {
            Intent intent = new Intent(AdminReportStatsActivity.this, AdminReportsByStatusActivity.class);
            startActivity(intent);
            Toast.makeText(AdminReportStatsActivity.this, "Reports by Time clicked", Toast.LENGTH_SHORT).show();
        });
    }
}
