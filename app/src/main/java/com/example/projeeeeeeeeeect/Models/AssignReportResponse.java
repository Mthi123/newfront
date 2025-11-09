package com.example.projeeeeeeeeeect.Models;

import com.google.gson.annotations.SerializedName;

public class AssignReportResponse {

    @SerializedName("message")
    private String message;

    @SerializedName("report")
    private Report report;

    public String getMessage() {
        return message;
    }

    public Report getReport() {
        return report;
    }
}
