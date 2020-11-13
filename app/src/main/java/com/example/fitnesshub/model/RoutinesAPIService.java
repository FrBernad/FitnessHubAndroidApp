package com.example.fitnesshub.model;


import android.content.Context;

import java.util.Map;

import io.reactivex.rxjava3.core.Single;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RoutinesAPIService extends APIService implements RoutinesAPI {

    private RoutinesAPI api;

    public RoutinesAPIService(Context context) {
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
                .create(RoutinesAPI.class);
    }

    @Override
    public Single<PagedList<RoutineData>> getRoutines(Map<String, String> options) {
        return api.getRoutines(options);
    }

    @Override
    public Single<PagedList<RoutineData>> getFavouriteRoutines(Map<String, String> options) {
        return api.getFavouriteRoutines(options);
    }

    @Override
    public Single<PagedList<ExerciseData>> getExercises(Integer routineId, Integer cycleId, Map<String, String> options) {
        return api.getExercises(routineId, cycleId, options);
    }

    @Override
    public Single<PagedList<RoutineCycleData>> getRoutineCycles(Integer routineId, Map<String, String> options) {
        return api.getRoutineCycles(routineId, options);
    }

}