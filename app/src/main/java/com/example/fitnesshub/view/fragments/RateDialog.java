package com.example.fitnesshub.view.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.fitnesshub.R;
import com.example.fitnesshub.databinding.RatingDialogBinding;
import com.example.fitnesshub.viewModel.ExercisesViewModel;
import com.example.fitnesshub.viewModel.RoutineListViewModel;

public class RateDialog extends AppCompatDialogFragment {

    private int routineId;
    private ExercisesViewModel viewModel;
    private RatingDialogBinding binding;
    private RatingBar ratingBar;

    public RateDialog(int routineId, FragmentActivity activity) {
        this.routineId = routineId;
        viewModel = new ViewModelProvider(activity).get(ExercisesViewModel.class);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        binding = RatingDialogBinding.inflate(getActivity().getLayoutInflater());

        ratingBar = binding.ratingBar;

        View view = binding.getRoot();

        builder.setView(view).setTitle("Rate the routine").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("Ok", (dialog, which) -> {
            viewModel.rateRoutine(routineId,ratingBar.getNumStars());
        });
        return builder.create();
    }
}
