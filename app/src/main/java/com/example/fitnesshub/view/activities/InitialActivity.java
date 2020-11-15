package com.example.fitnesshub.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.fitnesshub.R;
import com.example.fitnesshub.model.AppPreferences;

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
            Intent intent;
            AppPreferences preferences = new AppPreferences(this.getApplication());

            if(appLinkData!=null){ // cuando inicio la aplicacion desde un link
                List<String> params = appLinkData.getPathSegments();
                String routineId = params.get(params.size()-1);

            }else{ //Cuando inicio la aplicacion normalmente

                if (preferences.getAuthToken() != null) {

                    intent = new Intent(InitialActivity.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish(); //elimino la actividad del stack
                    return;
                }
                intent = new Intent(InitialActivity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish(); //elimino la actividad del stack
            }

        }, 3000);

    }
}

