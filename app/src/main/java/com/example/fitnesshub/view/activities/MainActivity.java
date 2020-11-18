package com.example.fitnesshub.view.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.fitnesshub.R;
import com.example.fitnesshub.databinding.ActivityMainBinding;
import com.example.fitnesshub.model.AppPreferences;
import com.example.fitnesshub.viewModel.FavouritesRoutinesViewModel;
import com.example.fitnesshub.viewModel.UserViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private boolean isDarkMode;
    private UserViewModel viewModel;
    AppPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferences = new AppPreferences(this.getApplication());
        setAppMode();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpBottomNavigation();
        setSupportActionBar(findViewById(R.id.main_toolbar));
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        new ViewModelProvider(this).get(FavouritesRoutinesViewModel.class).updateData();
        viewModel.setUserData();
    }

    public void setUpBottomNavigation() {
        bottomNavigationView = findViewById(R.id.bottomNav);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mainNavFragment);

        assert navHostFragment != null;
        NavigationUI.setupWithNavController(bottomNavigationView,
                navHostFragment.getNavController());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(false);
        getMenuInflater().inflate(R.menu.main_toolbar, menu);
        menu.findItem(R.id.app_bar_leave_session).setVisible(true);
        if (isDarkMode)
            menu.findItem(R.id.app_bar_light_mode).setVisible(true);
        else
            menu.findItem(R.id.app_bar_dark_mode).setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.app_bar_leave_session) {
            logout();
            return true;
        } else if (id == R.id.app_bar_dark_mode) {
            preferences.setNightModeState(true);
            restartApp();
            return true;
        } else if (id == R.id.app_bar_light_mode) {
            preferences.setNightModeState(false);
            restartApp();
            return true;
        }
        return false;
    }

    public void restartApp() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();

    }

    private void logout() {
        viewModel.logout();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish(); //elimino la actividad del stack
    }

    public void setAppMode() {
        if (preferences.loadNightModeState()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            isDarkMode = true;
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            isDarkMode = false;
        }
    }


}