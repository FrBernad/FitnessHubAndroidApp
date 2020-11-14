package com.example.fitnesshub.view.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.navigation.Navigation;

import com.example.fitnesshub.R;

public class PlayModeDialog extends AppCompatDialogFragment {

    private String routineData;
    private View view;

    public PlayModeDialog(String routineData, View view) {
        this.view = view;
        this.routineData = routineData;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.choose_execution_dialog, null);

        builder.setView(view).setNegativeButton("Close", (dialog, which) -> {
        });

        view.findViewById(R.id.simpleMode).setOnClickListener(v -> {
                    dismiss();
                    Navigation.findNavController(this.view).navigate(RoutineFragmentDirections.actionRoutineFragmentToRoutineExcecutionListFragment(routineData));
                }
        );
        return builder.create();
    }


}
