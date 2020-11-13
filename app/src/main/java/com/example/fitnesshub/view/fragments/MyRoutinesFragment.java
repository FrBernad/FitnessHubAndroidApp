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

import com.example.fitnesshub.databinding.FragmentMyRoutinesBinding;
import com.example.fitnesshub.view.adapters.RoutinesAdapter;
import com.example.fitnesshub.viewModel.RoutinesViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class MyRoutinesFragment extends Fragment {

    private RoutinesViewModel viewModel;
    private FragmentMyRoutinesBinding binding;

    private RecyclerView recyclerView;

    private RoutinesAdapter routinesAdapter = new RoutinesAdapter(new ArrayList<>());


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMyRoutinesBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();

        recyclerView = binding.userRecyclerView;

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(getActivity()).get(RoutinesViewModel.class);

        viewModel.updateUserRoutines();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(routinesAdapter);

        viewModel.getUserRoutines().observe(getViewLifecycleOwner(), routines -> {
            if (routines != null) {
                System.out.println(routines);
                routinesAdapter.updateRoutines(routines);
            }
        });

    }

}