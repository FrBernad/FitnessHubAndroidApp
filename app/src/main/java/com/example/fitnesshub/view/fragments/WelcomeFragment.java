package com.example.fitnesshub.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.fitnesshub.R;

public class WelcomeFragment extends Fragment {

    private String arg1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle a = getArguments();
        if(a!=null){
            arg1 = a.getString("RoutineId");
        }else {
            arg1=null;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_welcome, container, false);

        Button loginBtn = rootView.findViewById(R.id.welcomeLoginButton);
        Button registerBtn = rootView.findViewById(R.id.welcomeRegisterButton);

        loginBtn.setOnClickListener((v) -> {

            NavController navController = Navigation.findNavController(v);
            WelcomeFragmentDirections.ActionWelcomeToLoginFragment action = WelcomeFragmentDirections.actionWelcomeToLoginFragment();
            action.setRoutineId(arg1);
            navController.navigate(action);

        });
        registerBtn.setOnClickListener(v -> {

            NavController navController = Navigation.findNavController(v);
            WelcomeFragmentDirections.ActionWelcomeToRegisterFragment action = WelcomeFragmentDirections.actionWelcomeToRegisterFragment();
            action.setRoutineId(arg1);
            navController.navigate(action);

        });

        return rootView;
    }

}