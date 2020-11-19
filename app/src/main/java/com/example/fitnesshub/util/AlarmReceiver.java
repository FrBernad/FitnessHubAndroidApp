package com.example.fitnesshub.util;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int routineId = intent.getIntExtra("routineId", -1);
        String routineTitle = intent.getStringExtra("routineTitle");

        if (routineId != -1) {
            NotificationsHelper.getInstance(context).createNotification(routineId,routineTitle);
        }
    }
}
