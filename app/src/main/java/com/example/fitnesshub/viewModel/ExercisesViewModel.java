package com.example.fitnesshub.viewModel;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fitnesshub.model.ExerciseData;
import com.example.fitnesshub.model.PagedList;
import com.example.fitnesshub.model.RoutineCycleData;
import com.example.fitnesshub.model.RoutineData;
import com.example.fitnesshub.model.RoutinesAPIService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ExercisesViewModel extends AndroidViewModel {

    private MutableLiveData<List<ExerciseData>> warmupExercises = new MutableLiveData<>();
    private MutableLiveData<List<ExerciseData>> mainExercises = new MutableLiveData<>();
    private MutableLiveData<List<ExerciseData>> cooldownExercises = new MutableLiveData<>();

    private RoutinesAPIService routinesService;
    private CompositeDisposable disposable = new CompositeDisposable();


    public ExercisesViewModel(@androidx.annotation.NonNull Application application) {
        super(application);
        routinesService = new RoutinesAPIService(application);
    }

    public void refresh(int routineId) {
        fetchFromRemote(routineId);
    }

    private void fetchFromRemote(int routineId) {
        Map<String, String> options = new HashMap<>();
        options.put("page", "0");
        options.put("size", "100");

        List<RoutineCycleData> routineCycles = new ArrayList<>();

        disposable.add(
                routinesService.getRoutineCycles(routineId, options)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<PagedList<RoutineCycleData>>() {
                            @Override
                            public void onSuccess(@NonNull PagedList<RoutineCycleData> cycles) {
                                routineCycles.addAll(cycles.getEntries());
                                for (RoutineCycleData cycle : routineCycles) {
                                    disposable.add(
                                            routinesService.getExercises(routineId, cycle.getId(), options)
                                                    .subscribeOn(Schedulers.newThread())
                                                    .observeOn(AndroidSchedulers.mainThread())
                                                    .subscribeWith(new DisposableSingleObserver<PagedList<ExerciseData>>() {
                                                        @Override
                                                        public void onSuccess(@NonNull PagedList<ExerciseData> cycleExercises) {
                                                            switch (cycle.getType()) {
                                                                case "warmup":
                                                                    warmupExercises.setValue(cycleExercises.getEntries());
                                                                    break;
                                                                case "exercise":
                                                                    mainExercises.setValue(cycleExercises.getEntries());
                                                                    break;
                                                                case "cooldown":
                                                                    cooldownExercises.setValue(cycleExercises.getEntries());
                                                                    break;
                                                            }
                                                        }

                                                        @Override
                                                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                                                            e.printStackTrace();
                                                        }
                                                    })
                                    );
                                }
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                e.printStackTrace();
                            }
                        })
        );

    }

    public void rateRoutine(int routineId, int value) {
        Map<String, String> rating = new HashMap<>();
        rating.put("score", String.valueOf(value));
        rating.put("review", "");
        disposable.add(
                routinesService.rateRoutine(routineId, rating)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<RoutineData>() {
                            @Override
                            public void onSuccess(@NonNull RoutineData routineData) {

                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                e.printStackTrace();
                            }
                        })
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }

    public MutableLiveData<List<ExerciseData>> getWarmupExercises() {
        return warmupExercises;
    }

    public MutableLiveData<List<ExerciseData>> getMainExercises() {
        return mainExercises;
    }

    public MutableLiveData<List<ExerciseData>> getCooldownExercises() {
        return cooldownExercises;
    }
}

