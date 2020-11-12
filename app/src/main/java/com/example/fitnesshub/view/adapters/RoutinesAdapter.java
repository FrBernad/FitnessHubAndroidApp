package com.example.fitnesshub.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnesshub.R;
import com.example.fitnesshub.databinding.RoutineCardBinding;
import com.example.fitnesshub.view.fragments.RoutineClickListener;
import com.example.fitnesshub.model.RoutineData;


import java.util.List;

public class RoutinesAdapter extends RecyclerView.Adapter<RoutinesAdapter.RoutineViewHolder> {
    private List<RoutineData> routinesList;
    private View view;

    public RoutinesAdapter(List<RoutineData> routinesList) {
        this.routinesList = routinesList;
    }

    @NonNull
    @Override
    public RoutineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RoutineCardBinding binding = DataBindingUtil.inflate(inflater, R.layout.routine_card, parent, false);
        view = binding.getRoot();
        return new RoutineViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RoutineViewHolder holder, int position) {
        RoutineData routine = routinesList.get(position);
        int id = routine.getCategory().getId();
        switch (id) {
            case 1:
                holder.itemView.routineImg.setImageResource(R.drawable.p1);
                routine.setImage(String.valueOf(R.drawable.p1));
                break;

            case 2:
                holder.itemView.routineImg.setImageResource(R.drawable.p2);
                routine.setImage(String.valueOf(R.drawable.p2));
                break;

            case 3:
                holder.itemView.routineImg.setImageResource(R.drawable.p3);
                routine.setImage(String.valueOf(R.drawable.p3));
                break;

            case 4:
                holder.itemView.routineImg.setImageResource(R.drawable.p4);
                routine.setImage(String.valueOf(R.drawable.p4));
                break;

            case 5:
                holder.itemView.routineImg.setImageResource(R.drawable.p5);
                routine.setImage(String.valueOf(R.drawable.p5));
                break;

            case 6:
                holder.itemView.routineImg.setImageResource(R.drawable.p6);
                routine.setImage(String.valueOf(R.drawable.p6));
                break;

            case 7:
                holder.itemView.routineImg.setImageResource(R.drawable.p7);
                routine.setImage(String.valueOf(R.drawable.p7));
                break;
        }

        holder.itemView.setRoutineData(routine);
        holder.itemView.setClickListener(new RoutineClickListener(routine, RoutineClickListener.ROUTINES_ID));
    }

    @Override
    public int getItemCount() {
        return routinesList.size();
    }

    public void updateRoutines(List<RoutineData> routineCards) {
        routinesList.addAll(routineCards);
        notifyDataSetChanged();
    }

    public void resetRoutines() {
        routinesList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class RoutineViewHolder extends RecyclerView.ViewHolder {
        public RoutineCardBinding itemView;

        public RoutineViewHolder(@NonNull RoutineCardBinding itemView) {
            super(itemView.getRoot());
            this.itemView = itemView;
        }
    }
}
