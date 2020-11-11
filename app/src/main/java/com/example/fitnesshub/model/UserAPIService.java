package com.example.fitnesshub.model;

import io.reactivex.rxjava3.core.Single;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserAPIService extends APIService implements UserAPI {

    private UserAPI api;

    public UserAPIService() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

        api = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(httpClient)
                .build()
                .create(UserAPI.class);
    }

    @Override
    public Single<AuthToken> login(UserCredentials credentials) {
        return api.login(credentials);
    }

    @Override
    public Single<UserInfo> getCurrentUser(UserCredentials credentials) {
        return api.getCurrentUser(credentials);
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
    public Single<Response<Void>> resendVerification(String email) {
        return api.resendVerification(email);
    }
}
