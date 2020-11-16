package com.example.fitnesshub.view.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavArgument;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.fitnesshub.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavController aux = Navigation.findNavController(this,R.id.login_navigation);
        aux.addOnDestinationChangedListener((controller, destination, arguments) ->{
            if(destination.getId()==R.id.welcome){
                NavArgument arg = new NavArgument.Builder().setDefaultValue(null).build();
                destination.addArgument("RoutineId",arg);
            }

        });
        setContentView(R.layout.activity_login);
    }



}
