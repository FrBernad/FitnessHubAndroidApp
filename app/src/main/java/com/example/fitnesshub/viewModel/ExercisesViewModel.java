package com.example.fitnesshub.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.fitnesshub.model.ExerciseData;
import com.example.fitnesshub.model.PagedList;
import com.example.fitnesshub.model.RoutineCycleData;
import com.example.fitnesshub.model.RoutineData;
import com.example.fitnesshub.model.RoutineRating;
import com.example.fitnesshub.model.RoutinesAPIService;
import com.example.fitnesshub.view.fragments.CountDownTimer;

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

    private com.example.fitnesshub.view.fragments.CountDownTimer countDownTimer = new CountDownTimer();

    private MutableLiveData<List<ExerciseData>> warmupExercises = new MutableLiveData<>();
    private MutableLiveData<List<ExerciseData>> mainExercises = new MutableLiveData<>();
    private MutableLiveData<List<ExerciseData>> cooldownExercises = new MutableLiveData<>();
    private MutableLiveData<Boolean> isFav = new MutableLiveData<>();

    private RoutinesAPIService routinesService;
    private CompositeDisposable disposable = new CompositeDisposable();

    private boolean started = false; //borrar
    private boolean played; // borrar

    private int currentCycle;
    private String cycleTitle;
    private int currentExercise;
    private boolean isFirstTime = true;
    private boolean finished;
    private int status;
    private ArrayList<ExerciseData> currCycle;
    private boolean executed;


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
        RoutineRating rating = new RoutineRating(value, "");
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

    public boolean getExecuted() {
        return executed;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }

    public int getStatus(){
        return status;
    }

    public void setStatus(int status){
        this.status = status;
    }


    public MutableLiveData<List<ExerciseData>> getWarmupExercises() {
        return warmupExercises;
    }

    public MutableLiveData<List<ExerciseData>> getMainExercises() {
        return mainExercises;
    }

    public MutableLiveData<List<ExerciseData>> getCooldownExercises() {return cooldownExercises;}

    public CountDownTimer getCountDownTimer() {
        return countDownTimer;
    }

    public void setStarted(boolean state) {
        started = state;
    }

    public boolean getStarted() {
        return started;
    }

    public boolean getIsFirstTime(){
        return isFirstTime;
    }
    public void setIsFirstTime(boolean state){
        isFirstTime = state;
    }

    public int getCurrentCycle() {
        return currentCycle;
    }

    public int getCurrentExercise() {
        return currentExercise;
    }

    public void setCurrentCycle(int currentCycle) {
        this.currentCycle = currentCycle;
    }

    public void setCurrentExercise(int currentExercise) {
        this.currentExercise = currentExercise;
    }

    public boolean getPlayed() {
        return played;
    }
}

