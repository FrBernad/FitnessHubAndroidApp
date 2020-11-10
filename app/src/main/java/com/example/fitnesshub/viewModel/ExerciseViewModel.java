package com.example.fitnesshub.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fitnesshub.model.ExerciseOverviewInfo;
import com.example.fitnesshub.model.RoutinesAPIService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class ExerciseViewModel extends ViewModel {

    private MutableLiveData<List<ExerciseOverviewInfo>> warmupExercises = new MutableLiveData<>();
    private MutableLiveData<List<ExerciseOverviewInfo>> mainExercises = new MutableLiveData<>();
    private MutableLiveData<List<ExerciseOverviewInfo>> cooldownExercises = new MutableLiveData<>();

//    private RoutinesAPIService routinesService = new RoutinesAPIService();
//    private CompositeDisposable disposable = new CompositeDisposable();

    public void refresh() {
        ExerciseOverviewInfo ex = new ExerciseOverviewInfo("Abs", 4);
        List<ExerciseOverviewInfo> exerciseList = new ArrayList<>();
        exerciseList.add(ex);
        exerciseList.add(ex);
        exerciseList.add(ex);

        warmupExercises.setValue(exerciseList);
        mainExercises.setValue(exerciseList);
        cooldownExercises.setValue(exerciseList);
    }

    public MutableLiveData<List<ExerciseOverviewInfo>> getWarmupExercises() {
        return warmupExercises;
    }

    public MutableLiveData<List<ExerciseOverviewInfo>> getMainExercises() {
        return mainExercises;
    }

    public MutableLiveData<List<ExerciseOverviewInfo>> getCooldownExercises() {
        return cooldownExercises;
    }
}

