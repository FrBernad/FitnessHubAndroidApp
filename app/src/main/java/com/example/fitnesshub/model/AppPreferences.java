package com.example.fitnesshub.model;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.fitnesshub.R;

public class AppPreferences {
    private final String AUTH_TOKEN = "auth_token";
    private final String USER_ID = "user_id";

    private SharedPreferences sharedPreferences;

    public AppPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
    }

    public void setAuthToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AUTH_TOKEN, token);
        editor.apply();
    }

    public void setNightModeState(Boolean state){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("NightMode",state);
        editor.apply();
    }

    public Boolean loadNightModeState(){
        return sharedPreferences.getBoolean("NightMode",false);
    }

    public String getAuthToken() {
        return sharedPreferences.getString(AUTH_TOKEN, null);
    }

    public int getUserId() {
        return sharedPreferences.getInt(USER_ID, -1);
    }

    public void setUserId(Integer id) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(USER_ID, id);
        editor.apply();
    }
}
