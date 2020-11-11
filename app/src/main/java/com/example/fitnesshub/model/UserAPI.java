package com.example.fitnesshub.model;

import java.util.Map;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface UserAPI {

    @POST("user/login")
    Single<AuthToken> login(@Body UserCredentials credentials);

}
