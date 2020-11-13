package com.example.fitnesshub.model;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.fitnesshub.viewModel.UserViewModel;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private final AppPreferences preferences;

    public AuthInterceptor(Context context) {
        this.preferences = new AppPreferences(context);
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request.Builder newRequest = chain.request().newBuilder();
         if (preferences.getAuthToken() != null) {
            newRequest.addHeader("Authorization", "Bearer " + preferences.getAuthToken());
        }
        return chain.proceed(newRequest.build());
    }
}
