package com.example.fitnesshub.viewModel;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fitnesshub.model.PagedList;
import com.example.fitnesshub.model.RoutineData;
import com.example.fitnesshub.model.RoutinesAPIService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

public class FavouritesRoutinesViewModel extends AndroidViewModel {
    private MutableLiveData<List<RoutineData>> favouriteRoutines = new MutableLiveData<>();

    private RoutinesAPIService routinesService;
    private CompositeDisposable disposable = new CompositeDisposable();

    public void favRoutine(int routineId) {
        disposable.add(routinesService.favRoutine(routineId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Response<Void>>() {
                    @Override
                    public void onSuccess(@NonNull Response<Void> voidResponse) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }
                }));
    }


    public void unfavRoutine(int routineId) {
        disposable.add(routinesService.unfavRoutine(routineId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Response<Void>>() {
                    @Override
                    public void onSuccess(@NonNull Response<Void> voidResponse) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }
                }));
    }

    public FavouritesRoutinesViewModel(@androidx.annotation.NonNull Application application) {
        super(application);
        routinesService = new RoutinesAPIService(application);
    }

    public void updateData() {
        fetchFromRemote();
    }

    private void fetchFromRemote() {
        Map<String, String> options = new HashMap<>();
        options.put("page", String.valueOf(0));
        options.put("orderBy", "averageRating");
        options.put("direction", "desc");
        options.put("size", String.valueOf(100));

        disposable.add(
                routinesService.getFavouriteRoutines(options)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<PagedList<RoutineData>>() {
                            @Override
                            public void onSuccess(@NonNull PagedList<RoutineData> favourites) {
                                favouriteRoutines.setValue(favourites.getEntries());
                            }

                            @Override
                            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
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

    public MutableLiveData<List<RoutineData>> getFavouriteRoutines() {
        return favouriteRoutines;
    }
}
