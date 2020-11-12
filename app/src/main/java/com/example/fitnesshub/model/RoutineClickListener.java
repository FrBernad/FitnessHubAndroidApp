package com.example.fitnesshub.model;

import android.view.View;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.example.fitnesshub.R;
import com.example.fitnesshub.view.fragments.RoutinesFragmentDirections;

public class RoutineClickListener implements View.OnClickListener {

    private RoutineData routineData;

    public RoutineClickListener(RoutineData routineData) {
        this.routineData = routineData;
    }

    @Override
    public void onClick(View view) {
        int routineId = Integer.parseInt(((TextView) view.findViewById(R.id.routineId)).getText().toString());
        RoutinesFragmentDirections.ActionRoutinesFragmentToRoutineFragment action = RoutinesFragmentDirections.actionRoutinesFragmentToRoutineFragment(routineData);
        action.setRoutineId(routineId);
        action.setRoutineData(routineData);
        Navigation.findNavController(view).navigate(action);
    }
}
