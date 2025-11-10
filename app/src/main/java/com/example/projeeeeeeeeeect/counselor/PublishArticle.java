package com.example.projeeeeeeeeeect.counselor;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projeeeeeeeeeect.network.ApiService;
import com.example.projeeeeeeeeeect.R;
import com.example.projeeeeeeeeeect.network.RetrofitClient;
import com.example.projeeeeeeeeeect.Models.PublishArticleRequest; // <-- NEW
import com.example.projeeeeeeeeeect.Models.PublishArticleResponse; // <-- NEW

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PublishArticle extends AppCompatActivity {

    private EditText titleEditText, contentEditText;
    private Button publishBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_article);

        titleEditText = findViewById(R.id.titleEditText);
        contentEditText = findViewById(R.id.contentEditText);
        publishBtn = findViewById(R.id.publishBtn);

        publishBtn.setOnClickListener(v -> publishNewResource());
    }

    private void publishNewResource() {
        String title = titleEditText.getText().toString().trim();
        String content = contentEditText.getText().toString().trim();

        if (title.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "Please enter title and content", Toast.LENGTH_SHORT).show();
            return;
        }

        // 1. Create the request model
        PublishArticleRequest request = new PublishArticleRequest(title, content);

        // 2. Get the API service
        ApiService apiService = RetrofitClient.getApiService(this);

        // 3. Make the network call
        Call<PublishArticleResponse> call = apiService.publishResource(request);

        call.enqueue(new Callback<PublishArticleResponse>() {
            @Override
            public void onResponse(Call<PublishArticleResponse> call, Response<PublishArticleResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(PublishArticle.this, "Resource published successfully!", Toast.LENGTH_SHORT).show();

                    // Clear fields upon success
                    titleEditText.setText("");
                    contentEditText.setText("");
                } else {
                    // Log or display the error
                    Toast.makeText(PublishArticle.this, "Failed to publish resource. Code: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PublishArticleResponse> call, Throwable t) {
                Toast.makeText(PublishArticle.this, "Network Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}