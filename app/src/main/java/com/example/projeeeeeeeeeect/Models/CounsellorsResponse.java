package com.example.projeeeeeeeeeect.Models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class CounsellorsResponse {
    @SerializedName("counsellors")
    private List<Counsellor> counsellors;

    public List<Counsellor> getCounsellors() {
        return counsellors;
    }
}
