package com.example.projeeeeeeeeeect.admin.mainbtns;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projeeeeeeeeeect.Models.AssignReportRequest;
import com.example.projeeeeeeeeeect.Models.Counselor;
import com.example.projeeeeeeeeeect.Models.CounselorListResponse;
import com.example.projeeeeeeeeeect.Models.Report;
import com.example.projeeeeeeeeeect.Models.SubmitReportResponse;
import com.example.projeeeeeeeeeect.R;
import com.example.projeeeeeeeeeect.network.ApiService;
import com.example.projeeeeeeeeeect.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminManageCounselorsActivity extends AppCompatActivity {

    private ListView unassignedReportsListView;
    private Spinner counselorSpinner;
    private Button btnAssignReport;
    private Button btnCreateCounselor; // <-- NEW BUTTON
    private TextView tvSelectedReport;

    private List<Report> reportList = new ArrayList<>();
    private List<Counselor> counselorList = new ArrayList<>();

    private Report selectedReport = null;
    private Counselor selectedCounselor = null;

    private ArrayAdapter<Counselor> counselorAdapter;

    private static final int ROLE_COUNSELOR = 2; // Assuming 2 is the counselor role ID

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_counselors2);

        // Bind Views
        unassignedReportsListView = findViewById(R.id.unassignedReportsListView);
        counselorSpinner = findViewById(R.id.counselorSpinner);
        btnAssignReport = findViewById(R.id.btnAssignReport);
        tvSelectedReport = findViewById(R.id.tvSelectedReport);
        btnCreateCounselor = findViewById(R.id.btnCreateCounselor); // <-- BIND NEW BUTTON

        // Setup Spinners and Listeners
        setupCounselorSpinner();
        setupReportListView();

        loadReports();
        loadCounselors();

        btnAssignReport.setOnClickListener(v -> assignReport());

        // --- NEW BUTTON LISTENER ---
       // btnCreateCounselor.setOnClickListener(v -> {
         //   Intent intent = new Intent(this, AdminCreateCounselorActivity.class);
           // startActivity(intent);
        //});
        // ---------------------------
    }

    // ... (setup methods and loadReports/loadCounselors remain the same)

    private void setupCounselorSpinner() {
        counselorAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                counselorList
        );
        counselorSpinner.setAdapter(counselorAdapter);

        counselorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    selectedCounselor = counselorList.get(position);
                } else {
                    selectedCounselor = null;
                }
                updateAssignButtonState();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedCounselor = null;
                updateAssignButtonState();
            }
        });
    }

    private void setupReportListView() {
        unassignedReportsListView.setOnItemClickListener((parent, view, position, id) -> {
            // Highlight the selected item (visual feedback is ideal but hard in ListView)

            selectedReport = reportList.get(position);

            // Update TextView feedback
            String incidentType = (selectedReport.getIncidentType() != null) ? selectedReport.getIncidentType().getName() : "Unknown Type";
            tvSelectedReport.setText("Selected Report: ID " + selectedReport.getId() + " - " + incidentType);

            updateAssignButtonState();
        });
    }

    private void updateAssignButtonState() {
        // Button is enabled only if both a report and a counselor are selected
        btnAssignReport.setEnabled(selectedReport != null && selectedCounselor != null);
    }

    // ------------------- DATA LOADING -------------------

    private void loadReports() {
        ApiService apiService = RetrofitClient.getApiService(this);
        // We assume an endpoint /api/reports/unassigned exists to get only reports needing assignment
        Call<List<Report>> call = apiService.getUnassignedReports();

        call.enqueue(new Callback<List<Report>>() {
            @Override
            public void onResponse(Call<List<Report>> call, Response<List<Report>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    reportList.clear();
                    reportList.addAll(response.body());

                    List<String> displayReports = new ArrayList<>();
                    for (Report report : reportList) {
                        String incidentType = (report.getIncidentType() != null) ? report.getIncidentType().getName() : "Unknown Type";
                        displayReports.add("ID: " + report.getId() + " | " + incidentType + " | Loc: " + report.getLocation());
                    }

                    ArrayAdapter<String> reportAdapter = new ArrayAdapter<>(
                            AdminManageCounselorsActivity.this,
                            android.R.layout.simple_list_item_1,
                            displayReports
                    );
                    unassignedReportsListView.setAdapter(reportAdapter);
                    Toast.makeText(AdminManageCounselorsActivity.this, "Unassigned reports loaded: " + reportList.size(), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(AdminManageCounselorsActivity.this, "Failed to load reports. Code: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Report>> call, Throwable t) {
                Toast.makeText(AdminManageCounselorsActivity.this, "Network Error loading reports: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadCounselors() {
        ApiService apiService = RetrofitClient.getApiService(this);
        Call<CounselorListResponse> call = apiService.getAllCounselors();

        call.enqueue(new Callback<CounselorListResponse>() {
            @Override
            public void onResponse(Call<CounselorListResponse> call, Response<CounselorListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    counselorList.clear();
                    // Add placeholder (position 0)
                    counselorList.add(new Counselor() {{ name = "Select Counselor"; email = ""; }});
                    counselorList.addAll(response.body().getUsers());
                    counselorAdapter.notifyDataSetChanged();
                    Toast.makeText(AdminManageCounselorsActivity.this, "Counselors loaded: " + (counselorList.size() - 1), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AdminManageCounselorsActivity.this, "Failed to load counselors. Code: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CounselorListResponse> call, Throwable t) {
                Toast.makeText(AdminManageCounselorsActivity.this, "Network Error loading counselors: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    // ------------------- ASSIGNMENT LOGIC -------------------

    private void assignReport() {
        if (selectedReport == null || selectedCounselor == null) {
            Toast.makeText(this, "Please select a report AND a counselor.", Toast.LENGTH_SHORT).show();
            return;
        }

        int reportId = selectedReport.getId();
        int counselorId = selectedCounselor.getId();

        // 1. Create Request
        AssignReportRequest request = new AssignReportRequest(reportId, counselorId);

        // 2. API Call
        ApiService apiService = RetrofitClient.getApiService(this);
        Call<SubmitReportResponse> call = apiService.assignReport(request);

        call.enqueue(new Callback<SubmitReportResponse>() {
            @Override
            public void onResponse(Call<SubmitReportResponse> call, Response<SubmitReportResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AdminManageCounselorsActivity.this,
                            "Report ID " + reportId + " assigned to " + selectedCounselor.getName(),
                            Toast.LENGTH_LONG).show();

                    // Reset selection state and refresh report list
                    selectedReport = null;
                    tvSelectedReport.setText("Selected Report: None");
                    updateAssignButtonState();
                    loadReports(); // Reload unassigned reports

                } else {
                    Toast.makeText(AdminManageCounselorsActivity.this, "Assignment failed. Code: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SubmitReportResponse> call, Throwable t) {
                Toast.makeText(AdminManageCounselorsActivity.this, "Network Error during assignment: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}