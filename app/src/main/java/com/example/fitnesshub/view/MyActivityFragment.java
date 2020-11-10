package com.example.fitnesshub.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitnesshub.databinding.FragmentRoutineBinding;
import com.example.fitnesshub.model.ExerciseOverviewInfo;
import com.example.fitnesshub.viewModel.ExerciseViewModel;

import java.util.ArrayList;

public class MyActivityFragment extends Fragment {


    private ExerciseViewModel warmupModel;
    private ExerciseViewModel mainModel;
    private ExerciseViewModel cooldownModel;

    private ExerciseAdapter warmUpAdapter;
    private ExerciseAdapter mainAdapter;
    private ExerciseAdapter cooldownAdapter;

    private FragmentRoutineBinding binding;

    public MyActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRoutineBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        return view;
    }


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerViewWarmUp = binding.warmUpExercises;
        RecyclerView recyclerViewMain = binding.mainExercises;
        RecyclerView recyclerViewCooldown = binding.cooldownExercises;

        recyclerViewWarmUp.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewMain.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewCooldown.setLayoutManager(new LinearLayoutManager(getActivity()));


        warmUp.add(new ExerciseOverviewInfo("Abs", 4));
        warmUp.add(new ExerciseOverviewInfo("Abs", 4));
        warmUp.add(new ExerciseOverviewInfo("Abs", 4));

        main.add(new ExerciseOverviewInfo("Abs", 4));
        main.add(new ExerciseOverviewInfo("Abs", 4));
        main.add(new ExerciseOverviewInfo("Abs", 4));

        cooldown.add(new ExerciseOverviewInfo("Abs", 4));
        cooldown.add(new ExerciseOverviewInfo("Abs", 4));
        cooldown.add(new ExerciseOverviewInfo("Abs", 4));

        warmUpAdapter = new ExerciseAdapter(warmUp);
        mainAdapter = new ExerciseAdapter(main);
        cooldownAdapter = new ExerciseAdapter(cooldown);

        recyclerViewWarmUp.setAdapter(warmUpAdapter);
        recyclerViewMain.setAdapter(mainAdapter);
        recyclerViewCooldown.setAdapter(cooldownAdapter);
        observeExerciseViewModel();
    }

    private void observeExerciseViewModel() {

        warmupModel.get
    }

}
