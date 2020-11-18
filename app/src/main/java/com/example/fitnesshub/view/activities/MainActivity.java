package com.example.fitnesshub.view.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.example.fitnesshub.R;
import com.example.fitnesshub.viewModel.RoutinesViewModel;
import com.example.fitnesshub.viewModel.UserViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private RoutinesViewModel routinesViewModel;
    private UserViewModel userviewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent appLinkIntent = getIntent();
        setUpBottomNavigation();
        setSupportActionBar(findViewById(R.id.main_toolbar));
        userviewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userviewModel.setUserData();


            String id = appLinkIntent.getStringExtra("RoutineId");

            if(id!=null) {

                int idInt = verifyAndConvertId(id);

                if (idInt != -1) {
                   routinesViewModel =  new ViewModelProvider(this).get(RoutinesViewModel.class);
                   //routinesViewModel.getRoutineById(idInt);
                   //routinesViewModel.getExternLinkRoutine().observe(getLifecycle(),externRoutine ->{

                       if(externRoutine!=null){
                           //NavController aux = Navigation.findNavController(this,R.id.mainNavFragment);
                           //action
                           //navigate
                       }


                   });

                }
            }


    }
    private int verifyAndConvertId(String id){
        Integer aux;
        try {
            aux = Integer.parseInt(id);
        }catch (Exception e){
            return -1;
        }
        return aux;
    }


    public void setUpBottomNavigation() {
        bottomNavigationView = findViewById(R.id.bottomNav);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.mainNavFragment);
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
        userviewModel.logout();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish(); //elimino la actividad del stack
    }


}