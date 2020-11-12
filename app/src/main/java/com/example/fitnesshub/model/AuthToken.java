package com.example.fitnesshub.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthToken {

    @Expose
    @SerializedName("token")
    private String token;

    public AuthToken(String token) {
        System.out.println("CREATEDDD");
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
