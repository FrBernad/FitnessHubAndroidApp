package com.example.fitnesshub.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class ParticularRoutineViewModel extends AndroidViewModel {
    private int particularID;
    public ParticularRoutineViewModel(@NonNull Application application) {
        super(application);
    }
    public void setParticularRoutine(int particularID){
        this.particularID=particularID;
    }
    public int getParticularRoutine(){
        return particularID;
    }
}
