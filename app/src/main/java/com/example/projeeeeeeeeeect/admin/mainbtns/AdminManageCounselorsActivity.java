package com.example.projeeeeeeeeeect.admin.mainbtns;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projeeeeeeeeeect.R;
import com.example.projeeeeeeeeeect.admin.mainbtns.specbtns.AssignCounsellorToReportActivity;

public class AdminManageCounselorsActivity extends AppCompatActivity {

    private Button btnAssign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_counsellors);

        btnAssign = findViewById(R.id.btnAssignCounsellor);
        btnAssign.setOnClickListener(v -> {
            Intent intent = new Intent(this, AssignCounsellorToReportActivity.class);
            intent.putExtra("reportId", 1); // TODO: Pass actual report ID
            startActivity(intent);
        });
    }
}