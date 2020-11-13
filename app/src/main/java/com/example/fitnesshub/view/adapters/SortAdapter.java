package com.example.fitnesshub.view.adapters;

import android.view.View;
import android.widget.AdapterView;


import com.example.fitnesshub.viewModel.RoutinesViewModel;

public class SortAdapter implements AdapterView.OnItemSelectedListener {

    private RoutinesViewModel viewModel;

    public SortAdapter(RoutinesViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
