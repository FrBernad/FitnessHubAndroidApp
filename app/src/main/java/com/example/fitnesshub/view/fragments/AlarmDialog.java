package com.example.fitnesshub.view.fragments;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.fitnesshub.R;
import com.example.fitnesshub.databinding.AlarmDialogBinding;
import com.example.fitnesshub.util.AlarmReceiver;

import java.util.Calendar;

public class AlarmDialog extends AppCompatDialogFragment {

    private AlarmDialogBinding binding;

    private PendingIntent pendingIntent;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        binding = AlarmDialogBinding.inflate(getActivity().getLayoutInflater());
        View view = binding.getRoot();

        builder.setView(view).setNegativeButton(R.string.Close, (dialog, which) -> {

        });

        Intent alarmIntent = new Intent(getContext(), AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(getContext(), 0, alarmIntent, 0);

        binding.setAlarm.setOnClickListener(v -> {
            start();
            Toast.makeText(getContext(), "START ALARM", Toast.LENGTH_SHORT).show();
        });

        binding.cancelAlarm.setOnClickListener(v -> {
            cancel();
            Toast.makeText(getContext(), "CANCEL ALARM", Toast.LENGTH_SHORT).show();
        });

        return builder.create();
    }

    public void start() {
        AlarmManager manager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        int interval = 1000 * 60 * 20;

        /* Set the alarm to start at 10:30 AM */
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 30);

        /* Repeating on every 20 minutes interval */
        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                1000 * 60 * 20, pendingIntent);
    }

    public void cancel() {
        AlarmManager manager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
    }
}
