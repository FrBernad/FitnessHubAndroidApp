package com.example.fitnesshub.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnesshub.R;
import com.example.fitnesshub.model.ExerciseOverviewInfo;

import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {

    private List<ExerciseOverviewInfo> exerciseList;

    public ExerciseAdapter(List<ExerciseOverviewInfo> exerciseList) {
        this.exerciseList = exerciseList;
    }

    public void updateExercises(List<ExerciseOverviewInfo> newExercisesList) {
        exerciseList.clear();
        exerciseList.addAll(newExercisesList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_item, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        ExerciseOverviewInfo ex = exerciseList.get(position);
        TextView title = holder.itemView.findViewById(R.id.exerciseName);
        TextView quantity = holder.itemView.findViewById(R.id.quantityExercise);
        title.setText(ex.getExerciseName());
        quantity.setText(String.format("%d",ex.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        public View itemView;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }
}