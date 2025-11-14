package com.example.projeeeeeeeeeect.Models;

import com.google.gson.annotations.SerializedName;

public class ReportTypeStat {
    @SerializedName("incident_type_id")
    int incidentTypeId;

    @SerializedName("incident_type")
    String incidentType;

    @SerializedName("count")
    int count;

    // These keys with a "." in them are unusual,
    // but @SerializedName can handle them!
    @SerializedName("IncidentType.id")
    int nestedId;

    @SerializedName("IncidentType.name")
    String nestedName;

    public int getCount() {
        return count;
    }

    public String getNestedName(){
        return nestedName;
    }

    public String getIncidentType(){
        return incidentType;
    }
}
