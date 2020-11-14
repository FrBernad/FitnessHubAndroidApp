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

import com.example.fitnesshub.databinding.FragmentHistoryBinding;
import com.example.fitnesshub.view.adapters.RoutinesAdapter;
import com.example.fitnesshub.viewModel.RoutinesViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {

    private RoutinesViewModel viewModel;
    private FragmentHistoryBinding binding;

    private RecyclerView recyclerView;

    private RoutinesAdapter routinesAdapter = new RoutinesAdapter(new ArrayList<>(),RoutineClickListener.MY_ACTIVITY);

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHistoryBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();

        recyclerView = binding.recyclerView;

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(getActivity()).get(RoutinesViewModel.class);

        viewModel.updateUserHistory();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(routinesAdapter);

        viewModel.getUserHistory().observe(getViewLifecycleOwner(), routines -> {
            if (routines != null) {
                System.out.println(routines);
                routinesAdapter.updateRoutines(routines);
            }
        });

    }
}