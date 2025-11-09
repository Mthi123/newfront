package com.example.projeeeeeeeeeect.counselor;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projeeeeeeeeeect.auth.SessionManager;
import com.example.projeeeeeeeeeect.network.ApiService;
import com.example.projeeeeeeeeeect.network.RetrofitClient;
import com.example.projeeeeeeeeeect.R;
// CORRECTED IMPORT: Import Report from the new Models package
import com.example.projeeeeeeeeeect.Models.Report;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CounsilorViewReports extends AppCompatActivity {
    private ListView reportsListView;
    private List<Report> reportList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counsilor_view_reports);

        reportsListView = findViewById(R.id.reportsListView);

        reportList = new ArrayList<>();

        loadReports();

        reportsListView.setOnItemClickListener((parent, view, position, id) -> {
            Report selectedReport = reportList.get(position);
            // Use public getter methods now that models are separate classes
            String incidentTypeName = (selectedReport.getIncidentType() != null) ? selectedReport.getIncidentType().getName() : "Unknown Type";
            Toast.makeText(this, "Report ID: " + selectedReport.getId() + ", Type: " + incidentTypeName, Toast.LENGTH_LONG).show();
            // TODO: Implement navigation to a detailed report view
        });
    }

    private void loadReports() {
        SessionManager sessionManager = new SessionManager(this);
        String token = "Bearer " + sessionManager.getAuthToken();
        int counselorId = sessionManager.getUserId();// Ensure this returns the correct ID

        if (token == null || counselorId == -1) {
            Toast.makeText(this, "Session expired or invalid. Please log in again.", Toast.LENGTH_LONG).show();
            return;
        }

        ApiService apiService = RetrofitClient.getApiService(this);

        Call<List<Report>> call = apiService.getReportsByCounsellor(token, counselorId);


        call.enqueue(new Callback<List<Report>>() {
            @Override
            public void onResponse(Call<List<Report>> call, Response<List<Report>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    reportList.clear();
                    reportList.addAll(response.body());

                    List<String> displayReports = new ArrayList<>();
                    for (Report report : reportList) {
                        String incidentTypeName = (report.getIncidentType() != null)
                                ? report.getIncidentType().getName() : "Unknown Type";
                        displayReports.add("ID: " + report.getId() + " | " + incidentTypeName + " | Loc: " + report.getLocation());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            CounsilorViewReports.this,
                            android.R.layout.simple_list_item_1,
                            displayReports
                    );
                    reportsListView.setAdapter(adapter);

                    Toast.makeText(CounsilorViewReports.this, "Reports loaded successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CounsilorViewReports.this, "Failed to load reports. Code: " + response.code(), Toast.LENGTH_LONG).show();
                    reportsListView.setAdapter(new ArrayAdapter<>(CounsilorViewReports.this, android.R.layout.simple_list_item_1, new ArrayList<>()));
                }
            }

            @Override
            public void onFailure(Call<List<Report>> call, Throwable t) {
                Toast.makeText(CounsilorViewReports.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                reportsListView.setAdapter(new ArrayAdapter<>(CounsilorViewReports.this, android.R.layout.simple_list_item_1, new ArrayList<>()));
            }
        });
    }
}