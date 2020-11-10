package com.example.fitnesshub.view;

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
import com.example.fitnesshub.model.ExerciseOverviewInfo;
import com.example.fitnesshub.model.RoutineOverviewInfo;

import java.util.List;

public class FavoriteAdapter  extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> implements RoutineEntryClickListener{
    private List<RoutineOverviewInfo> favoriteList;

    public FavoriteAdapter(List<RoutineOverviewInfo> favoriteList) {
        this.favoriteList = favoriteList;
    }

    public void updateFavoriteList(List<RoutineOverviewInfo> newRoutinesList) {
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
        holder.itemView.setRoutineEntry(favoriteList.get(position));
        holder.itemView.setClickListener(this);
    }

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }

    @Override
    public void onRoutineClick(View view) {
        NavDirections action = RoutinesFragmentDirections.actionRoutinesFragmentToMeFragment();
        Navigation.findNavController(view).navigate(action);
    }

    public static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        public RoutineCardBinding itemView;

        public FavoriteViewHolder(@NonNull RoutineCardBinding itemView) {
            super(itemView.getRoot());
            this.itemView = itemView;
        }


    }
}
