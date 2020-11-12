package com.example.fitnesshub.view.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnesshub.R;
import com.example.fitnesshub.databinding.RoutineCardBinding;
import com.example.fitnesshub.view.fragments.RoutineClickListener;
import com.example.fitnesshub.model.RoutineData;


import java.util.List;

public class RoutinesAdapter extends RecyclerView.Adapter<RoutinesAdapter.RoutineViewHolder>{
    private List<RoutineData> routinesList;

    public RoutinesAdapter(List<RoutineData> routinesList) {
        this.routinesList = routinesList;
    }

    @NonNull
    @Override
    public RoutineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RoutineCardBinding view = DataBindingUtil.inflate(inflater, R.layout.routine_card, parent, false);
        return new RoutineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoutineViewHolder holder, int position) {
        holder.itemView.setRoutineData(routinesList.get(position));
        holder.itemView.setClickListener(new RoutineClickListener(routinesList.get(position),RoutineClickListener.ROUTINES_ID));
    }

    @Override
    public int getItemCount() {
        return routinesList.size();
    }

    public void updateRoutines(List<RoutineData> routineCards) {
        routinesList.addAll(routineCards);
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
