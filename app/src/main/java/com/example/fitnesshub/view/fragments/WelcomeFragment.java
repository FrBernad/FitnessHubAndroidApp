package com.example.fitnesshub.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.fitnesshub.R;

public class WelcomeFragment extends Fragment{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_welcome, container, false);

        Button loginBtn = rootView.findViewById(R.id.welcomeLoginButton);
        Button registerBtn = rootView.findViewById(R.id.welcomeRegisterButton);

        loginBtn.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_welcome_to_loginFragment));
        registerBtn.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_welcome_to_registerFragment));

        return rootView;
    }


    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.welcome_container, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}