package com.example.projeeeeeeeeeect.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Request body for assigning a specific report ID to a counselor ID.
 */
public class AssignReportRequest {
    @SerializedName("report_id")
    int reportId;

    @SerializedName("counselor_id")
    int counselorId;

    public AssignReportRequest(int reportId, int counselorId) {
        this.reportId = reportId;
        this.counselorId = counselorId;
    }
}