package com.example.projeeeeeeeeeect;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import android.content.res.Configuration;
import android.widget.ImageButton;

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
import android.app.AlertDialog;
import android.content.DialogInterface;

public class login extends AppCompatActivity {
    // Class variables for language switching
    private final List<String> supportedLocales = Arrays.asList("en", "af", "xh");
    private int currentLocaleIndex = 0;

    EditText emailInput, passwordInput;
    Button loginButton, anonymousButton;
    TextView signupLink;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // --- NEW: LANGUAGE SETUP ---
        // Must set the locale BEFORE super.onCreate() and setContentView()
        setAppLocale(getCurrentLanguageCode());
        // ---------------------------

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sessionManager = new SessionManager(this);

        // Find and set initial language index based on the current locale
        String currentCode = getCurrentLanguageCode();
        currentLocaleIndex = supportedLocales.indexOf(currentCode);
        if (currentLocaleIndex == -1) {
            currentLocaleIndex = 0; // Default to English if not found
        }

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        anonymousButton = findViewById(R.id.anonymousButton);
        signupLink = findViewById(R.id.signupLink);

        // --- NEW: UPDATE LANGUAGE BUTTON TEXT ---
        updateLangButtonText();
        // ----------------------------------------


        // --- Existing Login Button Logic ---
        loginButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString();

            UserLoginRequest loginRequest = new UserLoginRequest(email, password);
            ApiService apiService = RetrofitClient.getApiService(this);
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


        anonymousButton.setOnClickListener(v -> {
            ApiService apiService = RetrofitClient.getApiService(this);
            Call<UserLoginResponse> call = apiService.anonymousLogin();

            call.enqueue(new Callback<UserLoginResponse>() {
                @Override
                public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(login.this, "Logged in as Anonymous!", Toast.LENGTH_SHORT).show();

                        String token = response.body().getToken();
                        User user = response.body().getUser();

                        if (token != null) {
                            sessionManager.saveAuthToken(token);
                        }

                        if (user != null) {
                            int userId = user.getId();
                            int roleId = user.getRoleId();
                            sessionManager.saveUserId(userId);
                            sessionManager.saveRoleId(roleId);
                        }

                        // Proceed to MainActivity
                        Intent intent = new Intent(login.this, MainActivity.class);
                        intent.putExtra("isAnonymous", true);
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(login.this, "Anonymous login failed. Server error: " + response.code(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<UserLoginResponse> call, Throwable t) {
                    Toast.makeText(login.this, "Network Error on anonymous login: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });

        // Go to sign-up (no change needed)
        signupLink.setOnClickListener(v -> {
            startActivity(new Intent(this, SignUp.class));
        });

    }

    // --- Existing Help Button Method ---
    public void onHelpButtonClick(View view) {
        String helpContent =
                getString(R.string.help_step1_title) + "\n" + getString(R.string.help_step1_details)+ "\n\n" +
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

    // ===================================
    // NEW LANGUAGE BUTTON METHODS
    // ===================================

    /**
     * Handles the click event for the language button, cycling the locale.
     * Must be public and accept a View parameter (linked via android:onClick in XML).
     */
    public void onLangButtonClick(View view) {
        // Move to the next locale in the list (cycles back to 0)
        currentLocaleIndex = (currentLocaleIndex + 1) % supportedLocales.size();
        String newLocaleCode = supportedLocales.get(currentLocaleIndex);

        setAppLocale(newLocaleCode);

        // Recreate the activity to apply the language change instantly
        recreate();
    }

    /**
     * Utility function to set the application language
     */
    private void setAppLocale(String localeCode) {
        Locale locale = new Locale(localeCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);

        // Update configuration for the base context
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }

    /**
     * Utility function to get the current language code from resources
     */
    private String getCurrentLanguageCode() {
        // Get the primary locale from the system's configuration
        return getResources().getConfiguration().getLocales().get(0).getLanguage();
    }

    /**
     * Finds the button and sets its text using the current locale's string resource
     */
    private void updateLangButtonText() {
        Button langButton = findViewById(R.id.lang_button);
        // You must have a Button with id 'lang_button' in your activity_login.xml
        if (langButton != null) {
            langButton.setText(getString(R.string.lang_button_text));
        }
    }
}