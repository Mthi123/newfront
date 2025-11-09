package com.example.projeeeeeeeeeect.Models;

public class SubmitReportResponse {
    String message;
    int report_id; // Assuming API returns the ID of the new report

    public String getMessage() {
        return message;
    }

    public int getReport_id() {
        return report_id;
    }
}