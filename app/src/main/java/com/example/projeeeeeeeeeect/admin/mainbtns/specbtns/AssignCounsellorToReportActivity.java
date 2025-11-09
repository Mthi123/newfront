package com.example.projeeeeeeeeeect.admin.mainbtns.specbtns;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projeeeeeeeeeect.Models.Counsellor;
import com.example.projeeeeeeeeeect.Models.CounsellorsResponse;
import com.example.projeeeeeeeeeect.Models.AssignReportRequest;
import com.example.projeeeeeeeeeect.Models.AssignReportResponse;
import com.example.projeeeeeeeeeect.R;
import com.example.projeeeeeeeeeect.network.ApiService;
import com.example.projeeeeeeeeeect.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignCounsellorToReportActivity extends AppCompatActivity {

    private Spinner spinnerCounsellor;
    private Button btnAssign;
    private ArrayAdapter<Counsellor> counsellorAdapter;
    private List<Counsellor> counsellorList = new ArrayList<>();
    private int reportId = -1; // Default to invalid

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_counsellor);

        spinnerCounsellor = findViewById(R.id.spinnerCounsellor);
        btnAssign = findViewById(R.id.btnAssignCounsellor2);

        // 🔁 Get reportId from Intent
        Intent intent = getIntent();
        reportId = intent.getIntExtra("reportId", -1);

        if (reportId == -1) {
            Toast.makeText(this, "No report selected", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        fetchCounsellors();

        btnAssign.setOnClickListener(v -> {
            Counsellor selected = (Counsellor) spinnerCounsellor.getSelectedItem();
            if (selected == null) {
                Toast.makeText(this, "Please select a counsellor", Toast.LENGTH_SHORT).show();
                return;
            }

            assignCounsellorToReport(reportId, selected.getId());
        });
    }

    private void fetchCounsellors() {
        ApiService apiService = RetrofitClient.getApiService(this);
        Call<CounsellorsResponse> call = apiService.getCounsellors();

        call.enqueue(new Callback<CounsellorsResponse>() {
            @Override
            public void onResponse(Call<CounsellorsResponse> call, Response<CounsellorsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    counsellorList = response.body().getCounsellors();
                    counsellorAdapter = new ArrayAdapter<>(AssignCounsellorToReportActivity.this,
                            android.R.layout.simple_spinner_dropdown_item, counsellorList);
                    spinnerCounsellor.setAdapter(counsellorAdapter);
                } else {
                    Log.e("AssignCounsellor", "Failed to load counsellors");
                }
            }

            @Override
            public void onFailure(Call<CounsellorsResponse> call, Throwable t) {
                Log.e("AssignCounsellor", "Network error: " + t.getMessage());
            }
        });
    }

    private void assignCounsellorToReport(int reportId, int counsellorId) {
        ApiService apiService = RetrofitClient.getApiService(this);
        AssignReportRequest request = new AssignReportRequest(counsellorId);

        Call<AssignReportResponse> call = apiService.assignCounsellor(reportId, request);
        call.enqueue(new Callback<AssignReportResponse>() {
            @Override
            public void onResponse(Call<AssignReportResponse> call, Response<AssignReportResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(AssignCounsellorToReportActivity.this,
                            "Assigned to: " + response.body().getReport().getAssignedCounsellorId(),
                            Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AssignCounsellorToReportActivity.this,
                            "Failed to assign. Code: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AssignReportResponse> call, Throwable t) {
                Toast.makeText(AssignCounsellorToReportActivity.this,
                        "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}