package com.example.fitnesshub.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.fitnesshub.databinding.FragmentRegisterBinding;
import com.example.fitnesshub.model.UserInfo;
import com.example.fitnesshub.viewModel.UserViewModel;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterFragment extends Fragment {

    private TextInputLayout email, username, password;

    private FragmentRegisterBinding binding;

    private UserViewModel viewModel;

    private FrameLayout progressBarHolder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentRegisterBinding.inflate(getLayoutInflater());

        email = binding.registerEmail;
        password = binding.registerPassword;
        username = binding.registerUsername;
        Button registerBtn = binding.registerBtn;
        progressBarHolder = binding.progressBarHolder;

        View view = binding.getRoot();

        registerBtn.setOnClickListener(v -> tryRegister());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);

        viewModel.getLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading != null) {
                if (isLoading) {
                    progressBarHolder.setVisibility(View.VISIBLE);
                } else {
                    progressBarHolder.setVisibility(View.GONE);
                }
            }
        });
    }

    private void tryRegister() {
        if (!validateEmail() | !validateUsername() | !validatePassword()) {
            return;
        }

        UserInfo userInfo = new UserInfo(username.getEditText().getText().toString()
                , password.getEditText().getText().toString()
                , ""
                , "other"
                , ""
                , email.getEditText().getText().toString()
                , ""
                , ""
        );

        viewModel.tryRegister(userInfo);

        viewModel.getUserData().observe(getViewLifecycleOwner(), userData -> {
            if (userData != null) {
                Navigation.findNavController(getView()).navigate(RegisterFragmentDirections.actionRegisterFragmentToVerifyUserFragment());
            }
        });
    }

    private boolean validateEmail() {
        String val = email.getEditText().getText().toString().trim();
        String checkEmail = "[^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+";

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
        String checkspaces = "^[a-zA-Z0-9\\-_]{1,20}$"; //No white spaces, must contain at most 20 characters.

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
        String checkPassword = "^[a-zA-Z0-9\\-_]{8,}$"; //No whitespaces, must contain more than 8 characters.
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