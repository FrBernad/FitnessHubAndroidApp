package com.example.fitnesshub.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.fitnesshub.model.RoutineCardInfo;

import java.util.ArrayList;
import java.util.List;

public class RoutineListViewModel extends AndroidViewModel {

    private MutableLiveData<List<RoutineCardInfo>> routineCards = new MutableLiveData<>();
    private MutableLiveData<Boolean> routineCardLoadError = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();

    public RoutineListViewModel(@NonNull Application application) {
        super(application);
    }

    public void refresh() {
        RoutineCardInfo rci1 = new RoutineCardInfo("Ejer 1","John","",null,10,3);
        RoutineCardInfo rci2 = new RoutineCardInfo("Ejer 2","Juan","",null,10,2);
        RoutineCardInfo rci3 = new RoutineCardInfo("Ejer 3","Philipe","",null,10,5);
        RoutineCardInfo rci4 = new RoutineCardInfo("Ejer 4","Andrea","",null,10,1);

        ArrayList<RoutineCardInfo> routines = new ArrayList<>();
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

    public MutableLiveData<List<RoutineCardInfo>> getRoutineCards() {
        return routineCards;
    }

    public MutableLiveData<Boolean> getRoutineCardLoadError() {
        return routineCardLoadError;
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }
}
