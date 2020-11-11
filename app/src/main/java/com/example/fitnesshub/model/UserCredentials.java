package com.example.fitnesshub.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserCredentials {

    @Expose
    @SerializedName("username")
    private String username;

    @Expose
    @SerializedName("password")
    private String password;

    public UserCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
