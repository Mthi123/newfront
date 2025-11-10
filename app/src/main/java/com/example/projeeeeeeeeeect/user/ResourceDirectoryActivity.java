package com.example.projeeeeeeeeeect.user;

import android.os.Bundle;
import android.util.Log; // <-- Import Log
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projeeeeeeeeeect.Adapters.ResourceAdapter;
import com.example.projeeeeeeeeeect.Models.Resource;
import com.example.projeeeeeeeeeect.R;
import com.example.projeeeeeeeeeect.network.ApiService;
import com.example.projeeeeeeeeeect.network.RetrofitClient;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResourceDirectoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ResourceAdapter adapter;
    private static final String TAG = "ResourceActivity"; // <-- For logging

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_directory);

        recyclerView = findViewById(R.id.resourcesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchResources();
    }

    private void fetchResources() {
        ApiService apiService = RetrofitClient.getApiService(this);
        Call<List<Resource>> call = apiService.getAllResources();

        call.enqueue(new Callback<List<Resource>>() {
            @Override
            public void onResponse(Call<List<Resource>> call, Response<List<Resource>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Resource> resources = response.body();

                    // --- NEW: Check if list is empty ---
                    if (resources.isEmpty()) {
                        Toast.makeText(ResourceDirectoryActivity.this, "No resources found.", Toast.LENGTH_SHORT).show();
                    }

                    adapter = new ResourceAdapter(resources);
                    recyclerView.setAdapter(adapter);

                } else {
                    // --- MODIFIED: Show detailed error ---
                    String errorMsg = "Failed to load resources. Error code: " + response.code();
                    try {
                        // Try to get the error message from the server response
                        errorMsg += " - " + response.errorBody().string();
                    } catch (Exception e) {
                        Log.e(TAG, "Error parsing error body", e);
                    }
                    Toast.makeText(ResourceDirectoryActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                    Log.e(TAG, errorMsg);
                }
            }

            @Override
            public void onFailure(Call<List<Resource>> call, Throwable t) {
                // --- MODIFIED: Show detailed error ---
                String networkError = "Network Error: " + t.getMessage();
                Toast.makeText(ResourceDirectoryActivity.this, networkError, Toast.LENGTH_LONG).show();
                Log.e(TAG, networkError, t);
            }
        });
    }
}