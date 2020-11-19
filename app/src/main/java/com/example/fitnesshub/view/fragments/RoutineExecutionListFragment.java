package com.example.fitnesshub.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fitnesshub.R;
import com.example.fitnesshub.databinding.FragmentRoutineExecutionListBinding;
import com.example.fitnesshub.model.ExerciseData;
import com.example.fitnesshub.view.adapters.ExercisesAdapter;
import com.example.fitnesshub.viewModel.ExercisesViewModel;

import java.util.ArrayList;

public class RoutineExecutionListFragment extends Fragment {

    private FragmentRoutineExecutionListBinding binding;

    private ExercisesViewModel viewModel;

    private ExercisesAdapter warmUpAdapter = new ExercisesAdapter(new ArrayList<>());
    private ExercisesAdapter mainAdapter = new ExercisesAdapter(new ArrayList<>());
    private ExercisesAdapter cooldownAdapter = new ExercisesAdapter(new ArrayList<>());

    private RecyclerView recyclerViewWarmUp;
    private RecyclerView recyclerViewMain;
    private RecyclerView recyclerViewCooldown;

    private int currentCycle;
    private int currentExercise;
    private ExercisesAdapter currentAdapter;
    private boolean finished;

    private TextView title;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRoutineExecutionListBinding.inflate(getLayoutInflater());

        recyclerViewWarmUp = binding.warmUpExercises;
        recyclerViewMain = binding.mainExercises;
        recyclerViewCooldown = binding.cooldownExercises;

        title = binding.routineNameTitleInExecutionList;

        View view = binding.getRoot();

        getActivity().findViewById(R.id.bottomNav).setVisibility(View.GONE);

        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if (getArguments() != null) {
            title.setText(RoutineExecutionListFragmentArgs.fromBundle(getArguments()).getRoutineTitle());
        }

        viewModel = new ViewModelProvider(getActivity()).get(ExercisesViewModel.class);

        recyclerViewWarmUp.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewWarmUp.setAdapter(warmUpAdapter);

        recyclerViewMain.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewMain.setAdapter(mainAdapter);

        recyclerViewCooldown.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewCooldown.setAdapter(cooldownAdapter);

        observeExerciseViewModel();

        getActivity().findViewById(R.id.bottomNav).setVisibility(View.GONE);


    }

    private void observeExerciseViewModel() {
        viewModel.getWarmupExercises().observe(getViewLifecycleOwner(), warmupExercises -> {
            if (warmupExercises != null) {
                warmUpAdapter.updateExercises(warmupExercises);
            }
        });

        viewModel.getMainExercises().observe(getViewLifecycleOwner(), mainExercises -> {
            if (mainExercises != null) {
                mainAdapter.updateExercises(mainExercises);
            }
        });

        viewModel.getCooldownExercises().observe(getViewLifecycleOwner(), cooldownExercises -> {
            if (cooldownExercises != null) {
                cooldownAdapter.updateExercises(cooldownExercises);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().findViewById(R.id.bottomNav).setVisibility(View.VISIBLE);
    }


    @Override
    public void onResume() {
        super.onResume();
        startExecution();
    }

    private void startExecution() {
        currentCycle = 0;
        currentExercise = 0;
        finished = false;
        viewModel.getCountDownTimer().start(getNextExercise().getTime()*1000, 1000);
        viewModel.getCountDownTimer().getStatus().observe(getViewLifecycleOwner(), countDown -> {
            if (countDown.isFinished() && !finished) {
                ExerciseData exercise;
                currentAdapter.getExercise(currentExercise).setRunning(false);
                currentAdapter.notifyItemChanged(currentExercise);
                currentExercise++;
                if((exercise = getNextExercise()) != null ){
                    viewModel.getCountDownTimer().start(exercise.getTime()*1000, 1000);
                }
            }
        });
    }

    private ExerciseData getNextExercise() {
        ExerciseData exercise = null;
        currentAdapter = getCurrentCycle();
        if(currentAdapter != null){
            exercise = currentAdapter.getExercise(currentExercise);
            exercise.setRunning(true);
            currentAdapter.notifyItemChanged(currentExercise);
        }
        return exercise;
    }



    public ExercisesAdapter getCurrentCycle(){

        if(currentCycle == 0 ){
            if(currentExercise < warmUpAdapter.getExerciseList().size())
                return warmUpAdapter;
            currentCycle++;
            currentExercise=0;
        }

        if(currentCycle == 1){
            if(currentExercise < mainAdapter.getExerciseList().size())
                return mainAdapter;
            currentCycle++;
            currentExercise=0;
        }

        if(currentCycle == 2){
            if(currentExercise < cooldownAdapter.getExerciseList().size())
                return cooldownAdapter;
        }
        finished = true;

        return null;



    }
}


