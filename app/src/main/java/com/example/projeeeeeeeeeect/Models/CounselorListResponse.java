package com.example.projeeeeeeeeeect.Models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Wraps the list of counselors returned by the API.
 */
public class CounselorListResponse {
    @SerializedName("counselors")
    private List<Counselor> counselors;

    public List<Counselor> getUsers() {
        return counselors;
    }
}