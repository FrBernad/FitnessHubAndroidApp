package com.example.fitnesshub.view.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.fitnesshub.R;
import com.example.fitnesshub.databinding.FragmentLoginBinding;
import com.example.fitnesshub.view.activities.MainActivity;
import com.example.fitnesshub.viewModel.RoutineListViewModel;
import com.example.fitnesshub.viewModel.UserViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.nio.file.Path;

public class LoginFragment extends Fragment {

    //For validation
    private TextInputLayout username, password;

    private UserViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentLoginBinding binding = FragmentLoginBinding.inflate(getLayoutInflater());
        username = binding.loginUsername;
        password = binding.loginPassword;

        View view = binding.getRoot();
        Button loginBtn = view.findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(v -> tryLogin());

        return view;
    }


    private void tryLogin() {
//        if (!validateUsername() | !validatePassword()) {
//            return;
//        }

        viewModel.tryLogin(username.getEditText().getText().toString(), password.getEditText().getText().toString());

        viewModel.getToken().observe(getViewLifecycleOwner(), authToken -> {
            if (authToken != null) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    //Validation functions
    private boolean validateUsername() {
        String val = username.getEditText().getText().toString().trim();
        String checkspaces = "\\A\\w{1,20}\\z"; //Check whitespaces

        if (val.isEmpty()) {
            username.setError("Field can not be empty");
            return false;
        } else if (val.length() > 20) {
            username.setError("Username is too large!");
            return false;
        } else if (!val.matches(checkspaces)) {
            username.setError("No white spaces are allowed!");
            return false;
        } else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String val = password.getEditText().getText().toString().trim();
        String checkPassword = "^" +
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";
        ;

        if (val.isEmpty()) {
            password.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkPassword)) {
            password.setError("Password should contain at least 4 characters!");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

}