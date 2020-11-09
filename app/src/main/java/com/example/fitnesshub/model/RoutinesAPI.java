package com.example.fitnesshub.model;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.QueryMap;

public interface RoutinesAPI {

    @GET("routines")
    Single<List<RoutineEntries>> getRoutinesEntries(
            @QueryMap Map<String,String> options,
            @Header("Authorization") String token
    );
}
