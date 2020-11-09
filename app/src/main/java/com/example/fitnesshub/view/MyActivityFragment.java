package com.example.fitnesshub.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitnesshub.R;
import com.example.fitnesshub.databinding.FragmentRoutineBinding;
import com.example.fitnesshub.model.ExerciseOverviewInfo;

import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyActivityFragment extends Fragment {

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
        RecyclerView recyclerViewWarmUp = binding.warmUpExercises;
        RecyclerView recyclerViewMain = binding.mainExercises;
        RecyclerView recyclerViewCooldown = binding.cooldownExercises;

        recyclerViewWarmUp.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewMain.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewCooldown.setLayoutManager(new LinearLayoutManager(getActivity()));

        ArrayList<ExerciseOverviewInfo> warmUp = new ArrayList<>();
        ArrayList<ExerciseOverviewInfo> main = new ArrayList<>();
        ArrayList<ExerciseOverviewInfo> cooldown = new ArrayList<>();

        warmUp.add(new ExerciseOverviewInfo("Abs",4));
        warmUp.add(new ExerciseOverviewInfo("Abs",4));
        warmUp.add(new ExerciseOverviewInfo("Abs",4));

        main.add(new ExerciseOverviewInfo("Abs",4));
        main.add(new ExerciseOverviewInfo("Abs",4));
        main.add(new ExerciseOverviewInfo("Abs",4));

        cooldown.add(new ExerciseOverviewInfo("Abs",4));
        cooldown.add(new ExerciseOverviewInfo("Abs",4));
        cooldown.add(new ExerciseOverviewInfo("Abs",4));


        warmUpAdapter = new ExerciseAdapter(warmUp);
        mainAdapter = new ExerciseAdapter(main);
        cooldownAdapter = new ExerciseAdapter(cooldown);

        recyclerViewWarmUp.setAdapter(warmUpAdapter);
        recyclerViewMain.setAdapter(mainAdapter);
        recyclerViewCooldown.setAdapter(cooldownAdapter);
    }

}
