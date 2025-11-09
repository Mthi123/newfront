package com.example.projeeeeeeeeeect.Models;

import com.google.gson.annotations.SerializedName;

public class AssignReportRequest {
    @SerializedName("counsellorId")
    private int counsellorId;

    public AssignReportRequest(int counsellorId) {
        this.counsellorId = counsellorId;
    }

    public int getCounsellorId() {
        return counsellorId;
    }
}
