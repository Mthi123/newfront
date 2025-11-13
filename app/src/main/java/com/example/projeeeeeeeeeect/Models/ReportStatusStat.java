package com.example.projeeeeeeeeeect.Models;

import com.google.gson.annotations.SerializedName;

public class ReportStatusStat {
    @SerializedName("status_id")
    int statusId;

    @SerializedName("status_name")
    String statusName;

    @SerializedName("count")
    int count;

    @SerializedName("StatusType.id")
    int nestedId;

    @SerializedName("StatusType.name")
    String nestedName;
    public String getStatusName() { return statusName; }

}
