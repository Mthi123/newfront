package com.example.projeeeeeeeeeect.counselor;

import android.content.Intent; // <-- NEW IMPORT
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
import com.example.projeeeeeeeeeect.Models.Report;

import java.io.Serializable; // <-- NEW IMPORT
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

        // --- THIS IS THE UPDATED CLICK LISTENER ---
        reportsListView.setOnItemClickListener((parent, view, position, id) -> {
            Report selectedReport = reportList.get(position);

            if (selectedReport == null) {
                Toast.makeText(this, "Error: Could not load report details.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Navigate to ReportDetailActivity
            Intent intent = new Intent(CounsilorViewReports.this, ReportDetailActivity.class);

            intent.putExtra(ReportDetailActivity.EXTRA_REPORT, (Serializable) selectedReport);

            startActivity(intent);
        });
    }

    private void loadReports() {
        SessionManager sessionManager = new SessionManager(this);
        // The token is now retrieved correctly from SessionManager
        String token = sessionManager.getAuthToken();
        int counselorId = sessionManager.getUserId();

        // Check if the token is null or empty, not just the string "Bearer null"
        if (token == null || token.isEmpty() || counselorId == -1) {
            Toast.makeText(this, "Session expired or invalid. Please log in again.", Toast.LENGTH_LONG).show();
            return;
        }

        // Add "Bearer " prefix to the token
        String authToken = "Bearer " + token;

        ApiService apiService = RetrofitClient.getApiService(this);

        // Pass the full "Bearer <token>" string to the API
        Call<List<Report>> call = apiService.getReportsByCounsellor(authToken, counselorId);


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