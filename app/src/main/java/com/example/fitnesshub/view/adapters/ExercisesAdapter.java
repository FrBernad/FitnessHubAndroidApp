package com.example.fitnesshub.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnesshub.R;
import com.example.fitnesshub.databinding.ExerciseItemBinding;
import com.example.fitnesshub.model.ExerciseData;
import com.example.fitnesshub.view.fragments.ShowExerciseDialog;

import java.util.List;

public class ExercisesAdapter extends RecyclerView.Adapter<ExercisesAdapter.ExerciseViewHolder> {

    private List<ExerciseData> exerciseList;
    ExerciseItemBinding binding;

    private Context parentContext;

    private ImageView infoButton;

    public ExercisesAdapter(List<ExerciseData> exerciseList) {
        this.exerciseList = exerciseList;
    }

    public void updateExercises(List<ExerciseData> newExercisesList) {
        exerciseList.clear();
        exerciseList.addAll(newExercisesList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.parentContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parentContext);
        binding = DataBindingUtil.inflate(inflater, R.layout.exercise_item, parent, false);
        infoButton = binding.infoButton;
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openExerciseInfoDialog();
            }
        });
        return new ExerciseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        ExerciseData exercise = exerciseList.get(position);
        holder.itemView.setExerciseData(exercise);
        binding.repsExercise.setVisibility(exercise.getReps()!=0 ? View.VISIBLE : View.GONE);
        binding.timeExercise.setVisibility(exercise.getTime()!=0 ? View.VISIBLE : View.GONE);

    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        public ExerciseItemBinding itemView;

        public ExerciseViewHolder(@NonNull ExerciseItemBinding itemView) {
            super(itemView.getRoot());
            this.itemView = itemView;
        }
    }

    public void openExerciseInfoDialog() {
        ShowExerciseDialog showExerciseDialog = new ShowExerciseDialog();
        showExerciseDialog.show(((AppCompatActivity)parentContext).getSupportFragmentManager(), "example dialog");
    }
}