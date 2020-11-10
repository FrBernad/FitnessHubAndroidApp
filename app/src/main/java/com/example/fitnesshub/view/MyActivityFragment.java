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

import com.example.fitnesshub.R;
import com.example.fitnesshub.databinding.FragmentRoutineBinding;
import com.example.fitnesshub.model.ExerciseOverviewInfo;
import com.example.fitnesshub.viewModel.ExerciseViewModel;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class MyActivityFragment extends Fragment {


    private ExerciseViewModel warmupModel;
    private ExerciseViewModel mainModel;
    private ExerciseViewModel cooldownModel;

    private ExerciseAdapter warmUpAdapter;
    private ExerciseAdapter mainAdapter;
    private ExerciseAdapter cooldownAdapter;

    public MyActivityFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_routine, container, false);
        ButterKnife.bind(this, view);
        // Inflate the layout for this fragment

        return view;
    }


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // set up the RecyclerView
        FragmentRoutineBinding binding = FragmentRoutineBinding.inflate(getLayoutInflater());
        warmupModel = new ViewModelProvider(this).get(ExerciseViewModel.class);
        mainModel = new ViewModelProvider(this).get(ExerciseViewModel.class);
        cooldownModel = new ViewModelProvider(this).get(ExerciseViewModel.class);

        RecyclerView recyclerViewWarmUp = binding.warmUpExercises;
        RecyclerView recyclerViewMain = binding.mainExercises;
        RecyclerView recyclerViewCooldown = binding.cooldownExercises;

        recyclerViewWarmUp.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewMain.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewCooldown.setLayoutManager(new LinearLayoutManager(getActivity()));



        warmUpAdapter = new ExerciseAdapter(new ArrayList<>());
        mainAdapter = new ExerciseAdapter(new ArrayList<>());
        cooldownAdapter = new ExerciseAdapter(new ArrayList<>());

        recyclerViewWarmUp.setAdapter(warmUpAdapter);
        recyclerViewMain.setAdapter(mainAdapter);
        recyclerViewCooldown.setAdapter(cooldownAdapter);
        observeExerciseViewModel();
    }

    private void observeExerciseViewModel() {

        warmupModel.get
    }

}
