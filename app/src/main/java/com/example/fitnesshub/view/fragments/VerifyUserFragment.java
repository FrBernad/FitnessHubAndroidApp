package com.example.fitnesshub.view.fragments;

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

//        verifyBtn.setOnClickListener(v->tryVerify());
//        resendBtn.setOnClickListener(v->resendVerification());

        return inflater.inflate(R.layout.fragment_verify_user, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
    }

}