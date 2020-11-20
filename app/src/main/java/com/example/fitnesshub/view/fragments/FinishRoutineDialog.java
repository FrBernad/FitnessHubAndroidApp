package com.example.fitnesshub.view.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.navigation.Navigation;

import com.example.fitnesshub.R;

public class FinishRoutineDialog extends AppCompatDialogFragment {


    private View mainView;
    public static final int LIST_EXEC = 1;
    public static final int DETAIL_EXEC = 0;
    private int execType;

    public FinishRoutineDialog(View view, int execType) {
        this.mainView = view;
        this.execType = execType;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.PopUp);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.finish_routine_dialog, null);

        builder.setView(view).setNegativeButton(R.string.Close, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (execType == LIST_EXEC) {
                    Navigation.findNavController(mainView).navigate(RoutineExecutionListFragmentDirections.actionRoutineExcecutionListFragmentToRoutinesFragment());
                } else if (execType == DETAIL_EXEC) {
                    Navigation.findNavController(mainView).navigate(RoutineExecutionExerciseFragmentDirections.actionRoutineExecutionExerciseToRoutinesFragment());
                }
            }
        });

        this.setCancelable(false);
        return builder.create();
    }
}
