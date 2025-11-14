package com.example.projeeeeeeeeeect.admin.mainbtns.specbtns;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class AdminReportsByLocationActivity extends AppCompatActivity {

    private ListView statListView;
    private EditText etLocationInput;
    private Button btnSearchLocation;
    private ArrayAdapter<String> adapter;
    private List<String> statStrings = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_location_report);

        statListView = findViewById(R.id.statListView);
        etLocationInput = findViewById(R.id.etLocationInput);
        btnSearchLocation = findViewById(R.id.btnSearchLocation);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, statStrings);
        statListView.setAdapter(adapter);

        btnSearchLocation.setOnClickListener(v -> {
            String location = etLocationInput.getText().toString().trim();
            if (TextUtils.isEmpty(location)) {
                Toast.makeText(this, "Please enter a location", Toast.LENGTH_SHORT).show();
            } else {
                fetchReportStats(location);
            }
        });
    }

    private void fetchReportStats(String location) {
        ApiService apiService = RetrofitClient.getApiService(this);
        Call<List<ReportTypeStat>> call = apiService.getReportsByLocation(location);

        call.enqueue(new Callback<List<ReportTypeStat>>() {
            @Override
            public void onResponse(Call<List<ReportTypeStat>> call, Response<List<ReportTypeStat>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    statStrings.clear();
                    if (response.body().isEmpty()) {
                        statStrings.add("No reports found for this location.");
                    } else {
                        for (ReportTypeStat stat : response.body()) {
                            // The model has "incidentType" and "nestedName". Let's use "nestedName" if it's available.
                            String name = (stat.getNestedName() != null) ? stat.getNestedName() : stat.getIncidentType();
                            statStrings.add(name + ": " + stat.getCount());
                        } // <-- THIS CLOSING BRACE WAS MISSING
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(AdminReportsByLocationActivity.this, "Failed to load stats", Toast.LENGTH_SHORT).show();
                    statStrings.clear();
                    statStrings.add("Error loading data.");
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<ReportTypeStat>> call, Throwable t) {
                Toast.makeText(AdminReportsByLocationActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                statStrings.clear();
                statStrings.add("Network Error.");
                adapter.notifyDataSetChanged();
            }
        });
    }
}