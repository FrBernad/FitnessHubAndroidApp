package com.example.fitnesshub.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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
    private static final int PLAYING = 0;
    private static final int PAUSED = 1;
    private static final int NOTRUNNING = 2;
    private static final int REPS_TIME= 10;
    private static final String WARMUP_TITLE = "WARM UP";
    private static final String MAIN_TITLE = "MAIN EXERCISES";
    private static final String COOLDOWN_TITLE = "COOLDOWN";
    private String[] titles = new String[3];
    private ArrayList<ArrayList<ExerciseData>> cycles = new ArrayList<>();

    private int currentCycle;
    private String cycleTitle;
    private ArrayList<ExerciseData> currCycleIndex;
    private int currentExerciseIndex;
    private boolean finished;
    private ExerciseData currentExercise;
    private boolean executed;
    private int status;

    private TextView title;
    private TextView timeExercise;
    private ProgressBar progressBar;

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
        binding.executionBar.previous.setVisibility(View.GONE);
        binding.executionBar.next.setVisibility(View.GONE);
        titles[0]=WARMUP_TITLE;
        titles[1]=MAIN_TITLE;
        titles[2]=COOLDOWN_TITLE;

        progressBar = binding.progressBar;

        mainActivity = (MainActivity) getActivity();
        mainActivity.setNavigationVisibility(false);

        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            title.setText(RoutineExecutionExerciseFragmentArgs.fromBundle(getArguments()).getTitle());

            viewModel = new ViewModelProvider(getActivity()).get(ExercisesViewModel.class);

            if (!viewModel.getIsFirstTime()) {
                currentExerciseIndex = viewModel.getCurrentExercise();
                currentCycle = viewModel.getCurrentCycle();
                executed = viewModel.getExecuted();
                cycleTitle = titles[currentCycle];
                status = viewModel.getStatus();
            } else {
                currentCycle = 0;
                cycleTitle = titles[currentCycle];
                currentExerciseIndex = 0;
                viewModel.setStarted(true);
            }

            observeExerciseViewModel();
        }
    }

    private void observeExerciseViewModel() {
        viewModel.getWarmupExercises().observe(getViewLifecycleOwner(), warmupExercises -> {
            if (warmupExercises != null) {
                warmUp = (ArrayList<ExerciseData>) warmupExercises;
                cycles.add(WARMUP_CYCLE,warmUp);

            }
        });

        viewModel.getMainExercises().observe(getViewLifecycleOwner(), mainExercises -> {
            if (mainExercises != null) {
                main = (ArrayList<ExerciseData>) mainExercises;
                cycles.add(MAIN_CYCLE,main);

            }

        });

        viewModel.getCooldownExercises().observe(getViewLifecycleOwner(), cooldownExercises -> {
            if (cooldownExercises != null) {
                cooldown = (ArrayList<ExerciseData>) cooldownExercises;
                cycles.add(COOLDOWN_CYCLE,cooldown);

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
        status = PLAYING;

        if (executed) {
            viewModel.getCountDownTimer().resume();
        } else {
            binding.executionBar.previous.setVisibility(View.VISIBLE);
            binding.executionBar.next.setVisibility(View.VISIBLE);
            executed = true;
            currentExercise = getNextExercise();
            int r = currentExercise.getReps() > 0 ? currentExercise.getReps() * REPS_TIME : 0;
            progressBar.setMax(currentExercise.getTime()+r);
            viewModel.getCountDownTimer().start((currentExercise.getTime() + r + 1) * 1000, 1000);
            viewModel.getCountDownTimer().getStatus().observe(getViewLifecycleOwner(), countDown -> {
                if (!finished) {
                    if (countDown.isFinished()) {
                        currentExerciseIndex++;
                        if ((currentExercise = getNextExercise()) != null) {
                            int a = currentExercise.getReps() > 0 ? currentExercise.getReps() * REPS_TIME : 0;

                            viewModel.getCountDownTimer().start((currentExercise.getTime() + a + 1) * 1000, 1000);
                            progressBar.setProgress(0);
                            progressBar.setMax(currentExercise.getTime());
                        }
                    } else {
                        long remainingTime = countDown.getRemainingTime();
                        progressBar.setProgress(progressBar.getMax() - (int) remainingTime);
                        timeExercise.setText(String.valueOf(remainingTime));
                    }
                }else{
                    //terminaste la rutina
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
        currentExerciseIndex++;
        currentExercise = getNextExercise();
        if (currentExercise != null) {
            progressBar.setProgress(0);
            int a = currentExercise.getReps() > 0 ? currentExercise.getReps() * REPS_TIME : 0;
            progressBar.setMax(currentExercise.getTime()+a);
            viewModel.getCountDownTimer().start((currentExercise.getTime() + a + 1) * 1000, 1000);
            if(status == PAUSED)
                viewModel.getCountDownTimer().pause();
        }else{
            //terminaste la rutina
        }
    }

    private void previousExecution() {
        viewModel.getCountDownTimer().stop();
        currentExerciseIndex--;
        currentExercise = getPrevExercise();
        if (currentExercise != null) {
            progressBar.setProgress(0);
            int a = currentExercise.getReps() > 0 ? currentExercise.getReps() * REPS_TIME : 0;

            progressBar.setMax(currentExercise.getTime()+a  );
            viewModel.getCountDownTimer().start((currentExercise.getTime() + a + 1) * 1000, 1000);
            if(status == PAUSED){
                viewModel.getCountDownTimer().pause();
            }
        }
    }

    private ExerciseData getPrevExercise() {
        ExerciseData ex;
        currCycleIndex = getPrevCycle();
        if (currCycleIndex == null) {
            currentExerciseIndex = 0;
            currCycleIndex = cycles.get(currentCycle);
        }
        ex = currCycleIndex.get(currentExerciseIndex);
        binding.exerciseTitleInExecutionList.setText(ex.getName());
        binding.routineCycleTitleInExecutionExercise.setText(cycleTitle);
        binding.timeExercise.setText(String.valueOf(ex.getTime()));
        binding.ExerciseDescription.setText(ex.getDetail());
        return ex;
    }


    private ArrayList<ExerciseData> getPrevCycle() {

        if(currentExerciseIndex == -1){
            if(currentCycle == WARMUP_CYCLE){
                return null;
            }
            currentCycle--;
            cycleTitle = titles[currentCycle];
            currentExerciseIndex = cycles.get(currentCycle).size()-1;
        }
        return cycles.get(currentCycle);

    }

    private ExerciseData getNextExercise() {
        ExerciseData ex = null;
        currCycleIndex = getCurrentCycle();
        if (currCycleIndex != null) {
            ex = currCycleIndex.get(currentExerciseIndex);
            binding.exerciseTitleInExecutionList.setText(ex.getName());
            binding.routineCycleTitleInExecutionExercise.setText(cycleTitle);
            binding.timeExercise.setText(String.valueOf(ex.getTime()));
            binding.ExerciseDescription.setText(ex.getDetail());
        }
        return ex;
    }

    private ArrayList<ExerciseData> getCurrentCycle() {

        if(currentExerciseIndex >= cycles.get(0).size()){
            if(currentCycle == COOLDOWN_CYCLE){
                finished = true;
                return null;
            }

            currentCycle ++;
            cycleTitle = titles[currentCycle];
            currentExerciseIndex = 0;
        }
        return cycles.get(currentCycle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewModel.setCurrentCycle(currentCycle);
        viewModel.setCurrentExercise(currentExerciseIndex);
        viewModel.setStatus(status);
        viewModel.setExecuted(executed);
        if(executed){
            viewModel.getCountDownTimer().pause();
        }
    }

}
