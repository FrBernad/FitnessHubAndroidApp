package com.example.fitnesshub.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.fitnesshub.model.RoutineOverviewInfo;

import java.util.ArrayList;
import java.util.List;

public class RoutineListViewModel extends AndroidViewModel {

    private MutableLiveData<List<RoutineOverviewInfo>> routineCards = new MutableLiveData<>();
    private MutableLiveData<Boolean> routineCardLoadError = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();

    public RoutineListViewModel(@NonNull Application application) {
        super(application);
    }

    public void refresh() {
        RoutineOverviewInfo rci1 = new RoutineOverviewInfo("Ejer 1","John","",null,10,3);
        RoutineOverviewInfo rci2 = new RoutineOverviewInfo("Ejer 2","Juan","",null,10,2);
        RoutineOverviewInfo rci3 = new RoutineOverviewInfo("Ejer 3","Philipe","",null,10,5);
        RoutineOverviewInfo rci4 = new RoutineOverviewInfo("Ejer 4","Andrea","",null,10,1);

        ArrayList<RoutineOverviewInfo> routines = new ArrayList<>();
        routines.add(rci1);
        routines.add(rci2);
        routines.add(rci3);
        routines.add(rci4);routines.add(rci3);
        routines.add(rci4);routines.add(rci3);
        routines.add(rci4);routines.add(rci3);
        routines.add(rci4);routines.add(rci3);
        routines.add(rci4);routines.add(rci3);
        routines.add(rci4);routines.add(rci3);
        routines.add(rci4);routines.add(rci3);
        routines.add(rci4);routines.add(rci3);
        routines.add(rci4);routines.add(rci3);
        routines.add(rci4);routines.add(rci3);
        routines.add(rci4);

        routineCards.setValue(routines);
        routineCardLoadError.setValue(false);
        loading.setValue(false);
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
