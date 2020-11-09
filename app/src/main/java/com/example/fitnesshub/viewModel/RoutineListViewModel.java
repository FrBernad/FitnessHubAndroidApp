package com.example.fitnesshub.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.fitnesshub.model.RoutineEntries;
import com.example.fitnesshub.model.RoutineOverviewInfo;
import com.example.fitnesshub.model.RoutinesAPIService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RoutineListViewModel extends AndroidViewModel {

    private MutableLiveData<List<RoutineOverviewInfo>> routineCards = new MutableLiveData<>();
    private MutableLiveData<Boolean> routineCardLoadError = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();

    private RoutinesAPIService routinesService = new RoutinesAPIService();
    private CompositeDisposable disposable = new CompositeDisposable();

    public RoutineListViewModel(@NonNull Application application) {
        super(application);
    }

    public void refresh() {
        RoutineOverviewInfo rci1 = new RoutineOverviewInfo("Ejer 1", "John", null, null, "rookie", new RoutineOverviewInfo.RoutineCreator("male",null,null,null,null,null),new RoutineOverviewInfo.RoutineCategory(null,null,null));
        RoutineOverviewInfo rci2 = new RoutineOverviewInfo("Ejer 1", "John", null, null, "rookie", new RoutineOverviewInfo.RoutineCreator("male",null,null,null,null,null),new RoutineOverviewInfo.RoutineCategory(null,null,null));
        RoutineOverviewInfo rci3 = new RoutineOverviewInfo("Ejer 1", "John", null, null, "rookie", new RoutineOverviewInfo.RoutineCreator("male",null,null,null,null,null),new RoutineOverviewInfo.RoutineCategory(null,null,null));

        ArrayList<RoutineOverviewInfo> routines = new ArrayList<>();
        routines.add(rci1);
        routines.add(rci2);
        routines.add(rci3);

        routineCards.setValue(routines);
        routineCardLoadError.setValue(false);
        loading.setValue(false);
//        fetchFromRemote();
    }

    private void fetchFromRemote() {
        Map<String, String> options = new HashMap<>();
        options.put("page", "0");
        options.put("orderBy", "averageRating");
        options.put("direction", "desc");
        options.put("size", "100");

        loading.setValue(true);
        disposable.add(
                routinesService.getRoutinesEntries(options, "bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOjMsImlhdCI6MTYwNDkzODQ0MDI3MCwiZXhwIjoxNjA0OTQxMDMyMjcwfQ.6t9Q3d56aZZ6GT8D4F-FNZsi6gqltZHyYDku4SBjyWM")
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<RoutineEntries>>() {
                            @Override
                            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<RoutineEntries> routineEntries) {
                                routineCards.setValue(routineEntries.get(0).getEntries());
                                routineCardLoadError.setValue(false);
                                loading.setValue(false);
                            }

                            @Override
                            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                                routineCardLoadError.setValue(true);
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

    public MutableLiveData<List<RoutineOverviewInfo>> getRoutineCards() {
        return routineCards;
    }

    public MutableLiveData<Boolean> getRoutineCardLoadError() {
        return routineCardLoadError;
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }
}
