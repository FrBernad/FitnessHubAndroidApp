package com.example.fitnesshub.view.fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.fitnesshub.R;
import com.example.fitnesshub.databinding.FragmentRoutinesBinding;
import com.example.fitnesshub.view.adapters.RoutinesAdapter;
import com.example.fitnesshub.viewModel.RoutineListViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RoutinesFragment extends Fragment implements AdapterView.OnItemSelectedListener{

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

    public RoutinesFragment() {
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRoutinesBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();


        sortSpinner = view.findViewById(R.id.sortDiscoverSpinner);
        ArrayAdapter<CharSequence> sortDiscoverAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.sort,android.R.layout.simple_spinner_item);
        sortDiscoverAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(sortDiscoverAdapter);
        sortSpinner.setOnItemSelectedListener(this);


        orderSpinner = view.findViewById(R.id.orderDiscoverSpinner);
        ArrayAdapter<CharSequence> orderDiscoverAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.order,android.R.layout.simple_spinner_item);
        orderDiscoverAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orderSpinner.setAdapter(orderDiscoverAdapter);
        orderSpinner.setOnItemSelectedListener(this);

        nestedScrollView = binding.scrollView;
        recylcerView = binding.recyclerView;
        progressBar = binding.progressBar;


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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



}