package com.example.fitnesshub.view.fragments;

import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import com.example.fitnesshub.R;
import com.example.fitnesshub.model.RoutineData;
import com.example.fitnesshub.view.fragments.ProfileFragmentDirections;
import com.example.fitnesshub.view.fragments.RoutinesFragmentDirections;
import com.example.fitnesshub.viewModel.RoutinesViewModel;

public class RoutineClickListener implements View.OnClickListener {

    private RoutinesViewModel routineViewModel;
    private int from;
    public static final int ROUTINES_ID = 1;
    public static final int FAV_ID = 2;
    public static final int MY_ACTIVITY = 3;

    public RoutineClickListener(RoutinesViewModel routineViewModel, int from) {
        this.routineViewModel = routineViewModel;
        this.from = from;
    }

    @Override
    public void onClick(View view) {
        int routineId = Integer.parseInt(((TextView) view.findViewById(R.id.routineId)).getText().toString());
        routineViewModel.getRoutineById(routineId);
        switch (from) {
            case ROUTINES_ID:
                RoutinesFragmentDirections.ActionRoutinesFragmentToRoutineFragment action1 = RoutinesFragmentDirections.actionRoutinesFragmentToRoutineFragment();
                action1.setRoutineId(routineId);
                Navigation.findNavController(view).navigate(action1);
                break;
            case FAV_ID:
                ProfileFragmentDirections.ActionMeFragmentToRoutineFragment action2 = ProfileFragmentDirections.actionMeFragmentToRoutineFragment();
                action2.setRoutineId(routineId);
                Navigation.findNavController(view).navigate(action2);
                break;
            case MY_ACTIVITY:
                MyActivityFragmentDirections.ActionMyActivityFragmentToRoutineFragment action3 = MyActivityFragmentDirections.actionMyActivityFragmentToRoutineFragment();
                action3.setRoutineId(routineId);
                Navigation.findNavController(view).navigate(action3);
                break;
        }
    }
}
