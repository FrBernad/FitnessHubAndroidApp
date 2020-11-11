package com.example.fitnesshub.model;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.fitnesshub.viewModel.UserViewModel;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer " + APIService.getAuthToken())
                .build();
        return chain.proceed(newRequest);
    }
}
