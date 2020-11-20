package com.example.fitnesshub.view.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.fitnesshub.R;
import com.example.fitnesshub.databinding.FragmentShowExerciseBinding;
import com.example.fitnesshub.model.ExerciseData;


public class ShowExerciseDialog extends AppCompatDialogFragment {

    private ExerciseData exerciseData;

    public ShowExerciseDialog(ExerciseData exerciseData) {
        this.exerciseData = exerciseData;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.PopUp);


        FragmentShowExerciseBinding binding = FragmentShowExerciseBinding.inflate(getLayoutInflater());
        binding.setExerciseData(exerciseData);

        binding.showExerciseReps.setVisibility(exerciseData.getReps() != 0 ? View.VISIBLE : View.GONE);
        binding.showExerciseTime.setVisibility(exerciseData.getTime() != 0 ? View.VISIBLE : View.GONE);

        View view = binding.getRoot();


        builder.setView(view).setNegativeButton(getString(R.string.Close), (dialog, which) -> {

        });

        this.setCancelable(false);
        return builder.create();
    }

}
