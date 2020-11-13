package com.example.fitnesshub.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ErrorResponse {

    public static final int LOCAL_UNEXPECTED_ERROR = 10;

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("details")
    @Expose
    private List<String> details = null;

    public ErrorResponse() {
    }

    public ErrorResponse(Integer code, String description) {
        this(code, description, null);
    }

    public ErrorResponse(Integer code, String description, List<String> details) {
        this.code = code;
        this.description = description;
        this.details = details;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "code=" + code +
                ", description='" + description + '\'' +
                ", details=" + details +
                '}';
    }
}