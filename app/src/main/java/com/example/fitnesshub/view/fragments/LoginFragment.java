package com.example.fitnesshub.view.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.fitnesshub.R;
import com.example.fitnesshub.databinding.FragmentLoginBinding;
import com.example.fitnesshub.view.activities.MainActivity;
import com.example.fitnesshub.viewModel.UserViewModel;
import com.google.android.material.textfield.TextInputLayout;

public class LoginFragment extends Fragment {

    //For validation
    private TextInputLayout username, password;
    private TextView errorMessage;

    private UserViewModel viewModel;
    private FrameLayout progressBarHolder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentLoginBinding binding = FragmentLoginBinding.inflate(getLayoutInflater());
        username = binding.loginUsername;
        password = binding.loginPassword;
        errorMessage = binding.errorMessage;
        progressBarHolder = binding.progressBarHolder;

        View view = binding.getRoot();
        Button loginBtn = view.findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(v -> tryLogin());

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

    private void tryLogin() {
        if (!validateUsername() | !validatePassword()) {
            return;
        }
        viewModel.tryLogin(username.getEditText().getText().toString(), password.getEditText().getText().toString());

        viewModel.getLoginError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                switch (error.getCode()) {
                    case 4:
                        errorMessage.setText(R.string.wrong_credentials);
                        password.setError(" ");
                        username.setError(" ");
                        new Handler().postDelayed(() -> {
                            password.setError(null);
                            username.setError(null);
                            errorMessage.setText("");
                        }, 3000);
                        viewModel.setLoginErrorCode(null);
                        break;
                    case 8:
                        errorMessage.setText(R.string.user_not_verified);
                        new Handler().postDelayed(() -> {
                            Navigation.findNavController(getView()).navigate(LoginFragmentDirections.actionLoginFragmentToVerifyUserFragment());
                        }, 3000);
                        viewModel.setLoginErrorCode(null);
                        break;
                    default:
                        errorMessage.setText(R.string.default_error);
                        new Handler().postDelayed(() -> {
                            errorMessage.setText("");
                        }, 3000);
                        break;
                }
            }
        });

        viewModel.getToken().observe(getViewLifecycleOwner(), authToken -> {
            if (authToken != null) {

                Intent intent = new Intent(getActivity(), MainActivity.class);
                Bundle aux = getArguments();
                if(aux!=null){
                    intent.putExtra("RoutineId",aux.getString("RoutineId"));
                }
                else{
                    Bundle newBundle = new Bundle();
                    newBundle.putString("RoutineId",null);
                    intent.putExtra("RoutineId",newBundle);
                }
                startActivity(intent);
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                getActivity().finish();
            }
        });
    }

    //Validation functions
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
            password.setError("Password should contain at least 8 characters!");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

}