package com.example.fitnesshub.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnesshub.R;
import com.example.fitnesshub.databinding.FragmentRoutineBinding;
import com.example.fitnesshub.model.RoutineData;
import com.example.fitnesshub.view.activities.MainActivity;
import com.example.fitnesshub.view.adapters.ExercisesAdapter;
import com.example.fitnesshub.viewModel.ExercisesViewModel;
import com.example.fitnesshub.viewModel.FavouritesRoutinesViewModel;
import com.example.fitnesshub.viewModel.RoutinesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RoutineFragment extends Fragment {

    private ExercisesViewModel exercisesViewModel;
    private FavouritesRoutinesViewModel favViewModel;
    private RoutinesViewModel routinesViewModel;

    private ExercisesAdapter warmUpAdapter = new ExercisesAdapter(new ArrayList<>());
    private ExercisesAdapter mainAdapter = new ExercisesAdapter(new ArrayList<>());
    private ExercisesAdapter cooldownAdapter = new ExercisesAdapter(new ArrayList<>());

    private RecyclerView recyclerViewWarmUp;
    private RecyclerView recyclerViewMain;
    private RecyclerView recyclerViewCooldown;
    private FloatingActionButton playBtn;

    private TextView title;
    private TextView author;
    private TextView detail;

    private MenuItem fav;
    private MenuItem unfav;

    private View view;

    private ImageView image;

    private FragmentRoutineBinding binding;

    private RoutineData routineData;

    private int routineId;

    private MainActivity main;

    PlayModeDialog playModeDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRoutineBinding.inflate(getLayoutInflater());

        recyclerViewWarmUp = binding.warmUpExercises;
        recyclerViewMain = binding.mainExercises;
        recyclerViewCooldown = binding.cooldownExercises;

        title = binding.routineNameTitle;
        author = binding.routineAuthorName;
        detail = binding.routineDescription;

        playBtn = binding.playBtn;

        image = binding.imageView;

        view = binding.getRoot();

        main = (MainActivity) getActivity();

        main.showUpButton();
        main.setNavigationVisibility(false);

        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            routineId = RoutineFragmentArgs.fromBundle(getArguments()).getRoutineId();
        }

        routinesViewModel = new ViewModelProvider(getActivity()).get(RoutinesViewModel.class);

        routinesViewModel.getCurrentRoutine().observe(getViewLifecycleOwner(), routineData -> {
            if (routineData != null) {
                this.routineData = routineData;
                image.setImageResource(Integer.parseInt(routineData.getImage()));
                title.setText(routineData.getTitle());
                author.setText(routineData.getAuthor().getUsername());
                detail.setText(routineData.getDetail());
                playModeDialog = new PlayModeDialog(routineData, getView());
            }
        });

        playBtn.setOnClickListener(v -> openPlayModeDialog());

        favViewModel = new ViewModelProvider(getActivity()).get(FavouritesRoutinesViewModel.class);

        exercisesViewModel = new ViewModelProvider(getActivity()).get(ExercisesViewModel.class);
        exercisesViewModel.refresh(routineId);

        recyclerViewWarmUp.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewWarmUp.setAdapter(warmUpAdapter);

        recyclerViewMain.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewMain.setAdapter(mainAdapter);

        recyclerViewCooldown.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewCooldown.setAdapter(cooldownAdapter);

        observeExerciseViewModel();
    }

    private void observeExerciseViewModel() {
        exercisesViewModel.getWarmupExercises().observe(getViewLifecycleOwner(), warmupExercises -> {
            if (warmupExercises != null) {
                warmUpAdapter.updateExercises(warmupExercises);
            }
        });

        exercisesViewModel.getMainExercises().observe(getViewLifecycleOwner(), mainExercises -> {
            if (mainExercises != null) {
                mainAdapter.updateExercises(mainExercises);
            }
        });

        exercisesViewModel.getCooldownExercises().observe(getViewLifecycleOwner(), cooldownExercises -> {
            if (cooldownExercises != null) {
                cooldownAdapter.updateExercises(cooldownExercises);
            }
        });

    }

    @Override
    public void onDestroyView() {
        main.hideUpButton();
        super.onDestroyView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.findItem(R.id.app_bar_share).setVisible(true);
        menu.findItem(R.id.app_bar_rate).setVisible(true);
        menu.findItem(R.id.app_bar_favorite_outlined).setVisible(true);
        menu.findItem(R.id.app_bar_alarm).setVisible(true);

        fav = menu.findItem(R.id.app_bar_favorite_filled);
        unfav = menu.findItem(R.id.app_bar_favorite_outlined);

        favViewModel.getFavouriteRoutines()
                .observe(getViewLifecycleOwner(), favourites -> {
                    boolean isFav = false;
                    for (RoutineData routine : favourites) {
                        if (routine.getId() == routineId) {
                            isFav = true;
                            break;
                        }
                    }
                    if (isFav) {
                        favRoutine();
                    } else {
                        unfavRoutine();
                    }
                });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.app_bar_rate) {
            openRateDialog();
        } else if (id == R.id.app_bar_favorite_filled) {
            favViewModel.unfavRoutine(routineId);
            unfavRoutine();
        } else if (id == R.id.app_bar_favorite_outlined) {
            favViewModel.favRoutine(routineId);
            favRoutine();
        } else if (id == R.id.app_bar_share) {
            share();
        } else if(id == R.id.app_bar_alarm){
            openAlarmDialog();
        }else
        {
            return super.onOptionsItemSelected(item);
        }

        return true;
    }

    private void share() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, routineData.getTitle());
        sharingIntent.putExtra("RoutineId", routineId);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.subject) + ": http://www.fitnesshub.com/Routines/" + routineId);
        startActivity(Intent.createChooser(sharingIntent, "Share Rutine"));
    }

    public void openRateDialog() {
        RateDialog rateDialog = new RateDialog(routineId, getActivity());
        rateDialog.show(getParentFragmentManager(), "Example dialog");
    }

    public void favRoutine() {
        fav.setVisible(true);
        unfav.setVisible(false);
    }

    public void unfavRoutine() {
        fav.setVisible(false);
        unfav.setVisible(true);
    }

    public void openPlayModeDialog() {
        playModeDialog.show(getParentFragmentManager(), "example dialog");
    }

    public void openAlarmDialog() {
        AlarmDialog alarmDialog = new AlarmDialog(routinesViewModel);
        alarmDialog.show(getParentFragmentManager(), "example dialog2");
    }


}