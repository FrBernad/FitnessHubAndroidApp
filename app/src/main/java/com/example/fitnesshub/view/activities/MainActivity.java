package com.example.fitnesshub.view.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.fitnesshub.R;
import com.example.fitnesshub.model.AppPreferences;
import com.example.fitnesshub.view.fragments.HomeFragmentDirections;
import com.example.fitnesshub.viewModel.RoutinesViewModel;
import com.example.fitnesshub.viewModel.UserViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private RoutinesViewModel routinesViewModel;
    private UserViewModel userviewModel;
    AppPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = new AppPreferences(this.getApplication());
        setAppMode();

        setContentView(R.layout.activity_main);

        Intent appLinkIntent = getIntent();

        System.out.println(appLinkIntent.getExtras());

        setUpBottomNavigation();

        setSupportActionBar(findViewById(R.id.main_toolbar));

        userviewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userviewModel.setUserData();

        String id = appLinkIntent.getStringExtra("RoutineId");

        if (id != null) {
            int idInt = verifyAndConvertId(id);
            if (idInt != -1) {
                routinesViewModel = new ViewModelProvider(this).get(RoutinesViewModel.class);
                routinesViewModel.getRoutineById(idInt);
                routinesViewModel.getCurrentRoutine().observe(this, externRoutine -> {
                    if (externRoutine != null) {
                        NavController aux = Navigation.findNavController(this, R.id.mainNavFragment);
                        HomeFragmentDirections.ActionHomeFragmentToRoutineFragment action = HomeFragmentDirections.actionHomeFragmentToRoutineFragment();
                        aux.navigate(action.setRoutineId(idInt));
                    }
                });
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.mainNavFragment);
        return navController.navigateUp();
    }

    private int verifyAndConvertId(String id) {
        int aux;
        try {
            aux = Integer.parseInt(id);
        } catch (Exception e) {
            return -1;
        }
        return aux;
    }

    public void setUpBottomNavigation() {
        bottomNavigationView = findViewById(R.id.bottomNav);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.mainNavFragment);
        assert navHostFragment != null;
        NavigationUI.setupWithNavController(bottomNavigationView,
                navHostFragment.getNavController());
    }

    public void setNavigationVisibility(boolean b) {
        if (b) {
            bottomNavigationView.setVisibility(View.VISIBLE);
        } else {
            bottomNavigationView.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(false);
        getMenuInflater().inflate(R.menu.main_toolbar, menu);

        return true;
    }

    public void setAppMode() {
        if (preferences.loadNightModeState())
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    public void showUpButton() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void hideUpButton() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

}