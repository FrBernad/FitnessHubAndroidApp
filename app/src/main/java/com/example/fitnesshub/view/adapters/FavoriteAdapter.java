package com.example.fitnesshub.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnesshub.R;
import com.example.fitnesshub.databinding.RoutineCardBinding;
import com.example.fitnesshub.model.RoutineClickListener;
import com.example.fitnesshub.model.RoutineData;
import com.example.fitnesshub.view.fragments.RoutinesFragmentDirections;

import java.util.List;

public class FavoriteAdapter  extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>{
    private List<RoutineData> favoriteList;

    public FavoriteAdapter(List<RoutineData> favoriteList) {
        this.favoriteList = favoriteList;
    }

    public void updateFavoriteList(List<RoutineData> newRoutinesList) {
        favoriteList.clear();
        favoriteList.addAll(newRoutinesList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteAdapter.FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RoutineCardBinding view = DataBindingUtil.inflate(inflater, R.layout.routine_card, parent, false);
        return new FavoriteAdapter.FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        holder.itemView.setRoutineData(favoriteList.get(position));
        holder.itemView.setClickListener(new RoutineClickListener(favoriteList.get(position)));
    }

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }

    public static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        public RoutineCardBinding itemView;

        public FavoriteViewHolder(@NonNull RoutineCardBinding itemView) {
            super(itemView.getRoot());
            this.itemView = itemView;
        }


    }
}
