package com.example.fitnesshub.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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

public class FavouritesRoutinesViewModel extends ViewModel {
    private MutableLiveData<List<RoutineData>> favouriteRoutines = new MutableLiveData<>();

    private RoutinesAPIService routinesService = new RoutinesAPIService();
    private CompositeDisposable disposable = new CompositeDisposable();

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
                routinesService.getFavouriteRoutines(options, "bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOjMsImlhdCI6MTYwNDkzODQ0MDI3MCwiZXhwIjoxNjA0OTQxMDMyMjcwfQ.6t9Q3d56aZZ6GT8D4F-FNZsi6gqltZHyYDku4SBjyWM")
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<PagedList<RoutineData>>() {
                            @Override
                            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull PagedList<RoutineData> favourites) {
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
