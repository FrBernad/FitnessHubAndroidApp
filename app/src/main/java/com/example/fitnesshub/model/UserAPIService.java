package com.example.fitnesshub.model;

import android.content.Context;

import java.util.Map;

import io.reactivex.rxjava3.core.Single;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserAPIService extends APIService implements UserAPI {

    private UserAPI api;

    public UserAPIService(Context context) {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(new AuthInterceptor(context))
                .build();

        api = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(httpClient)
                .build()
                .create(UserAPI.class);
    }

    public UserAPI getApi() {
        return api;
    }

    @Override
    public Single<AuthToken> login(UserCredentials credentials) {
        return api.login(credentials);
    }

    @Override
    public Single<UserInfo> getCurrentUser() {
        return api.getCurrentUser();
    }

    @Override
    public Single<UserInfo> register(UserInfo userInfo) {
        return api.register(userInfo);
    }

    @Override
    public Single<Response<Void>> verifyEmail(VerificationData credentials) {
        return api.verifyEmail(credentials);
    }

    @Override
    public Single<Response<Void>> resendVerification(Map<String,String> data) {
        return api.resendVerification(data);
    }

    @Override
    public Single<Response<Void>> logout() {
        return api.logout();
    }
}
