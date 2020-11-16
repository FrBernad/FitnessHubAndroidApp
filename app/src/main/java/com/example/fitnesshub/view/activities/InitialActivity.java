package com.example.fitnesshub.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.example.fitnesshub.R;
import com.example.fitnesshub.model.AppPreferences;
import com.example.fitnesshub.view.fragments.LoginFragment;

public class InitialActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_initial);

        new Handler().postDelayed(() -> {
            AppPreferences preferences = new AppPreferences(this.getApplication());
            if (preferences.getAuthToken() != null) {
                Intent intent = new Intent(InitialActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish(); //elimino la actividad del stack
                return;
            }
            Intent intent = new Intent(InitialActivity.this, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish(); //elimino la actividad del stack
        }, 3000);
    }
}