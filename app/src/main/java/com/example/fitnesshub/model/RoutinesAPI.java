package com.example.fitnesshub.model;

import java.util.Map;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface RoutinesAPI {

    @GET("routines")
    Single<PagedList<RoutineData>> getRoutines(
            @QueryMap Map<String, String> options
    );

    @GET("routines/{routineId}/cycles")
    Single<PagedList<RoutineCycleData>> getRoutineCycles(
            @Path("routineId") Integer routineId,
            @QueryMap Map<String, String> options
    );

    @GET("routines/{routineId}/cycles/{cycleId}/exercises")
    Single<PagedList<ExerciseData>> getExercises(
            @Path("routineId") Integer routineId,
            @Path("cycleId") Integer cycleId,
            @QueryMap Map<String, String> options
    );

    @GET("user/current/routines/favourites")
    Single<PagedList<RoutineData>> getFavouriteRoutines(
            @QueryMap Map<String, String> options
    );

    @POST("routines/{routineId}/ratings")
    Single<RoutineData> rateRoutine(
            @Path("routineId") Integer routineId,
            @Body Map<String, String> rating
    );

}
