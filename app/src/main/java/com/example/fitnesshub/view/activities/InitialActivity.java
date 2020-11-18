package com.example.fitnesshub.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import com.example.fitnesshub.R;
import com.example.fitnesshub.model.AppPreferences;
import com.example.fitnesshub.view.fragments.HomeFragment;

import java.util.List;

public class InitialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_initial);

        new Handler().postDelayed(() -> {

            Intent appLinkIntent = getIntent();
            Uri appLinkData = appLinkIntent.getData();
            AppPreferences preferences = new AppPreferences(this.getApplication());

            if(appLinkData!=null){ // cuando inicio la aplicacion desde un link
                String routineId = appLinkData.getLastPathSegment();
                newActivity(preferences,routineId);

            }else{ //Cuando inicio la aplicacion normalmente

                newActivity(preferences,null);
            }

        }, 3000);

    }

    private void newActivity(AppPreferences preferences, String routineId) {
        Intent intent;
        if (preferences.getAuthToken() != null) {

            intent = new Intent(InitialActivity.this, MainActivity.class);
            putAndStart(routineId, intent);
            return;
        }
        intent = new Intent(InitialActivity.this, LoginActivity.class);
        putAndStart(routineId, intent);

    }

    private void putAndStart(String routineId, Intent intent) {
        intent.putExtra("RoutineId", routineId);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish(); //elimino la actividad del stack
    }
}

