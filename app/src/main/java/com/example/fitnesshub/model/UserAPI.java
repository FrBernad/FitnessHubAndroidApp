package com.example.fitnesshub.model;

import io.reactivex.rxjava3.core.Single;
import okhttp3.ResponseBody;
import retrofit2.Response;
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
    Single<Response<Void>> verifyEmail(@Body VerificationData credentials);

    @POST("user/resend_verification")
    Single<Response<Void>> resendVerification(@Body String email);


}
