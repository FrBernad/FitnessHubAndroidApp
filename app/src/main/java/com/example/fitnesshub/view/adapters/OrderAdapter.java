package com.example.fitnesshub.view.adapters;

import android.view.View;
import android.widget.AdapterView;

import com.example.fitnesshub.viewModel.RoutineListViewModel;

public class OrderAdapter implements AdapterView.OnItemSelectedListener {

    private RoutineListViewModel viewModel;


    public OrderAdapter(RoutineListViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
//        viewModel.orderRoutines(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
