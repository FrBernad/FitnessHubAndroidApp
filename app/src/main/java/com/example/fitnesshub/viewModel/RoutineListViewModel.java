package com.example.fitnesshub.viewModel;

import android.app.Application;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.fitnesshub.model.PagedList;
import com.example.fitnesshub.model.RoutineData;
import com.example.fitnesshub.model.RoutinesAPIService;
import com.example.fitnesshub.view.adapters.RoutinesAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RoutineListViewModel extends AndroidViewModel {

    private MutableLiveData<List<RoutineData>> routineCards = new MutableLiveData<>();
    private MutableLiveData<Boolean> noMoreEntries = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();

    private RoutinesAPIService routinesService = new RoutinesAPIService();
    private CompositeDisposable disposable = new CompositeDisposable();

    private int currentPage = 0;
    private int totalPages = 0;
    private int itemsPerRequest = 5;
    private boolean isLastPage = false;

    public RoutineListViewModel(@NonNull Application application) {
        super(application);
    }

    public void updateData(RoutinesAdapter routinesAdapter) {
        if (!isLastPage) {
            fetchFromRemote(routinesAdapter);
        }
    }


    private void fetchFromRemote(RoutinesAdapter routinesAdapter) {
        Map<String, String> options = new HashMap<>();
        options.put("page", String.valueOf(currentPage));
        options.put("orderBy", "averageRating");
        options.put("direction", "desc");
        options.put("size", String.valueOf(itemsPerRequest));


        loading.setValue(true);

        disposable.add(
                routinesService.getRoutines(options, "bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOjMsImlhdCI6MTYwNTE5NDM0NzIwNSwiZXhwIjoxNjA1MTk2OTM5MjA1fQ.MDxHS8S-FceRlvwwzFLbDh0uaM2lp7OM8uHx7TNtvWk")
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
                                routinesAdapter.updateRoutines(routineCards.getValue());
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

    public MutableLiveData<Boolean> getNoMoreEntries() {
        return noMoreEntries;
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }
}
