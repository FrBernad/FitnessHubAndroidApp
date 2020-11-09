package com.example.fitnesshub.model;

import com.example.fitnesshub.view.Routine;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RoutinesApiService {

    public static final String BASE_URL = "http://127.0.0.1:8080/api";

    private RoutinesAPI api;

    public RoutinesApiService() {
        api = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(RoutinesAPI.class);

    }

    public Single<List<RoutineOverviewInfo>> getRoutinesOverviews(){
        return api.getRoutinesOverviews();
    }
}
