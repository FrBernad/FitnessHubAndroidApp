package com.example.fitnesshub.model;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserAPI {

    @POST("user/login")
    Single<AuthToken> login(@Body UserCredentials credentials);

    @POST("user/current")
    Single<UserInfo> getCurrentUser(@Body UserCredentials credentials);

    @POST("user")
    Single<UserInfo> register(@Body UserInfo userInfo);

    @POST("user/verify_email")
    void verifyEmail(@Body EmailVerification credentials);

    @POST("user/resend_verification")
    void resendVerification(@Body String email);


}
