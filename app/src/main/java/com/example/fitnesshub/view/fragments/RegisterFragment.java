package com.example.fitnesshub.view.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.fitnesshub.R;
import com.example.fitnesshub.databinding.FragmentRegisterBinding;
import com.example.fitnesshub.model.UserInfo;
import com.example.fitnesshub.view.activities.MainActivity;
import com.example.fitnesshub.viewModel.UserViewModel;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterFragment extends Fragment {

    TextInputLayout email, username, password;

    FragmentRegisterBinding binding;

    private UserViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentRegisterBinding.inflate(getLayoutInflater());

        email = binding.registerEmail;
        password = binding.registerPassword;
        username = binding.registerUsername;
        Button registerBtn = binding.registerBtn;

        View view = binding.getRoot();

        registerBtn.setOnClickListener(v -> tryRegister());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
    }

    private void tryRegister() {

        System.out.println("trying");

        UserInfo userInfo = new UserInfo(username.getEditText().getText().toString()
                , password.getEditText().getText().toString()
                , ""
                , "other"
                , 0
                , email.getEditText().getText().toString()
                , ""
                , ""
        );

        viewModel.tryRegister(userInfo);

        viewModel.getUserData().observe(getViewLifecycleOwner(), userData -> {
            if (userData != null) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    //Validation functions

    private boolean validateEmail() {
        String val = email.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

        if (val.isEmpty()) {
            email.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkEmail)) {
            email.setError("Invalid Email!");
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }

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

    //Cuando se llame al metodo NEXT fragment hay que hacer esto
//    if(!validateEmail() | !validateUsername() | !validatePassword()) {
//        return;
//    }


}