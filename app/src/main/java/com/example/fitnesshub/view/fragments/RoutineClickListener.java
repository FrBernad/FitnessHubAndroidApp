package com.example.fitnesshub.view.fragments;

import android.view.View;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.example.fitnesshub.R;
import com.example.fitnesshub.model.RoutineData;
import com.example.fitnesshub.view.fragments.ProfileFragmentDirections;
import com.example.fitnesshub.view.fragments.RoutinesFragmentDirections;

public class RoutineClickListener implements View.OnClickListener {

    private RoutineData routineData;
    private int from;
    public static final int ROUTINES_ID = 1;
    public static final int FAV_ID = 2;

    public RoutineClickListener(RoutineData routineData, int from) {
        this.routineData = routineData;
        this.from = from;
    }

    @Override
    public void onClick(View view) {
        int routineId = Integer.parseInt(((TextView) view.findViewById(R.id.routineId)).getText().toString());
        switch (from) {
            case ROUTINES_ID:
                RoutinesFragmentDirections.ActionRoutinesFragmentToRoutineFragment action1 = RoutinesFragmentDirections.actionRoutinesFragmentToRoutineFragment(routineData);
                action1.setRoutineId(routineId);
                action1.setRoutineData(routineData);
                Navigation.findNavController(view).navigate(action1);
                break;
            case FAV_ID:
                ProfileFragmentDirections.ActionMeFragmentToRoutineFragment action2 = ProfileFragmentDirections.actionMeFragmentToRoutineFragment(routineData);
                action2.setRoutineId(routineId);
                action2.setRoutineData(routineData);
                Navigation.findNavController(view).navigate(action2);
                break;
        }
    }
}
