package com.example.projeeeeeeeeeect.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projeeeeeeeeeect.R;
import com.example.projeeeeeeeeeect.Articles;
import com.example.projeeeeeeeeeect.admin.mainbtns.AdminManageCounselorsActivity;
import com.example.projeeeeeeeeeect.admin.mainbtns.AdminReportStatsActivity;

public class AdminDashboardActivity extends AppCompatActivity {

    private Button viewUsersBtn, viewReportsBtn, manageCounselorsBtn, viewResourcesBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        viewUsersBtn = findViewById(R.id.viewUsersBtn);
        viewReportsBtn = findViewById(R.id.viewReportsBtn);
        manageCounselorsBtn = findViewById(R.id.manageCounselorsBtn);
        viewResourcesBtn = findViewById(R.id.viewResourcesBtn);

        // TODO: Implement actual logic for each button
        viewUsersBtn.setOnClickListener(v -> {
            // Open activity to view all users
            Intent intent = new Intent(this, AdminViewUsersActivity.class);
            startActivity(intent);
        });

        viewReportsBtn.setOnClickListener(v -> {
            // Open activity to view report statistics
            Intent intent = new Intent(this, AdminReportStatsActivity.class);
            startActivity(intent);
        });

        // --- UPDATED LOGIC: Launch AdminManageCounselorsActivity ---
        manageCounselorsBtn.setOnClickListener(v -> {
            // Open activity to manage counselors/NGOs (Assignment screen)
            Intent intent = new Intent(this, AdminManageCounselorsActivity.class);
            startActivity(intent);
        });
        // -----------------------------------------------------------

        viewResourcesBtn.setOnClickListener(v -> {
            // Open activity to view all resources
            Intent intent = new Intent(this, Articles.class); // reusing user resource view
            startActivity(intent);
        });
    }
}