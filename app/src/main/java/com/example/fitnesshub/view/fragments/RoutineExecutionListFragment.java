package com.example.fitnesshub.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fitnesshub.R;
import com.example.fitnesshub.databinding.FragmentRoutineExecutionListBinding;
import com.example.fitnesshub.model.ExerciseData;
import com.example.fitnesshub.view.activities.MainActivity;
import com.example.fitnesshub.view.adapters.ExercisesAdapter;
import com.example.fitnesshub.viewModel.ExercisesViewModel;

import java.util.ArrayList;

public class RoutineExecutionListFragment extends Fragment {

    private FragmentRoutineExecutionListBinding binding;

    private ExercisesViewModel viewModel;

    private ExercisesAdapter warmUpAdapter = new ExercisesAdapter(new ArrayList<>());
    private ExercisesAdapter mainAdapter = new ExercisesAdapter(new ArrayList<>());
    private ExercisesAdapter cooldownAdapter = new ExercisesAdapter(new ArrayList<>());
    private ExercisesAdapter[] adapters = new ExercisesAdapter[3];

    private RecyclerView recyclerViewWarmUp;
    private RecyclerView recyclerViewMain;
    private RecyclerView recyclerViewCooldown;

    private static final int WARMUP_CYCLE = 0;
    private static final int MAIN_CYCLE = 1;
    private static final int COOLDOWN_CYCLE = 2;
    private static final int PLAYING = 0;
    private static final int PAUSED = 1;
    private static final int NOTRUNNING = 2;


    private int currentCycle;
    private int currentExercise;
    private ExercisesAdapter currentAdapter;
    private boolean finished;
    private int status; //la uso para ver si el ejercicio esta corriendo o pausado
    private boolean executed; //me fijo para ver si presiono play alguna la primera vez

    private TextView title;

    private MainActivity mainActivity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRoutineExecutionListBinding.inflate(getLayoutInflater());

        recyclerViewWarmUp = binding.warmUpExercises;
        recyclerViewMain = binding.mainExercises;
        recyclerViewCooldown = binding.cooldownExercises;

        title = binding.routineNameTitleInExecutionList;

        binding.executionBar.play.setOnClickListener(v -> playExecution());
        binding.executionBar.pause.setOnClickListener(v -> pauseExecution());
        binding.executionBar.next.setOnClickListener(v -> nextExecution());
        binding.executionBar.previous.setOnClickListener(v -> previousExecution());
        binding.executionBar.previous.setVisibility(View.GONE);
        binding.executionBar.next.setVisibility(View.GONE);
        adapters[0] = warmUpAdapter;
        adapters[1] = mainAdapter;
        adapters[2]= cooldownAdapter;

        View view = binding.getRoot();

