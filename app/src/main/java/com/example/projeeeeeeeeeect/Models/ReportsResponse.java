package com.example.projeeeeeeeeeect.Models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ReportsResponse {
    @SerializedName("reports")
    private List<Report> reports;

    public List<Report> getReports() {
        return reports;
    }
}
