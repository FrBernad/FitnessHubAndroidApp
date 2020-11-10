package com.example.fitnesshub.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fitnesshub.model.ExerciseOverviewInfo;

import java.util.ArrayList;
import java.util.List;

public class ExerciseViewModel extends ViewModel {
    MutableLiveData<List<ExerciseOverviewInfo>> exerciseLiveData;
    List<ExerciseOverviewInfo> exerciseList;

    public ExerciseViewModel(){
        exerciseLiveData = new MutableLiveData<>();
        init();
    }

    public MutableLiveData<List<ExerciseOverviewInfo>> getExerciseMutableLiveData() {
        return exerciseLiveData;
    }

    public void init(){
        populateList();
        exerciseLiveData.setValue(exerciseList);
    }

    public void populateList(){

        ExerciseOverviewInfo ex = new ExerciseOverviewInfo("Abs",4);
        exerciseList = new ArrayList<>();
        exerciseList.add(ex);
        exerciseList.add(ex);
        exerciseList.add(ex);
        exerciseList.add(ex);
        exerciseList.add(ex);
        exerciseList.add(ex);
    }
}

