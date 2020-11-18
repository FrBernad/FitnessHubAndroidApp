package com.example.fitnesshub.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fitnesshub.R;
import com.example.fitnesshub.databinding.FragmentRoutineExecutionExerciseBinding;
import com.example.fitnesshub.model.ExerciseData;
import com.example.fitnesshub.view.adapters.ExercisesAdapter;
import com.example.fitnesshub.viewModel.ExercisesViewModel;

import java.util.ArrayList;

public class RoutineExecutionExerciseFragment extends Fragment {


    private FragmentRoutineExecutionExerciseBinding binding;
    private ExercisesViewModel viewModel;

    private ArrayList<ExerciseData> warmUp;
    private ArrayList<ExerciseData> main;
    private ArrayList<ExerciseData> cooldown;
    private int currentCycle;
    private String cycleTitle;
    private ArrayList<ExerciseData> currCycle;
    private int currentExercise;
    private boolean finished;


    private TextView title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRoutineExecutionExerciseBinding.inflate(getLayoutInflater());
        title = binding.routineNameTitleInExecutionExercise;
        View view = binding.getRoot();
        getActivity().findViewById(R.id.bottomNav).setVisibility(View.GONE);
        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        if(getArguments() != null){
            title.setText(RoutineExecutionExerciseFragmentArgs.fromBundle(getArguments()).getTitle());
        }

        viewModel = new ViewModelProvider(getActivity()).get(ExercisesViewModel.class);
        observeExerciseViewModel();

    }

    private void observeExerciseViewModel() {
        viewModel.getWarmupExercises().observe(getViewLifecycleOwner(), warmupExercises -> {
            if (warmupExercises != null) {
                warmUp = (ArrayList<ExerciseData>) warmupExercises;
            }
        });

        viewModel.getMainExercises().observe(getViewLifecycleOwner(), mainExercises -> {
            if (mainExercises != null) {
                main = (ArrayList<ExerciseData>) mainExercises;
            }
        });

        viewModel.getCooldownExercises().observe(getViewLifecycleOwner(), cooldownExercises -> {
            if (cooldownExercises != null) {
                cooldown =(ArrayList<ExerciseData>) cooldownExercises;
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        startExecution();
    }

    private void startExecution(){
        currentCycle = 0;
        currentExercise = 0;
        cycleTitle = "WARM UP";
        finished = false;

        viewModel.getCountDownTimer().start(getNextExercise().getTime()*1000, 1000);
        viewModel.getCountDownTimer().getStatus().observe(getViewLifecycleOwner(), countDown -> {
            if (countDown.isFinished() && !finished) {
                ExerciseData exercise;
                currentExercise++;
                if((exercise = getNextExercise()) != null ){
                    viewModel.getCountDownTimer().start(exercise.getTime()*1000, 1000);
                }
            }
        });


    }

    private ExerciseData getNextExercise(){
        ExerciseData ex = null;
        currCycle = getCurrentCycle();
        if(currCycle != null){
            System.out.println(currentExercise);
            ex = currCycle.get(currentExercise);
            binding.exerciseTitleInExecutionList.setText(ex.getName());
            binding.routineCycleTitleInExecutionExercise.setText(cycleTitle);
            binding.timeExercise.setText(String.valueOf(ex.getTime()));
            binding.ExerciseDescription.setText(ex.getDetail());
        }
        System.out.println(ex);
        return ex;


    }



    private ArrayList<ExerciseData> getCurrentCycle(){
        if(currentCycle == 0 ){
            if(currentExercise < warmUp.size()){
                return warmUp;
            }
            cycleTitle = "MAIN EXERCISES";
            currentCycle++;
            currentExercise=0;
        }

        if(currentCycle == 1){
            if(currentExercise < main.size())
                return main;
            currentCycle++;
            currentExercise=0;
            cycleTitle = "COOLDOWN";

        }

        if(currentCycle == 2){
            if(currentExercise < cooldown.size())
                return cooldown;
        }
        finished = true;
        return null;
    }




}