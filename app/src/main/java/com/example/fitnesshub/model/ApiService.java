package com.example.fitnesshub.model;

public abstract class ApiService {
    public static final String BASE_URL = "http://10.0.2.2/api/";
    private static String authToken = null;

    public static String getAuthToken() {
        return "bearer "+authToken;
    }
}
