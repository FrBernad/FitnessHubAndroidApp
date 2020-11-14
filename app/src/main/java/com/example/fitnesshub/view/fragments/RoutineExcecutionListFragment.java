package com.example.fitnesshub.view.fragments;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fitnesshub.R;
import com.example.fitnesshub.databinding.FragmentRoutineExecutionListBinding;
import com.example.fitnesshub.model.ExerciseData;
import com.example.fitnesshub.view.adapters.ExercisesAdapter;
import com.example.fitnesshub.viewModel.ExercisesViewModel;

import java.util.ArrayList;
import java.util.List;

public class RoutineExcecutionListFragment extends Fragment {

    private FragmentRoutineExecutionListBinding binding;

    private ExercisesViewModel viewModel;

    private ExercisesAdapter warmUpAdapter = new ExercisesAdapter(new ArrayList<>());
    private ExercisesAdapter mainAdapter = new ExercisesAdapter(new ArrayList<>());
    private ExercisesAdapter cooldownAdapter = new ExercisesAdapter(new ArrayList<>());

    private RecyclerView recyclerViewWarmUp;
    private RecyclerView recyclerViewMain;
    private RecyclerView recyclerViewCooldown;

    private TextView title;


    private CountDownTimer countDownTimer;
    private long millisecondsLeft;
    private boolean timerRunning;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        binding = FragmentRoutineExecutionListBinding.inflate(getLayoutInflater());

        recyclerViewWarmUp = binding.warmUpExercises;
        recyclerViewMain = binding.mainExercises;
        recyclerViewCooldown = binding.cooldownExercises;

        title = binding.routineNameTitleInExecutionList;

        View view = binding.getRoot();

        getActivity().findViewById(R.id.bottomNav).setVisibility(View.GONE);

        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if (getArguments() != null) {
            title.setText(RoutineExcecutionListFragmentArgs.fromBundle(getArguments()).getRoutineTitle());
        }

        viewModel = new ViewModelProvider(getActivity()).get(ExercisesViewModel.class);

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
    public void onResume() {
        super.onResume();

        ArrayList<ExecutionListThread>  threads = new ArrayList<>();
        List<ExerciseData> warmUp = warmUpAdapter.getExerciseList();
        List<ExerciseData> main = mainAdapter.getExerciseList();
        List<ExerciseData> cooldown = cooldownAdapter.getExerciseList();
        ExecutionListThread t;
        int i=0;
        for (int j = 0; j < warmUp.size() ;j++, i++) {
            t = new ExecutionListThread();
            t.setAdapter(warmUpAdapter);
            t.setCurrent(i,warmUp.get(j));
            threads.add(i,new ExecutionListThread());
            t.execute(10000);
        }
        for(int j=0;j<main.size();j++,i++){
            t = new ExecutionListThread();
            t.setAdapter(mainAdapter);
            t.setCurrent(i,main.get(j));
            threads.add(i,new ExecutionListThread());
            t.execute(10000);
        }
        for(int j=0;j<cooldown.size();j++,i++){
            t = new ExecutionListThread();
            t.setAdapter(cooldownAdapter);
            t.setCurrent(i,cooldown.get(j));
            threads.add(i,new ExecutionListThread());
            t.execute(10000);
        }

        for (int j = 0 ; j < i ; j++){
            threads.get(i).join();
        }
    }


    public void runCycles(ExecutionListThread thread, ExercisesAdapter adapter){
        ArrayList<ExerciseData> exercises;
        ExerciseData ex;
        thread.setAdapter(adapter);
        int curr = 0;
        int cycleSize = adapter.getItemCount();
        exercises = (ArrayList<ExerciseData>) adapter.getExerciseList();

        for ()


        while( curr < cycleSize ){
            ex = exercises.get(curr);
            thread.setCurrent(curr,ex);
            thread.execute(10000);
            curr++;
        }

    }



    public static class ExecutionListThread extends AsyncTask<Integer, Void, Void> {

        private ExerciseData exercise;
        private int curr;
        private ExercisesAdapter adapter;

       public void setCurrent(int curr,ExerciseData exercise) {
            this.curr = curr;
            this.exercise = exercise;
        }

        public void setAdapter(ExercisesAdapter adapter){
            this.adapter = adapter;
        }


        @Override
        protected Void doInBackground(Integer... integers) {
            try {
                Thread.sleep(integers[0]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        synchronized protected void onPreExecute() {
           super.onPreExecute();
            exercise.setRunning(true);
            adapter.notifyItemChanged(curr);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            exercise.setRunning(false);
            adapter.notifyItemChanged(curr);
        }

    }

}
