package com.example.ivan.sunrisesunset.entity;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SunInfoResponse {
    @SerializedName("results")
    @Expose
    private SunInfoResult results;
    @SerializedName("statusType")
    @Expose
    private String status;

    public SunInfoResult getResults() {
        return results;
    }

    public void setResults(SunInfoResult results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
