package com.example.projeeeeeeeeeect.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projeeeeeeeeeect.R;
import com.example.projeeeeeeeeeect.network.ApiService; // New Import
import com.example.projeeeeeeeeeect.network.RetrofitClient; // New Import
import com.example.projeeeeeeeeeect.Models.SubmitReportRequest; // New Import
import com.example.projeeeeeeeeeect.Models.SubmitReportResponse; // New Import

import retrofit2.Call; // New Import
import retrofit2.Callback; // New Import
import retrofit2.Response; // New Import

public class FileReport extends AppCompatActivity {

    private Spinner incidentTypeSpinner;
    private EditText etLocation, etDescription, etDate;
    private Button btnSubmitReport, btnCancelReport;

    // Hardcoded incident types
    private final String[] incidentTypes = {
            "Select Incident Type",
            "Physical Abuse",
            "Emotional Abuse",
            "Neglect",
            "Sexual Abuse"
    };

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

        // Setup the Spinner Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                incidentTypes
        );
        incidentTypeSpinner.setAdapter(adapter);

        // Submit button logic
        btnSubmitReport.setOnClickListener(v -> submitReport());

        // Cancel button logic
        btnCancelReport.setOnClickListener(v -> finish());
    }

    private void submitReport() {
        String type = incidentTypeSpinner.getSelectedItem().toString();
        String location = etLocation.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String date = etDate.getText().toString().trim();

        // 1. Validate required fields
        if (type.equals(incidentTypes[0]) || TextUtils.isEmpty(location) || TextUtils.isEmpty(description)) {
            Toast.makeText(this, "Please select an Incident Type and fill in all required fields.", Toast.LENGTH_LONG).show();
            return;
        }

        // 2. Create the API request model
        SubmitReportRequest request = new SubmitReportRequest(type, location, description, date);

        // 3. Get the API service
        ApiService apiService = RetrofitClient.getApiService(this);

        // 4. Make the API call
        Call<SubmitReportResponse> call = apiService.submitReport(request);

        call.enqueue(new Callback<SubmitReportResponse>() {
            @Override
            public void onResponse(Call<SubmitReportResponse> call, Response<SubmitReportResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(FileReport.this, "Report submitted successfully! ID: " + response.body().getReport_id(), Toast.LENGTH_LONG).show();
                    // Close the form and return to dashboard
                    finish();
                } else {
                    // API call failed (e.g., 400 Bad Request, 500 Server Error)
                    Toast.makeText(FileReport.this, "Failed to submit report. Code: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SubmitReportResponse> call, Throwable t) {
                // Network failure (no internet, server offline, etc.)
                Toast.makeText(FileReport.this, "Network error: Unable to submit report. " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}