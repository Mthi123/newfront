package com.example.projeeeeeeeeeect.Models;

import com.google.gson.annotations.SerializedName;

public class SubmitReportRequest {
    // These fields match the data collected from the form
    @SerializedName("incident_type")
    String incidentType;

    @SerializedName("location")
    String location;

    @SerializedName("description")
    String description;

    @SerializedName("date_of_incident")
    String dateOfIncident;

    // The backend is typically responsible for setting user_id based on the session token,
    // but these are the explicit fields collected from the form.

    public SubmitReportRequest(String incidentType, String location, String description, String dateOfIncident) {
        this.incidentType = incidentType;
        this.location = location;
        this.description = description;
        this.dateOfIncident = dateOfIncident;
    }
}