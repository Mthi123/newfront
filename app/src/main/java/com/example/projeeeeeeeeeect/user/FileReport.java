package com.example.projeeeeeeeeeect.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projeeeeeeeeeect.R;
import com.example.projeeeeeeeeeect.network.ApiService; // New Import
import com.example.projeeeeeeeeeect.Models.IncidentType;
import com.example.projeeeeeeeeeect.network.RetrofitClient; // New Import
import com.example.projeeeeeeeeeect.Models.SubmitReportRequest; // New Import
import com.example.projeeeeeeeeeect.Models.SubmitReportResponse;
import com.example.projeeeeeeeeeect.Models.IncidentTypesResponse;

import java.io.IOException;
import java.util.ArrayList; // <-- NEW IMPORT
import java.util.List;

import retrofit2.Call; // New Import
import retrofit2.Callback; // New Import
import retrofit2.Response; // New Import

public class FileReport extends AppCompatActivity {

    private Spinner incidentTypeSpinner;
    private EditText etLocation, etDescription, etDate;
    private Button btnSubmitReport, btnCancelReport;
    private ArrayAdapter<IncidentType> spinnerAdapter;
    private ArrayList<IncidentType> incidentTypesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_report);

        // Bind views
        incidentTypeSpinner = findViewById(R.id.etIncidentType);
        etLocation = findViewById(R.id.etLocation);
        etDescription = findViewById(R.id.etDescription);
        etDate = findViewById(R.id.etDate);
        btnSubmitReport = findViewById(R.id.btnSubmitReport);
        btnCancelReport = findViewById(R.id.btnCancelReport);

        setupSpinner();
        fetchIncidentTypes();

        btnSubmitReport.setOnClickListener(v -> {
            Log.d("FileReport", "Submit button clicked");
            submitReport();
        });

        btnCancelReport.setOnClickListener(v -> {
            Log.d("FileReport", "Cancel button clicked");
            finish();
        });
    }

    private void setupSpinner() {
        incidentTypesList.add(new IncidentType(0, "Select Incident Type..."));
        spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                incidentTypesList
        );
        incidentTypeSpinner.setAdapter(spinnerAdapter);
    }

    private void fetchIncidentTypes() {
        ApiService apiService = RetrofitClient.getApiService(this);
        Call<IncidentTypesResponse> call = apiService.getIncidentTypes();

        call.enqueue(new Callback<IncidentTypesResponse>() {
            @Override
            public void onResponse(Call<IncidentTypesResponse> call, Response<IncidentTypesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<IncidentType> types = response.body().getIncidentTypes();

                    spinnerAdapter.clear();
                    spinnerAdapter.add(new IncidentType(0, "Select Incident Type..."));
                    spinnerAdapter.addAll(types);
                    spinnerAdapter.notifyDataSetChanged();

                    Log.d("FileReport", "Loaded " + types.size() + " incident types");
                } else {
                    Log.e("FileReport", "Failed to load incident types. Code: " + response.code());
                    Toast.makeText(FileReport.this, "Failed to load incident types", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<IncidentTypesResponse> call, Throwable t) {
                Log.e("FileReport", "Network error: " + t.getMessage());
                Toast.makeText(FileReport.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void submitReport() {
        if (incidentTypeSpinner == null || etLocation == null || etDescription == null || etDate == null) {
            Log.e("FileReport", "One or more views are not initialized");
            Toast.makeText(this, "UI not fully initialized", Toast.LENGTH_SHORT).show();
            return;
        }

        IncidentType selectedType = (IncidentType) incidentTypeSpinner.getSelectedItem();
        if (selectedType == null) {
            Log.e("FileReport", "Selected incident type is null");
            Toast.makeText(this, "Error: No item selected or types not loaded.", Toast.LENGTH_SHORT).show();
            return;
        }

        String location = etLocation.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String date = etDate.getText().toString().trim();

        Log.d("FileReport", "Selected Type ID: " + selectedType.getId());
        Log.d("FileReport", "Location: " + location);
        Log.d("FileReport", "Description: " + description);
        Log.d("FileReport", "Date: " + date);

        if (selectedType.getId() == 0 || TextUtils.isEmpty(location) || TextUtils.isEmpty(description)) {
            Log.w("FileReport", "Validation failed");
            Toast.makeText(this, "Please select an Incident Type and fill in all required fields.", Toast.LENGTH_LONG).show();
            return;
        }

        SubmitReportRequest request = new SubmitReportRequest(selectedType.getId(), location, description, date);
        ApiService apiService = RetrofitClient.getApiService(this);
        if (apiService == null) {
            Log.e("FileReport", "ApiService is null");
            Toast.makeText(this, "API service not initialized!", Toast.LENGTH_SHORT).show();
            return;
        }

        Call<SubmitReportResponse> call = apiService.submitReport(request);
        call.enqueue(new Callback<SubmitReportResponse>() {
            @Override
            public void onResponse(Call<SubmitReportResponse> call, Response<SubmitReportResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("FileReport", "Report submitted successfully. ID: " + response.body().getReport_id());
                    Toast.makeText(FileReport.this, "Report submitted successfully! ID: " + response.body().getReport_id(), Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Log.e("FileReport", "Failed to submit report. Code: " + response.code());
                    Toast.makeText(FileReport.this, "Failed to submit report. Code: " + response.code(), Toast.LENGTH_LONG).show();
                    Log.e("FileReport", "Failed to submit report. Code: " + response.code());
                    try {
                        Log.e("FileReport", "Error body: " + response.errorBody().string());
                    } catch (IOException e) {
                        Log.e("FileReport", "Error reading errorBody", e);
                    }

                }

            }

            @Override
            public void onFailure(Call<SubmitReportResponse> call, Throwable t) {
                Log.e("FileReport", "Submit report failed: " + t.getMessage());
                Toast.makeText(FileReport.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}