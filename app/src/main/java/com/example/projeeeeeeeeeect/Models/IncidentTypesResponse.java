package com.example.projeeeeeeeeeect.Models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class IncidentTypesResponse {
    @SerializedName("incidentTypes")
    private List<IncidentType> incidentTypes;

    public List<IncidentType> getIncidentTypes() {
        return incidentTypes;
    }
}
