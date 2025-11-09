package com.example.projeeeeeeeeeect;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.projeeeeeeeeeect.user.FileReport;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity {

    private Button submitReportButton, sosButton, resourcesButton, messagesButton, stealthButton;
    private TextView welcomeTextView;

    private boolean isAnonymous;
    private FusedLocationProviderClient fusedLocationClient;

    private static final int LOCATION_PERMISSION_CODE = 101;

    // Registered user emergency contact (replace with dynamic fetch if needed)
    private static final String REGISTERED_EMERGENCY_CONTACT = "5554";

    // GBV helpline / police number (always included)
    private static final String GBV_HELPLINE = "5556";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get data from login intent
        isAnonymous = getIntent().getBooleanExtra("isAnonymous", false);

        // Initialize UI components
        welcomeTextView = findViewById(R.id.welcomeTextView);
        submitReportButton = findViewById(R.id.submitReportButton);
        sosButton = findViewById(R.id.sosButton);
        resourcesButton = findViewById(R.id.resourcesButton);
        messagesButton = findViewById(R.id.messagesButton);
        stealthButton = findViewById(R.id.stealthButton);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Display welcome text
        welcomeTextView.setText("Welcome");

        // Hide Messages button for anonymous users
        if (isAnonymous) {
            messagesButton.setVisibility(View.GONE);
        }

        // Submit Report button
        submitReportButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FileReport.class);
            startActivity(intent);
        });

        // SOS button
        sosButton.setOnClickListener(v -> sendEmergencyAlert());

        // Resources button
        resourcesButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Articles.class);
            startActivity(intent);
        });

        // Messages button (only for registered users)
        messagesButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Conversation.class);
            startActivity(intent);
        });

        // Stealth / Exit button
        stealthButton.setOnClickListener(v -> {
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.whatsapp");
            if (launchIntent != null) {
                startActivity(launchIntent);
            } else {
                Intent openStore = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=com.whatsapp"));
                startActivity(openStore);
            }
            finish();
        });
    }

    private void sendEmergencyAlert() {
        // Check location permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_CODE
            );
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                sendSMSWithLocation(location);
            } else {
                Toast.makeText(MainActivity.this, "Unable to get location.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendSMSWithLocation(Location location) {
        String message = "🚨 EMERGENCY ALERT! I need help.\n"
                + "My current location: https://maps.google.com/?q="
                + location.getLatitude() + "," + location.getLongitude();

        String recipients;

        // Determine recipients based on anonymity
        if (isAnonymous) {
            // Anonymous users: only GBV helpline
            recipients = GBV_HELPLINE;
        } else {
            // Registered users: emergency contact + GBV helpline
            recipients = REGISTERED_EMERGENCY_CONTACT + ";" + GBV_HELPLINE;
        }

        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setData(Uri.parse("smsto:" + recipients));
        smsIntent.putExtra("sms_body", message);

        try {
            startActivity(smsIntent);
        } catch (Exception e) {
            Toast.makeText(this, "Unable to open SMS app: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
