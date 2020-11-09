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

    public ExerciseAdapter(List<ExerciseOverviewInfo> exerciseList){
        this.exerciseList = exerciseList;
    }


    // Create new views (invoked by the layout manager)
    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_item, parent, false);
        return new ExerciseViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        ExerciseOverviewInfo ex  = exerciseList.get(position);
        TextView title = holder.itemView.findViewById(R.id.exerciseName);
        TextView quantity = holder.itemView.findViewById(R.id.quantityExercise);
        title.setText(ex.getExerciseName());
        quantity.setText(ex.getQuantity());

    }


    // total number of rows
    @Override
    public int getItemCount() {
        return exerciseList.size();
    }


    // stores and recycles views as they are scrolled off screen
    public static class ExerciseViewHolder extends RecyclerView.ViewHolder{
        public View itemView;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }




}