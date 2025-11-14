package com.example.projeeeeeeeeeect.admin.mainbtns.specbtns;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projeeeeeeeeeect.Adapters.ReportAdapter; // Uses the existing adapter
import com.example.projeeeeeeeeeect.R;
import com.example.projeeeeeeeeeect.Models.Report;
import com.example.projeeeeeeeeeect.network.ApiService;
import com.example.projeeeeeeeeeect.network.RetrofitClient;
import com.example.projeeeeeeeeeect.auth.SessionManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Implements the custom listener defined in ReportAdapter
public class AdminViewReportActivity extends AppCompatActivity implements ReportAdapter.OnItemClickListener {

    private RecyclerView recyclerViewReports;
    private ProgressBar progressBar;
    private ReportAdapter reportAdapter;
    private List<Report> reportList = new ArrayList<>();
    private SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_reports);

        // Initialize views
        recyclerViewReports = findViewById(R.id.recyclerViewReports);
        progressBar = findViewById(R.id.progressBar);
        sessionManager = new SessionManager(this);

        // Setup RecyclerView
        recyclerViewReports.setLayoutManager(new LinearLayoutManager(this));
        // Pass 'this' as the click listener, as we implement the interface below
        reportAdapter = new ReportAdapter(reportList, this);
        recyclerViewReports.setAdapter(reportAdapter);

        // Load data on creation
        loadReports();
    }

    private void loadReports() {
        progressBar.setVisibility(View.VISIBLE);
        String token = sessionManager.getAuthToken();

        if (token == null || token.isEmpty()) {
            Toast.makeText(this, "Authentication failed. Please log in again.", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
            return;
        }

        ApiService apiService = RetrofitClient.getApiService(this);
        String authToken = "Bearer " + token;

        // API call to fetch all reports
        Call<List<Report>> call = apiService.getAllReports(authToken);

        call.enqueue(new Callback<List<Report>>() {
            @Override
            public void onResponse(Call<List<Report>> call, Response<List<Report>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    reportAdapter.setReports(response.body()); // Update the list
                    Toast.makeText(AdminViewReportActivity.this, "Total Reports: " + response.body().size(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AdminViewReportActivity.this, "Failed to load reports. Code: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Report>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(AdminViewReportActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    // Implementation of the ReportAdapter.OnItemClickListener interface
    @Override
    public void onItemClick(Report report) {
        // Launch the Admin Report Detail View (AdminReportDetailActivity)
        Intent intent = new Intent(AdminViewReportActivity.this, AdminReportDetailActivity.class);
        // Pass the entire Report object (must be Serializable, which we fixed earlier)
        intent.putExtra(AdminReportDetailActivity.EXTRA_REPORT, (Serializable) report);
        startActivity(intent);
    }
}