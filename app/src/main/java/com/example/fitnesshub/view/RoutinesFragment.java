package com.example.fitnesshub.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
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
import com.example.fitnesshub.model.RoutineData;
import com.example.fitnesshub.viewModel.RoutineListViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RoutinesFragment extends Fragment {

    private RoutineListViewModel viewModel;

    private RoutinesAdapter routinesAdapter = new RoutinesAdapter(new ArrayList<>());

    private FragmentRoutinesBinding binding;

    private NestedScrollView nestedScrollView;
    private RecyclerView recylcerView;
    private ProgressBar progressBar;

    boolean noMoreEntries = false;

    public RoutinesFragment() {
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRoutinesBinding.inflate(getLayoutInflater());

        nestedScrollView = binding.scrollView;
        recylcerView = binding.recyclerView;
        progressBar = binding.progressBar;

        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(RoutineListViewModel.class);

        noMoreEntries = viewModel.updateData(progressBar, routinesAdapter);

        recylcerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recylcerView.setAdapter(routinesAdapter);


        nestedScrollView.setOnScrollChangeListener(
                (NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                    if (!noMoreEntries && scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                        noMoreEntries = viewModel.updateData(progressBar, routinesAdapter);
                    }
                });
    }

}