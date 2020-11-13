package com.example.fitnesshub.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.fitnesshub.model.PagedList;
import com.example.fitnesshub.model.RoutineData;
import com.example.fitnesshub.model.RoutinesAPIService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RoutineListViewModel extends AndroidViewModel {

    private MutableLiveData<List<RoutineData>> routineCards = new MutableLiveData<>();
    private MutableLiveData<List<RoutineData>> userRoutines = new MutableLiveData<>();
    private MutableLiveData<Boolean> noMoreEntries = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private MutableLiveData<Boolean> changedOptions = new MutableLiveData<>();

    private RoutinesAPIService routinesService;
    private CompositeDisposable disposable = new CompositeDisposable();

    private int currentPage = 0;
    private int totalPages = 0;
    private int itemsPerRequest = 5;
    private boolean isLastPage = false;
    private String direction = "asc";


    public RoutineListViewModel(@NonNull Application application) {
        super(application);
        routinesService = new RoutinesAPIService(application);
    }

    public void updateData() {
        if (!isLastPage) {
            fetchFromRemote();
        }
    }

    public void updateUserRoutines() {
        Map<String, String> options = new HashMap<>();
        options.put("page", "0");
        options.put("orderBy", "averageRating");
        options.put("direction", "asc");
        options.put("size", String.valueOf(1000));

        disposable.add(
                routinesService.getUserRoutines(options)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<PagedList<RoutineData>>() {
                            @Override
                            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull PagedList<RoutineData> routinesEntries) {
                                userRoutines.setValue(routinesEntries.getEntries());
                            }

                            @Override
                            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                                e.printStackTrace();
                            }
                        })
        );
    }

    public void orderRoutines(int option) {
        switch (option) {
            case 1:
                direction = "asc";
                break;

            case 2:
                direction = "desc";
                break;
        }

        applyChanges();
    }

    private void applyChanges() {
        currentPage = 0;
        isLastPage = false;
        totalPages = 0;
        changedOptions.setValue(true);
        changedOptions.setValue(false);
    }

    private void fetchFromRemote() {
        Map<String, String> options = new HashMap<>();
        options.put("page", String.valueOf(currentPage));
        options.put("orderBy", "averageRating");
        options.put("direction", direction);
        options.put("size", String.valueOf(itemsPerRequest));


        loading.setValue(true);

        disposable.add(
                routinesService.getRoutines(options)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<PagedList<RoutineData>>() {
                            @Override
                            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull PagedList<RoutineData> routinesEntries) {
                                isLastPage = routinesEntries.getLastPage();
                                noMoreEntries.setValue(isLastPage);
                                currentPage++;
                                routineCards.setValue(routinesEntries.getEntries());
                                totalPages = (int) Math.ceil(routinesEntries.getTotalCount() / (double) itemsPerRequest);
                                loading.setValue(false);
                            }

                            @Override
                            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                                loading.setValue(false);
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

    public MutableLiveData<List<RoutineData>> getRoutineCards() {
        return routineCards;
    }

    public MutableLiveData<Boolean> getChangedOptions() {
        return changedOptions;
    }

    public MutableLiveData<Boolean> getNoMoreEntries() {
        return noMoreEntries;
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    public MutableLiveData<List<RoutineData>> getUserRoutines() {
        return userRoutines;
    }

}