        mainActivity = (MainActivity) getActivity();
        mainActivity.setNavigationVisibility(false);

        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            title.setText(RoutineExecutionListFragmentArgs.fromBundle(getArguments()).getRoutineTitle());
        }

        viewModel = new ViewModelProvider(getActivity()).get(ExercisesViewModel.class);

        recyclerViewWarmUp.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewWarmUp.setAdapter(warmUpAdapter);

        recyclerViewMain.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewMain.setAdapter(mainAdapter);

        recyclerViewCooldown.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewCooldown.setAdapter(cooldownAdapter);
        if (!viewModel.getIsFirstTime()) { //me fijo si es necesario recuperar los datos
            currentExercise = viewModel.getCurrentExercise();
            currentCycle = viewModel.getCurrentCycle();
            executed = viewModel.getExecuted(); //si fue ejecutada en el onresume tengo que fijarme en que estado esta el ejercicio.
            status = viewModel.getStatus();
        } else {
            currentCycle = 0;
            currentExercise = 0;
            executed = false;
            viewModel.setIsFirstTime(false);
        }

        observeExerciseViewModel();

        mainActivity = (MainActivity) getActivity();
        mainActivity.setNavigationVisibility(false);

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
    public void onResume() {
        super.onResume();
        if(executed){
            binding.executionBar.previous.setVisibility(View.VISIBLE);
            binding.executionBar.next.setVisibility(View.VISIBLE);

            if(status == PLAYING){
                binding.executionBar.play.setVisibility(View.INVISIBLE);
                binding.executionBar.pause.setVisibility(View.VISIBLE);
                viewModel.getCountDownTimer().resume();
            }else if(status == PAUSED){
                binding.executionBar.play.setVisibility(View.VISIBLE);
                binding.executionBar.pause.setVisibility(View.INVISIBLE);
            }
        }



    }

    private void playExecution() {
        binding.executionBar.play.setVisibility(View.INVISIBLE);
        binding.executionBar.pause.setVisibility(View.VISIBLE);
        status = PLAYING;
        finished = false;
        if(executed) {
            viewModel.getCountDownTimer().resume(); //me fijo si fue ejecutada entonces corresponde reanudar el ejercicio
        }
        else{
            binding.executionBar.previous.setVisibility(View.VISIBLE);
            binding.executionBar.next.setVisibility(View.VISIBLE);
            executed = true;
            viewModel.getCountDownTimer().start(getNextExercise().getTime() * 1000, 1000);
            viewModel.getCountDownTimer().getStatus().observe(getViewLifecycleOwner(), countDown -> {
                if (countDown.isFinished() && !finished) {
                    ExerciseData exercise;
                    currentAdapter.getExercise(currentExercise).setRunning(false);
                    currentAdapter.notifyItemChanged(currentExercise);
                    currentExercise++;
                    if ((exercise = getNextExercise()) != null) {
                        viewModel.getCountDownTimer().start(exercise.getTime() * 1000, 1000);
                    }
                }
            });
        }
    }

    private void pauseExecution() {
        binding.executionBar.play.setVisibility(View.VISIBLE);
        binding.executionBar.pause.setVisibility(View.INVISIBLE);
        viewModel.getCountDownTimer().pause();
        status = PAUSED;
    }

    private void nextExecution() {
        viewModel.getCountDownTimer().stop();
        currentAdapter.getExercise(currentExercise).setRunning(false);
        currentAdapter.notifyItemChanged(currentExercise);
        currentExercise++;
        ExerciseData exercise = getNextExercise();
        if (exercise != null) {
            viewModel.getCountDownTimer().start((exercise.getTime() + 1) * 1000, 1000);
            if(status == PAUSED)
                viewModel.getCountDownTimer().pause();
        }
        else{
            //terminaste la rutina!
        }

    }

    private ExerciseData getNextExercise() {
        ExerciseData exercise = null;
        currentAdapter = getCurrentAdapter();
        if (currentAdapter != null) {
            exercise = currentAdapter.getExercise(currentExercise);
            exercise.setRunning(true);
            currentAdapter.notifyItemChanged(currentExercise);
        }
        return exercise;
    }

    public ExercisesAdapter getCurrentAdapter() {
        if (currentExercise >= adapters[currentCycle].getExerciseList().size()) {
            if (currentCycle == COOLDOWN_CYCLE) {
                finished = true;
                return null;
            }
            currentCycle++;
            currentExercise = 0;
        }
        return adapters[currentCycle];

    }


    private void previousExecution() {
        viewModel.getCountDownTimer().stop();
        currentAdapter.getExercise(currentExercise).setRunning(false);
        currentAdapter.notifyItemChanged(currentExercise);
        currentExercise--;
        ExerciseData exercise = getPrevExercise();
        if (exercise != null) {
            viewModel.getCountDownTimer().start((exercise.getTime() + 1) * 1000, 1000);
            if(status == PAUSED){
                viewModel.getCountDownTimer().pause();
            }
        }
    }

    private ExerciseData getPrevExercise() {
        ExerciseData exercise;
        currentAdapter = getPrevAdapter();
        if (currentAdapter == null) {
           currentAdapter = warmUpAdapter;
           currentExercise = 0;
        }
        exercise = currentAdapter.getExercise(currentExercise);
        exercise.setRunning(true);
        currentAdapter.notifyItemChanged(currentExercise);
        return exercise;

    }

    private ExercisesAdapter getPrevAdapter(){
        if(currentExercise == -1){
            if(currentCycle == WARMUP_CYCLE){
                return null;
            }
            currentCycle--;
            currentExercise = adapters[currentCycle].getExerciseList().size()-1;
            return adapters[currentCycle];
        }
        return adapters[currentCycle];
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewModel.setStatus(status);
        viewModel.setCurrentExercise(currentExercise);
        viewModel.setCurrentCycle(currentCycle);
        viewModel.setExecuted(executed);
        if(executed){
            viewModel.getCountDownTimer().pause();
        }
    }



}


