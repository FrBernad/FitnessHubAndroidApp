package com.example.fitnesshub.view.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.fitnesshub.R;
import com.example.fitnesshub.view.fragments.RoutineFragment;
import com.example.fitnesshub.viewModel.FavouritesRoutinesViewModel;
import com.example.fitnesshub.viewModel.UserViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static androidx.core.app.NotificationCompat.CATEGORY_REMINDER;

public class MainActivity extends AppCompatActivity {


    private BottomNavigationView bottomNavigationView;
    private UserViewModel viewModel;

    private static final String CHANNEL_ID = "Routine channel";
    private static final String CHANNEL_NAME = "Routine recordatory";
    private static final String CHANNEL_DESC = "Get a reminder so you remember to train";
    private static final String GROUP_KEY_ROUTINES = "ROUTINES";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpBottomNavigation();
        setSupportActionBar(findViewById(R.id.main_toolbar));
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        new ViewModelProvider(this).get(FavouritesRoutinesViewModel.class).updateData();
        viewModel.setUserData();
        Navigation.findNavController()
        createNotificationChannel();
        findViewById(R.id.notificationBtn).setOnClickListener(v -> displayNotification());

    }

    public void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel routineCh = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            routineCh.setDescription(CHANNEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(routineCh);
        }
    }

    private void displayNotification(){

        /*Create an explicit intent for an activity in your app*/

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("name",)
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);


        /*Configuro el contenido y el canal de la notificaion*/
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Nombre de la rutina")
                .setContentText("Comenza tu entrenamiento con la rutina")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .setGroup(GROUP_KEY_ROUTINES);

        NotificationManagerCompat mNotificationMgr = NotificationManagerCompat.from(this);
        mNotificationMgr.notify((int)System.currentTimeMillis(),mBuilder.build());

    }

    public void setUpBottomNavigation() {
        bottomNavigationView = findViewById(R.id.bottomNav);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mainNavFragment);

        assert navHostFragment != null;
        NavigationUI.setupWithNavController(bottomNavigationView,
                navHostFragment.getNavController());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(false);
        getMenuInflater().inflate(R.menu.main_toolbar, menu);
        menu.findItem(R.id.app_bar_leave_session).setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.app_bar_leave_session) {
            logout();
            return true;
        }
        return false;
    }

    private void logout() {
        viewModel.logout();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish(); //elimino la actividad del stack
    }


}