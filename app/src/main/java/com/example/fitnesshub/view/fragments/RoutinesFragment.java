package com.example.fitnesshub.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.fitnesshub.R;
import com.example.fitnesshub.databinding.FragmentRoutinesBinding;
import com.example.fitnesshub.view.adapters.OrderAdapter;
import com.example.fitnesshub.view.adapters.RoutinesAdapter;
import com.example.fitnesshub.view.adapters.SortAdapter;
import com.example.fitnesshub.viewModel.RoutineListViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RoutinesFragment extends Fragment {

    private RoutineListViewModel viewModel;

    private TextView textViewFilter;

    private RoutinesAdapter routinesAdapter = new RoutinesAdapter(new ArrayList<>());

    private FragmentRoutinesBinding binding;

    private NestedScrollView nestedScrollView;
    private RecyclerView recylcerView;
    private ProgressBar progressBar;

    private Spinner sortSpinner;
    private Spinner orderSpinner;

    boolean noMoreEntries = false;
    boolean searching = false;

    public RoutinesFragment() {
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRoutinesBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();

        nestedScrollView = binding.scrollView;
        recylcerView = binding.recyclerView;
        progressBar = binding.progressBar;

        return view;
    }

    private void setSpinners(View view) {
        sortSpinner = view.findViewById(R.id.sortDiscoverSpinner);
        ArrayAdapter<CharSequence> sortDiscoverAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.sort, android.R.layout.simple_spinner_item);
        sortDiscoverAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(sortDiscoverAdapter);
        sortSpinner.setOnItemSelectedListener(new SortAdapter(viewModel));

        orderSpinner = view.findViewById(R.id.orderDiscoverSpinner);
        ArrayAdapter<CharSequence> orderDiscoverAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.order, android.R.layout.simple_spinner_item);
        orderDiscoverAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orderSpinner.setAdapter(orderDiscoverAdapter);
        orderSpinner.setOnItemSelectedListener(new OrderAdapter(viewModel));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(RoutineListViewModel.class);

        viewModel.updateData();

        setSpinners(view);

        recylcerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recylcerView.setAdapter(routinesAdapter);

        viewModel.getRoutineCards().observe(getViewLifecycleOwner(), routines -> {
            if (routines != null) {
                routinesAdapter.updateRoutines(routines);
            }
        });

//        viewModel.getChangedOptions().observe(getViewLifecycleOwner(), changed -> {
//            if (changed != null) {
//                if (changed) {
//                    routinesAdapter.resetRoutines();
//                    viewModel.updateData();
//                }
//            }
//        });

        viewModel.getLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading != null) {
                if (isLoading) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.GONE);
                    searching = false;
                }
            }
        });

        viewModel.getNoMoreEntries().observe(getViewLifecycleOwner(), value -> {
            if (value != null) {
                noMoreEntries = value;
            }
        });

        nestedScrollView.setOnScrollChangeListener(
                (NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                    if (!searching && !noMoreEntries && scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                        searching = true;
                        viewModel.updateData();
                    }
                });
    }

}