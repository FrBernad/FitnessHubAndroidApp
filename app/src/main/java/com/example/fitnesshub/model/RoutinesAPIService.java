package com.example.fitnesshub.model;


import java.util.Map;

import io.reactivex.rxjava3.core.Single;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RoutinesAPIService extends ApiService implements RoutinesAPI {

    private RoutinesAPI api;

    public RoutinesAPIService() {
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
                .create(RoutinesAPI.class);
    }

    @Override
    public Single<PagedList<RoutineOverviewInfo>> getRoutines(Map<String, String> options, String token) {
        return api.getRoutines(options, token);
    }

    @Override
    public Single<PagedList<ExerciseOverviewInfo>> getExercises(Integer routineId, Integer cycleId, Map<String, String> options, String token) {
        return api.getExercises(routineId, cycleId, options, token);
    }

}