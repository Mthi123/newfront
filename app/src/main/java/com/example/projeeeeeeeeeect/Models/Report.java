package com.example.projeeeeeeeeeect.Models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable; // <-- ADDED IMPORT

/**
 * Represents a single, full report object
 */
public class Report implements Serializable { // <-- ADDED 'implements Serializable'
    @SerializedName("id")
    int id;

    @SerializedName("user_id")
    int userId;

    @SerializedName("date_reported")
    String dateReported;

    @SerializedName("description")
    String description;

    @SerializedName("date_of_incident")
    String dateOfIncident;

    @SerializedName("location")
    String location;

    @SerializedName("incident_type_id")
    int incidentTypeId;

    @SerializedName("status_id")
    int statusId;

    @SerializedName("IncidentType")
    IncidentType incidentType;

    @SerializedName("StatusType")
    StatusType statusType;

    // --- CRITICAL FIX: GETTER METHODS ---
    public int getId() { return id; }
    public int getUserId() { return userId; }
    public String getDateReported() { return dateReported; }
    public String getDescription() { return description; }
    public String getDateOfIncident() { return dateOfIncident; }
    public String getLocation() { return location; }
    public int getIncidentTypeId() { return incidentTypeId; }
    public int getStatusId() { return statusId; }

    // THIS METHOD WAS MISSING/INCORRECTLY REFERENCED, CAUSING THE ERROR
    public IncidentType getIncidentType() { return incidentType; }

    public StatusType getStatusType() { return statusType; }
}