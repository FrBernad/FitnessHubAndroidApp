package com.example.fitnesshub.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmailVerification {

    @Expose
    @SerializedName("email")
    private String email;

    @Expose
    @SerializedName("code")
    private String code;

    public EmailVerification(String email, String code) {
        this.email = email;
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public String getCode() {
        return code;
    }
}
