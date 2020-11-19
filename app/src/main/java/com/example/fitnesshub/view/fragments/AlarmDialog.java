package com.example.fitnesshub.view.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.fitnesshub.R;
import com.example.fitnesshub.databinding.AlarmDialogBinding;

public class AlarmDialog extends AppCompatDialogFragment {

    private AlarmDialogBinding binding;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        binding = AlarmDialogBinding.inflate(getActivity().getLayoutInflater());
        View view = binding.getRoot();

        builder.setView(view).setNegativeButton(R.string.Close, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }
}
