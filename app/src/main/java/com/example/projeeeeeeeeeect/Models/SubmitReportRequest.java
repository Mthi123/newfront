package com.example.projeeeeeeeeeect.Models;

import com.google.gson.annotations.SerializedName;

public class SubmitReportRequest {

    // --- CRITICAL FIX: Changed from String to int ---
    @SerializedName("incident_type_id")
    int incidentTypeId; // Renamed to clarify it's an ID

    @SerializedName("location")
    String location;

    @SerializedName("description")
    String description;

    @SerializedName("date_of_incident")
    String dateOfIncident;

    // --- CRITICAL FIX: Changed constructor parameter from String to int ---
    public SubmitReportRequest(int incidentTypeId, String location, String description, String dateOfIncident) {
        this.incidentTypeId = incidentTypeId; // Now takes an int
        this.location = location;
        this.description = description;
        this.dateOfIncident = dateOfIncident;
    }
}