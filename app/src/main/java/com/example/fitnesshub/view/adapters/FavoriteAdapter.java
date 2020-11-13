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

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {
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
        RoutineData routine = favoriteList.get(position);

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

        holder.itemView.setRoutineData(favoriteList.get(position));
        holder.itemView.setClickListener(new RoutineClickListener(favoriteList.get(position), RoutineClickListener.FAV_ID));
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
