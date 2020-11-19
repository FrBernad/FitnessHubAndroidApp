package com.example.fitnesshub.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fitnesshub.R;
import com.example.fitnesshub.databinding.FragmentRoutineExecutionExerciseBinding;
import com.example.fitnesshub.model.ExerciseData;
import com.example.fitnesshub.view.activities.MainActivity;
import com.example.fitnesshub.viewModel.ExercisesViewModel;

import java.util.ArrayList;

public class RoutineExecutionExerciseFragment extends Fragment {

    private FragmentRoutineExecutionExerciseBinding binding;
    private ExercisesViewModel viewModel;

    private ArrayList<ExerciseData> warmUp;
    private ArrayList<ExerciseData> main;
    private ArrayList<ExerciseData> cooldown;

    private static final int WARMUP_CYCLE = 0;
    private static final int MAIN_CYCLE = 1;
    private static final int COOLDOWN_CYCLE = 2;
    private static final String WARMUP_TITLE = "WARM UP";
    private static final String MAIN_TITLE = "MAIN EXERCISES";
    private static final String COOLDOWN_TITLE = "COOLDOWN";


    private int currentCycle; //rescatado
    private String cycleTitle; //rescatado
    private ArrayList<ExerciseData> currCycle;
    private int currentExercise; //rescatado
    private boolean finished; //rescatado
    private boolean played = false;

    private TextView title;
    private TextView timeExercise;

    private MainActivity mainActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRoutineExecutionExerciseBinding.inflate(getLayoutInflater());
        title = binding.routineNameTitleInExecutionExercise;
        timeExercise = binding.timeExercise;
        View view = binding.getRoot();

        binding.executionBar.play.setOnClickListener(v -> playExecution());
        binding.executionBar.pause.setOnClickListener(v -> pauseExecution());
        binding.executionBar.next.setOnClickListener(v -> nextExecution());
        binding.executionBar.previous.setOnClickListener(v -> previousExecution());

        binding.executionBar.pause.setOnClickListener(v -> pauseExecution());
        binding.executionBar.next.setOnClickListener(v -> nextExecution());
        binding.executionBar.previous.setOnClickListener(v -> previousExecution());

        mainActivity = (MainActivity) getActivity();
        mainActivity.setNavigationVisibility(false);

        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            title.setText(RoutineExecutionExerciseFragmentArgs.fromBundle(getArguments()).getTitle());

            viewModel = new ViewModelProvider(getActivity()).get(ExercisesViewModel.class);


            if (viewModel.getStarted()) {
                currentExercise = viewModel.getCurrentExercise();
                currentCycle = viewModel.getCurrentCycle();
                played = viewModel.getPlayed();
                cycleTitle = getCycleTitle();
            } else {
                viewModel.setCurrentExercise(0);
                viewModel.setCurrentCycle(0);
                cycleTitle = WARMUP_TITLE;
                currentCycle = 0;
                currentExercise = 0;
                viewModel.setStarted(true);
            }

            observeExerciseViewModel();
        }
    }

    private void observeExerciseViewModel() {
        viewModel.getWarmupExercises().observe(getViewLifecycleOwner(), warmupExercises -> {
            if (warmupExercises != null) {
                warmUp = (ArrayList<ExerciseData>) warmupExercises;
            }
        });

        viewModel.getMainExercises().observe(getViewLifecycleOwner(), mainExercises -> {
            if (mainExercises != null) {
                main = (ArrayList<ExerciseData>) mainExercises;
            }
        });

        viewModel.getCooldownExercises().observe(getViewLifecycleOwner(), cooldownExercises -> {
            if (cooldownExercises != null) {
                cooldown = (ArrayList<ExerciseData>) cooldownExercises;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void playExecution() {
        binding.executionBar.play.setVisibility(View.INVISIBLE);
        binding.executionBar.pause.setVisibility(View.VISIBLE);
        finished = false;

        if (played) {
            viewModel.getCountDownTimer().resume();
        } else {
            played=true;
            viewModel.getCountDownTimer().start((getNextExercise().getTime() + 1) * 1000, 1000);
            viewModel.getCountDownTimer().getStatus().observe(getViewLifecycleOwner(), countDown -> {
                if (!finished) {
                    if (countDown.isFinished()) {
                        ExerciseData exercise;
                        currentExercise++;
                        if ((exercise = getNextExercise()) != null)
                            viewModel.getCountDownTimer().start((exercise.getTime() + 1) * 1000, 1000);
                    } else {
                        System.out.println(countDown.getRemainingTime());
                        timeExercise.setText(String.valueOf(Math.ceil(countDown.getRemainingTime())));
                    }

                }
            });
        }

    }

    private void pauseExecution() {
        binding.executionBar.play.setVisibility(View.VISIBLE);
        binding.executionBar.pause.setVisibility(View.INVISIBLE);
        viewModel.getCountDownTimer().pause();
    }

    private void nextExecution() {
        viewModel.getCountDownTimer().stop();
        currentExercise++;
        ExerciseData exercise = getNextExercise();
        if(exercise != null){
            viewModel.getCountDownTimer().start((exercise.getTime() + 1) * 1000, 1000);
        }

    }

    private void previousExecution() {
        

    }

    private ExerciseData getNextExercise() {
        ExerciseData ex = null;
        currCycle = getCurrentCycle();
        if (currCycle != null) {
            System.out.println(currentExercise);
            ex = currCycle.get(currentExercise);
            binding.exerciseTitleInExecutionList.setText(ex.getName());
            binding.routineCycleTitleInExecutionExercise.setText(cycleTitle);
            binding.timeExercise.setText(String.valueOf(ex.getTime()));
            binding.ExerciseDescription.setText(ex.getDetail());
        }
        System.out.println(ex);
        return ex;
    }

    private ArrayList<ExerciseData> getCurrentCycle() {
        if (currentCycle == WARMUP_CYCLE) {
            if (currentExercise < warmUp.size()) {
                return warmUp;
            }
            cycleTitle = MAIN_TITLE;
            currentCycle++;
            currentExercise = 0;
        }

        if (currentCycle == MAIN_CYCLE) {
            if (currentExercise < main.size())
                return main;
            currentCycle++;
            currentExercise = 0;
            cycleTitle = COOLDOWN_TITLE;

        }

        if (currentCycle == COOLDOWN_CYCLE) {
            if (currentExercise < cooldown.size())
                return cooldown;
        }
        finished = true;
        return null;
    }

    private String getCycleTitle() {
        if (currentCycle == WARMUP_CYCLE) {
            return WARMUP_TITLE;
        } else if (currentCycle == MAIN_CYCLE) {
            return MAIN_TITLE;
        } else
            return COOLDOWN_TITLE;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.setStarted(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewModel.setCurrentCycle(currentCycle);
        viewModel.setCurrentExercise(currentExercise);
        viewModel.getCountDownTimer().stop();
    }

}
