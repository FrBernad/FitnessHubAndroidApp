package com.example.fitnesshub.view.fragments;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnesshub.R;
import com.example.fitnesshub.databinding.FragmentRoutineBinding;
import com.example.fitnesshub.model.RoutineData;
import com.example.fitnesshub.view.adapters.ExercisesAdapter;
import com.example.fitnesshub.viewModel.ExercisesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RoutineFragment extends Fragment {

    private ExercisesViewModel viewModel;

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

    private boolean isFav = false;

    private View view;

    private ImageView image;

    private FragmentRoutineBinding binding;

    private int routineId;
    private RoutineData routineData;

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

        getActivity().findViewById(R.id.bottomNav).setVisibility(View.GONE);

        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            routineId = RoutineFragmentArgs.fromBundle(getArguments()).getRoutineId();
            routineData = RoutineFragmentArgs.fromBundle(getArguments()).getRoutineData();
            image.setImageResource(Integer.parseInt(routineData.getImage()));
        }

        playModeDialog = new PlayModeDialog(routineData.getTitle(), getView());

        playBtn.setOnClickListener(v -> {
            openPlayModeDialog();
        });

        title.setText(routineData.getTitle());
        author.setText(routineData.getAuthor().getUsername());
        detail.setText(routineData.getDetail());

        viewModel = new ViewModelProvider(getActivity()).get(ExercisesViewModel.class);
        viewModel.refresh(routineId);

        recyclerViewWarmUp.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewWarmUp.setAdapter(warmUpAdapter);

        recyclerViewMain.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewMain.setAdapter(mainAdapter);

        recyclerViewCooldown.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewCooldown.setAdapter(cooldownAdapter);

        observeExerciseViewModel();
    }

    private void observeExerciseViewModel() {
        viewModel.getWarmupExercises().observe(getViewLifecycleOwner(), warmupExercises -> {
            if (warmupExercises != null) {
                warmUpAdapter.updateExercises(warmupExercises);
            }
        });

        viewModel.getMainExercises().observe(getViewLifecycleOwner(), mainExercises -> {
            if (mainExercises != null) {
                mainAdapter.updateExercises(mainExercises);
            }
        });

        viewModel.getCooldownExercises().observe(getViewLifecycleOwner(), cooldownExercises -> {
            if (cooldownExercises != null) {
                cooldownAdapter.updateExercises(cooldownExercises);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().findViewById(R.id.bottomNav).setVisibility(View.VISIBLE);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.findItem(R.id.app_bar_share).setVisible(true);
        menu.findItem(R.id.app_bar_rate).setVisible(true);
        menu.findItem(R.id.app_bar_favorite_outlined).setVisible(true);

        fav = menu.findItem(R.id.app_bar_favorite_filled);
        unfav = menu.findItem(R.id.app_bar_favorite_outlined);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.app_bar_rate) {
            openRateDialog();
        } else if (id == R.id.app_bar_favorite_filled) {
            isFav = true;
            changeFavStatus();
        } else if (id == R.id.app_bar_favorite_outlined) {
            isFav = false;
            changeFavStatus();
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void openRateDialog() {
        RateDialog rateDialog = new RateDialog(routineId, getActivity());
        rateDialog.show(getParentFragmentManager(), "Example dialog");
    }

    public void changeFavStatus() {
        fav.setVisible(!isFav);
        unfav.setVisible(isFav);
    }

    public void openPlayModeDialog() {
        playModeDialog.show(getParentFragmentManager(), "example dialog");
    }


}