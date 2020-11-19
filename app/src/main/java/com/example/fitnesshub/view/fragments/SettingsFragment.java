package com.example.fitnesshub.view.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;

import com.example.fitnesshub.R;
import com.example.fitnesshub.databinding.FragmentRoutineBinding;
import com.example.fitnesshub.databinding.FragmentSettingsBinding;
import com.example.fitnesshub.model.AppPreferences;
import com.example.fitnesshub.view.activities.LoginActivity;
import com.example.fitnesshub.view.activities.MainActivity;
import com.example.fitnesshub.view.adapters.SortAdapter;
import com.example.fitnesshub.viewModel.UserViewModel;

import java.util.Locale;

public class SettingsFragment extends Fragment{

    FragmentSettingsBinding binding;
    AppPreferences preferences;
    SwitchCompat switchDarkMode;
    UserViewModel userviewModel;

    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        userviewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        preferences = new AppPreferences(getActivity().getApplication());
        binding.logOutButtom.setOnClickListener(v -> logout());
        switchDarkMode = binding.enableDarkModeSwitch;
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            switchDarkMode.setChecked(true);
        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            preferences.setNightModeState(isChecked);
            restartApp();
        });
        return view;
    }

    public void restartApp() {
        Intent i = new Intent(getActivity(), MainActivity.class);
        startActivity(i);
        getActivity().finish();
    }

    private void logout() {
        userviewModel.logout();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        getActivity().finish(); //elimino la actividad del stack
    }



}


