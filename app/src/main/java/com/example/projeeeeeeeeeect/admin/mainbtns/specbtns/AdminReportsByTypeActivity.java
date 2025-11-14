package com.example.projeeeeeeeeeect.admin.mainbtns.specbtns;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projeeeeeeeeeect.R;
import com.example.projeeeeeeeeeect.Models.ReportTypeStat;
import com.example.projeeeeeeeeeect.network.ApiService;
import com.example.projeeeeeeeeeect.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminReportsByTypeActivity extends AppCompatActivity {

    private ListView statListView;
    private TextView tvStatTitle;
    private ArrayAdapter<String> adapter;
    private List<String> statStrings = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_stat_list);

        statListView = findViewById(R.id.statListView);
        tvStatTitle = findViewById(R.id.tvStatTitle);
        tvStatTitle.setText("Reports by Incident Type");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, statStrings);
        statListView.setAdapter(adapter);

        fetchReportStats();
    }

    private void fetchReportStats() {
        ApiService apiService = RetrofitClient.getApiService(this);
        Call<List<ReportTypeStat>> call = apiService.getReportsByType();

        call.enqueue(new Callback<List<ReportTypeStat>>() {
            @Override
            public void onResponse(Call<List<ReportTypeStat>> call, Response<List<ReportTypeStat>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    statStrings.clear();
                    for (ReportTypeStat stat : response.body()) {
                        // The model has "incidentType" and "nestedName". Let's use "nestedName" if it's available.
                        String name = (stat.getNestedName() != null) ? stat.getNestedName() : stat.getIncidentType();
                        statStrings.add(name + ": " + stat.getCount());
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(AdminReportsByTypeActivity.this, "Failed to load stats", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ReportTypeStat>> call, Throwable t) {
                Toast.makeText(AdminReportsByTypeActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}