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
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.fitnesshub.R;
import com.example.fitnesshub.databinding.AlarmDialogBinding;
import com.example.fitnesshub.model.RoutineData;
import com.example.fitnesshub.util.AlarmReceiver;
import com.example.fitnesshub.viewModel.RoutinesViewModel;

import java.util.Calendar;

public class AlarmDialog extends AppCompatDialogFragment {

    private AlarmDialogBinding binding;

    private TimePicker timePicker;

    private PendingIntent pendingIntent;

    private RoutinesViewModel viewModel;

    public AlarmDialog(RoutinesViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        binding = AlarmDialogBinding.inflate(getActivity().getLayoutInflater());
        View view = binding.getRoot();

        RoutineData routineData = viewModel.getCurrentRoutine().getValue();

        timePicker = binding.timePicker;
        timePicker.setIs24HourView(true);

        builder.setView(view).setNegativeButton(R.string.Close, (dialog, which) -> {
        });

        Intent alarmIntent = new Intent(getContext(), AlarmReceiver.class);

        alarmIntent.putExtra("routineId", routineData.getId());
        alarmIntent.putExtra("routineTitle", routineData.getTitle());

        pendingIntent = PendingIntent.getBroadcast(getContext(), routineData.getId(), alarmIntent, 0);

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
        int interval = 1000 * 60 * 60 * 24;

        int hour = timePicker.getHour();
        int minutes = timePicker.getMinute();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minutes);

        manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pendingIntent);
    }

    public void cancel() {
        AlarmManager manager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
    }
}
