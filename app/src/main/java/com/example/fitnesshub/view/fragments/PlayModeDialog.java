package com.example.fitnesshub.view.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.fitnesshub.R;
import com.example.fitnesshub.model.RoutineData;
import com.example.fitnesshub.viewModel.RoutinesViewModel;

public class PlayModeDialog extends AppCompatDialogFragment {

    private RoutineData routineData;
    private View view;
    private RoutinesViewModel viewModel;

    public PlayModeDialog(RoutineData routineData, View view) {
        this.view = view;
        this.routineData = routineData;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.PopUp);
        this.viewModel = new ViewModelProvider(getActivity()).get(RoutinesViewModel.class);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.choose_execution_dialog, null);

        builder.setView(view).setTitle(getString(R.string.ExecutionRoutineDialog).toUpperCase()).setNegativeButton(getString(R.string.Close), (dialog, which) -> {
        });

        view.findViewById(R.id.simpleMode).setOnClickListener(v -> {
                    dismiss();
                    viewModel.addRoutineExcecution(routineData.getId());
                    Navigation.findNavController(this.view).navigate(RoutineFragmentDirections.actionRoutineFragmentToRoutineExcecutionListFragment(routineData.getTitle()));
                }
        );

        view.findViewById(R.id.detailedMode).setOnClickListener(v -> {
                    dismiss();
                    viewModel.addRoutineExcecution(routineData.getId());
                    Navigation.findNavController(this.view).navigate(RoutineFragmentDirections.actionRoutineFragmentToRoutineExecutionExercise(routineData.getTitle()));
                }
        );

        this.setCancelable(false);
        return builder.create();
    }


}
