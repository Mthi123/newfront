package com.example.projeeeeeeeeeect;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projeeeeeeeeeect.Models.UserLoginRequest;
import com.example.projeeeeeeeeeect.Models.UserLoginResponse;
import com.example.projeeeeeeeeeect.admin.AdminDashboardActivity;
import com.example.projeeeeeeeeeect.counselor.CounsilorDashboard;
import com.example.projeeeeeeeeeect.network.ApiService;
import com.example.projeeeeeeeeeect.network.RetrofitClient;
import com.example.projeeeeeeeeeect.user.SignUp;
import com.example.projeeeeeeeeeect.auth.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login extends AppCompatActivity {

    EditText emailInput, passwordInput;
    Button loginButton, anonymousButton;
    TextView signupLink;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sessionManager = new SessionManager(this);

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        anonymousButton = findViewById(R.id.anonymousButton);
        signupLink = findViewById(R.id.signupLink);

        // --- THIS IS THE NEW CLICK LISTENER ---
        loginButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString();

            UserLoginRequest loginRequest = new UserLoginRequest(email, password);

            // Pass the context (this) to getApiService
            ApiService apiService = RetrofitClient.getApiService(this);
            Call<UserLoginResponse> call = apiService.loginUser(loginRequest);

            call.enqueue(new Callback<UserLoginResponse>() {
                @Override
                public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {

                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(login.this, "Login Successful!", Toast.LENGTH_SHORT).show();

                        // --- SAVE THE TOKEN ---
                        // Get token from response and save it
                        String token = response.body().getToken();
                        if (token != null) {
                            sessionManager.saveAuthToken(token);
                        }
                        // --- END SAVE TOKEN ---

                        int roleId = response.body().getUser().role_id;;

                        // 3=Admin, 2=Counselor, 1=User (or default)
                        switch (roleId) {
                            case 3:
                                startActivity(new Intent(login.this, AdminDashboardActivity.class));
                                break;
                            case 2: // Assuming 2 is for Counselor
                                startActivity(new Intent(login.this, CounsilorDashboard.class));
                                break;
                            case 1: // Assuming 1 is for User
                            default:
                                startActivity(new Intent(login.this, MainActivity.class));
                                break;
                        }
                        finish(); // Close the login activity

                    } else {
                        // API call was not successful (e.g., 401 Unauthorized, 404 Not Found)
                        Toast.makeText(login.this, "Invalid email or password.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UserLoginResponse> call, Throwable t) {
                    // This happens on network errors
                    Toast.makeText(login.this, "Network Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });


        // Anonymous login (no change needed)
        anonymousButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("isAnonymous", true); // This now matches MainActivity
            startActivity(intent);
        });

        // Go to sign-up (no change needed)
        signupLink.setOnClickListener(v -> {
            startActivity(new Intent(this, SignUp.class));
        });
    }
}