package com.example.fitnesshub.view.fragments;

import android.content.Intent;
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

import com.example.fitnesshub.databinding.FragmentVerifyUserBinding;
import com.example.fitnesshub.viewModel.UserViewModel;
import com.google.android.material.textfield.TextInputLayout;

public class VerifyUserFragment extends Fragment {

    private UserViewModel viewModel;
    private TextInputLayout email, code;
    private Button resendBtn, verifyBtn;
    private FragmentVerifyUserBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentVerifyUserBinding.inflate(getLayoutInflater());

        email = binding.verificationResendEmail;
        code = binding.verificationCode;

        resendBtn = binding.resendBtn;
        verifyBtn = binding.verifyBtn;

        View view = binding.getRoot();

        verifyBtn.setOnClickListener(v -> tryVerify());
        resendBtn.setOnClickListener(v -> resendVerification());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
    }

    private void resendVerification() {
        viewModel.resendVerification(email.getEditText().getText().toString());
    }

    private void tryVerify() {
        viewModel.verifyUser(code.getEditText().getText().toString());
        viewModel.getVerified().observe(getViewLifecycleOwner(), verified -> {
            if (verified) {
                Navigation.findNavController(getView()).navigate(VerifyUserFragmentDirections.actionVerifyUserFragmentToWelcome());
            }
        });
    }

}