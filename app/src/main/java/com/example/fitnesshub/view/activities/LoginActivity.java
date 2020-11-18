package com.example.fitnesshub.view.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NotificationCompat;
import androidx.navigation.NavArgument;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.fitnesshub.R;
import com.example.fitnesshub.model.AppPreferences;
import com.example.fitnesshub.view.fragments.HomeFragmentDirections;
import com.example.fitnesshub.view.fragments.LoginFragment;
import com.example.fitnesshub.view.fragments.LoginFragmentDirections;
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

        NavController aux = Navigation.findNavController(this,R.id.nav_host_login);
        WelcomeFragmentDirections.ActionWelcomeToLoginFragment action = WelcomeFragmentDirections.actionWelcomeToLoginFragment();
        aux.navigate(action.setRoutineId(getIntent().getStringExtra("RoutineId")));

    }
}