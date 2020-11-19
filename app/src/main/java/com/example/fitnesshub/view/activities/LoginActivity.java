package com.example.fitnesshub.view.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.fitnesshub.R;
import com.example.fitnesshub.model.AppPreferences;
import com.example.fitnesshub.view.fragments.WelcomeFragmentDirections;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppPreferences preferences = new AppPreferences(this.getApplication());
        if(preferences.loadNightModeState())
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        NavController aux = Navigation.findNavController(this,R.id.nav_host_login);
//        WelcomeFragmentDirections.ActionWelcomeToLoginFragment action = WelcomeFragmentDirections.actionWelcomeToLoginFragment();
//        aux.navigate(action.setRoutineId(getIntent().getStringExtra("RoutineId")));

    }
}