package com.example.fitnesshub.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fitnesshub.model.ExerciseOverviewInfo;
import com.example.fitnesshub.model.PagedList;
import com.example.fitnesshub.model.RoutinesAPIService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ExercisesViewModel extends ViewModel {

    private MutableLiveData<List<ExerciseOverviewInfo>> warmupExercises = new MutableLiveData<>();
    private MutableLiveData<List<ExerciseOverviewInfo>> mainExercises = new MutableLiveData<>();
    private MutableLiveData<List<ExerciseOverviewInfo>> cooldownExercises = new MutableLiveData<>();

    private RoutinesAPIService routinesService = new RoutinesAPIService();
    private CompositeDisposable disposable = new CompositeDisposable();

    public void refresh(int routineId) {
        fetchFromRemote(routineId);
    }

    private void fetchFromRemote(int routineId) {
        Map<String, String> options = new HashMap<>();
        options.put("page", "0");
        options.put("size", "100");

        disposable.add(
                routinesService.getExercises(routineId, 1, options, "bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOjMsImlhdCI6MTYwNDkzODQ0MDI3MCwiZXhwIjoxNjA0OTQxMDMyMjcwfQ.6t9Q3d56aZZ6GT8D4F-FNZsi6gqltZHyYDku4SBjyWM")
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<PagedList<ExerciseOverviewInfo>>() {
                            @Override
                            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull PagedList<ExerciseOverviewInfo> warmupEntries) {
                                warmupExercises.setValue(warmupEntries.getEntries());
                                mainExercises.setValue(warmupEntries.getEntries());
                                cooldownExercises.setValue(warmupEntries.getEntries());
                            }

                            @Override
                            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                                e.printStackTrace();
                            }
                        })
        );

//        disposable.clear();
//
//        disposable.add(
//                routinesService.getExercises(routineId, 2, options, "bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOjMsImlhdCI6MTYwNDkzODQ0MDI3MCwiZXhwIjoxNjA0OTQxMDMyMjcwfQ.6t9Q3d56aZZ6GT8D4F-FNZsi6gqltZHyYDku4SBjyWM")
//                        .subscribeOn(Schedulers.newThread())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeWith(new DisposableSingleObserver<PagedList<ExerciseOverviewInfo>>() {
//                            @Override
//                            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull PagedList<ExerciseOverviewInfo> mainEntries) {
//                                mainExercises.setValue(mainEntries.getEntries());
//                            }
//
//                            @Override
//                            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
//                                e.printStackTrace();
//                            }
//                        })
//        );
//
//        disposable.clear();
//
//        disposable.add(
//                routinesService.getExercises(routineId, 3, options, "bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOjMsImlhdCI6MTYwNDkzODQ0MDI3MCwiZXhwIjoxNjA0OTQxMDMyMjcwfQ.6t9Q3d56aZZ6GT8D4F-FNZsi6gqltZHyYDku4SBjyWM")
//                        .subscribeOn(Schedulers.newThread())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeWith(new DisposableSingleObserver<PagedList<ExerciseOverviewInfo>>() {
//                            @Override
//                            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull PagedList<ExerciseOverviewInfo> cooldownEntries) {
//                                cooldownExercises.setValue(cooldownEntries.getEntries());
//                            }
//
//                            @Override
//                            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
//                                e.printStackTrace();
//                            }
//                        })
//        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
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

