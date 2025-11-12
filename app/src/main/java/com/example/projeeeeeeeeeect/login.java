package com.example.projeeeeeeeeeect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View; // NEW: Needed for the onClick method
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projeeeeeeeeeect.Models.User;
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

// NEW IMPORTS FOR HELP DIALOG
import android.app.AlertDialog;
import android.content.DialogInterface;

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

        // --- Existing Login Button Logic ---
        loginButton.setOnClickListener(v -> {
            // ... (Your existing login logic here) ...
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString();
            // Create the request object
            UserLoginRequest loginRequest = new UserLoginRequest(email, password);
            // Get the API service
            ApiService apiService = RetrofitClient.getApiService(this);
            // Create the network call
            Call<UserLoginResponse> call = apiService.loginUser(loginRequest);

            call.enqueue(new Callback<UserLoginResponse>() {
                @Override
                public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(login.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                        String token = response.body().getToken();
                        User user = response.body().getUser();

                        if (token != null) {
                            sessionManager.saveAuthToken(token);
                        }

                        if (user != null) {
                            int roleId = user.getRoleId();
                            int userId = user.getId();

                            sessionManager.saveUserId(userId);
                            sessionManager.saveRoleId(roleId);

                            switch (roleId) {
                                case 3:
                                    startActivity(new Intent(login.this, AdminDashboardActivity.class));
                                    break;
                                case 2:
                                    startActivity(new Intent(login.this, CounsilorDashboard.class));
                                    break;
                                case 1:
                                default:
                                    startActivity(new Intent(login.this, MainActivity.class));
                                    break;
                            }
                            finish();
                        } else {
                            Toast.makeText(login.this, "Login successful, but user data is missing.", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(login.this, "Invalid email or password.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UserLoginResponse> call, Throwable t) {
                    Toast.makeText(login.this, "Network Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });

        // Anonymous login (no change needed)
        anonymousButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("isAnonymous", true);
            startActivity(intent);
        });

        // Go to sign-up (no change needed)
        signupLink.setOnClickListener(v -> {
            startActivity(new Intent(this, SignUp.class));
        });
    }

    // ===================================
    // NEW HELP BUTTON METHOD
    // ===================================

    /**
     * Shows a dialog box with help information when the help button is clicked.
     * Must be public and accept a View parameter because it's linked via android:onClick in XML.
     */
    public void onHelpButtonClick(View view) {
        // Concatenate the strings defined in values/strings.xml
        String helpContent =
                getString(R.string.help_step1_title) + "\n" + getString(R.string.help_step1_details) + "\n\n" +
                        getString(R.string.help_step2_title) + "\n" + getString(R.string.help_step2_details) + "\n\n" +
                        getString(R.string.help_step3_title) + "\n" + getString(R.string.help_step3_details) + "\n\n" +
                        getString(R.string.help_step4_title) + "\n" + getString(R.string.help_step4_details) + "\n\n" +
                        getString(R.string.help_step5_title) + "\n" + getString(R.string.help_step5_details);

        new AlertDialog.Builder(this)
                .setTitle(R.string.help_title)
                .setMessage(helpContent)
                .setPositiveButton(R.string.help_dialog_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}