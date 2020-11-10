package com.example.fitnesshub.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.fitnesshub.R;
import com.example.fitnesshub.databinding.FragmentRoutinesBinding;
import com.example.fitnesshub.viewModel.RoutineListViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RoutinesFragment extends Fragment {

    private RoutineListViewModel viewModel;

    private final RoutinesAdapter routinesAdapter = new RoutinesAdapter(new ArrayList<>());

    private FragmentRoutinesBinding binding;

    private RecyclerView routineCardsList;
    private TextView routineListError;
    private ProgressBar routineListLoadingView;
    private SwipeRefreshLayout routinesRefreshLayout;

    public RoutinesFragment() {
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRoutinesBinding.inflate(getLayoutInflater());

        routineCardsList = binding.routineCardsList;
        routineListError = binding.routineListError;
        routineListLoadingView = binding.routineListLoadingView;
        routinesRefreshLayout = binding.routinesRefreshLayout;

        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(RoutineListViewModel.class);
        viewModel.refresh();

        routineCardsList.setLayoutManager(new LinearLayoutManager(getContext()));
        routineCardsList.setAdapter(routinesAdapter);

        observeRoutinesViewModel();
    }

    private void observeRoutinesViewModel() {
        viewModel.getRoutineCards().observe(getViewLifecycleOwner(), routineCards -> {
            if (routineCards != null) {
                routineCardsList.setVisibility(View.VISIBLE);
                routinesAdapter.updateRoutinesList(routineCards);
            }
        });

        viewModel.getRoutineCardLoadError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                routineListError.setVisibility(error ? View.VISIBLE : View.GONE);
            }
        });

        viewModel.getLoading().observe(getViewLifecycleOwner(), loading -> {
            if (loading != null) {
                routineListLoadingView.setVisibility(loading ? View.VISIBLE : View.GONE);
                if (loading) {
                    routineCardsList.setVisibility(View.GONE);
                    routineListError.setVisibility(View.GONE);
                }
            }
        });

    }
}