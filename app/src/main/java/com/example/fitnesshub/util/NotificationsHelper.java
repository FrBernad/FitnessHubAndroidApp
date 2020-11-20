package com.example.fitnesshub.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.navigation.NavDeepLinkBuilder;

import com.example.fitnesshub.R;
import com.example.fitnesshub.view.activities.MainActivity;

import java.util.Random;

public class NotificationsHelper {
    private static final String CHANNEL_ID = "FitnessHub_channel";
    private static final int NOTIFICATION_ID = 420;

    private static NotificationsHelper instance;
    private Context context;

    public NotificationsHelper(Context context) {
        this.context = context;
    }

    public static NotificationsHelper getInstance(Context context) {
        if (instance == null) {
            instance = new NotificationsHelper(context);
        }
        return instance;
    }

    public void createNotification(int routineId, String routineTitle) {
        createNotificationChannel();

        Bundle bundle = new Bundle();
        bundle.putInt("routineId", routineId);
        PendingIntent pendingIntent = new NavDeepLinkBuilder(context).setComponentName(MainActivity.class).setGraph(R.navigation.app_navigation).setDestination(R.id.routineFragment).setArguments(bundle).createPendingIntent();

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Time to exercise!")
                .setContentText("Click here to execute your programmed routine: " + routineTitle)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();

        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String name = CHANNEL_ID;
            String description = "Routine remember notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }

    }
}
