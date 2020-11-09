package com.example.fitnesshub.model;


import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RoutinesAPIService extends ApiService implements RoutinesAPI{

    private RoutinesAPI api;

    public RoutinesAPIService() {
        api = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(RoutinesAPI.class);

    }

    @Override
    public Single<List<RoutineEntries>> getRoutinesEntries(Map<String, String> options, String token) {
        return api.getRoutinesEntries(options,token);
    }
}