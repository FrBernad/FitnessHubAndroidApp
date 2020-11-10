package com.example.fitnesshub.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnesshub.R;
import com.example.fitnesshub.databinding.FragmentRoutinesBinding;
import com.example.fitnesshub.viewModel.RoutineListViewModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

public class MeFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    private RoutineListViewModel viewModel;
    private Spinner spinner;
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private final FavoriteAdapter favoriteAdapter = new FavoriteAdapter(new ArrayList<>());
    private FragmentRoutinesBinding binding;
    private RecyclerView favoriteCardsList;
    private TextView routineListError;
    private ProgressBar routineListLoadingView;
    private SwipeRefreshLayout routinesRefreshLayout;

    public MeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        spinner = view.findViewById(R.id.genderSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.genders,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        mDisplayDate = (TextView) view.findViewById(R.id.tvDate);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener,year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                mDisplayDate.setText(date);
            }
        };

        binding = FragmentRoutinesBinding.inflate(getLayoutInflater());

//        favoriteCardsList = binding.favoriteCardsList;
        routineListError = binding.routineListError;
        routineListLoadingView = binding.routineListLoadingView;
        routinesRefreshLayout = binding.routinesRefreshLayout;
        
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(RoutineListViewModel.class);
        viewModel.refresh();

        favoriteCardsList.setLayoutManager(new LinearLayoutManager(getContext()));
        favoriteCardsList.setAdapter(favoriteAdapter);

        observeRoutinesViewModel();
    }


    private void observeRoutinesViewModel() {
        viewModel.getRoutineCards().observe(getViewLifecycleOwner(), routineCards -> {
            if (routineCards != null) {
                favoriteCardsList.setVisibility(View.VISIBLE);
//                favoriteAdapter.updateRoutinesList(routineCards);
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
                    favoriteCardsList.setVisibility(View.GONE);
                    routineListError.setVisibility(View.GONE);
                }
            }
        });

    }


}