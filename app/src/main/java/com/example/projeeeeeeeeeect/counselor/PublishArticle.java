package com.example.projeeeeeeeeeect.counselor;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projeeeeeeeeeect.R;
import com.example.projeeeeeeeeeect.Models.PublishArticleRequest;
import com.example.projeeeeeeeeeect.Models.PublishArticleResponse;
import com.example.projeeeeeeeeeect.network.ApiService;
import com.example.projeeeeeeeeeect.network.RetrofitClient;

import java.io.IOException;

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

        if (title.length() < 10) {
            Toast.makeText(this, "Title must be at least 10 characters.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (content.length() < 50) {
            Toast.makeText(this, "Content must be at least 50 characters.", Toast.LENGTH_SHORT).show();
            return;
        }

        publishBtn.setEnabled(false); // Disable to prevent double submission

        PublishArticleRequest request = new PublishArticleRequest(title, content);
        ApiService apiService = RetrofitClient.getApiService(this);
        Call<PublishArticleResponse> call = apiService.publishResource(request);

        call.enqueue(new Callback<PublishArticleResponse>() {
            @Override
            public void onResponse(Call<PublishArticleResponse> call, Response<PublishArticleResponse> response) {
                publishBtn.setEnabled(true);

                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(PublishArticle.this, "Resource published successfully!", Toast.LENGTH_SHORT).show();
                    titleEditText.setText("");
                    contentEditText.setText("");
                } else {
                    try {
                        String errorMsg = response.errorBody() != null ? response.errorBody().string() : "Unknown error";
                        Log.e("PublishError", errorMsg);
                        Toast.makeText(PublishArticle.this, "Failed to publish. Code: " + response.code(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(PublishArticle.this, "Error reading server response.", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<PublishArticleResponse> call, Throwable t) {
                publishBtn.setEnabled(true);
                Toast.makeText(PublishArticle.this, "Network Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}