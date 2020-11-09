package com.example.fitnesshub.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnesshub.R;
import com.example.fitnesshub.model.RoutineCardInfo;

import java.util.List;

public class RoutinesAdapter extends RecyclerView.Adapter<RoutinesAdapter.RoutineViewHolder> {

    private List<RoutineCardInfo> routinesList;

    public RoutinesAdapter(List<RoutineCardInfo> routinesList) {
        this.routinesList = routinesList;
    }

    public void updateRoutinesList(List<RoutineCardInfo> newRoutinesList){
        routinesList.clear();
        routinesList.addAll(newRoutinesList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RoutineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.routine_card,parent,false);
        return new RoutineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoutineViewHolder holder, int position) {
        ImageView img = holder.itemView.findViewById(R.id.routineImg);
        TextView title =  holder.itemView.findViewById(R.id.routineTitle);
        TextView author =  holder.itemView.findViewById(R.id.routineAuthor);
        RatingBar rating =  holder.itemView.findViewById(R.id.routineRatingBar);
//      TextView fav =  holder.itemView.findViewById(R.id.routineFav);

        title.setText(routinesList.get(position).getTitle());
        author.setText(routinesList.get(position).getAuthor());
        rating.setNumStars(routinesList.get(position).getRating());
//      fav.set(routinesList.get(position).getAuthor());
    }

    @Override
    public int getItemCount() {
        return routinesList.size();
    }

    static class RoutineViewHolder extends RecyclerView.ViewHolder{
        public View itemView;

        public RoutineViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }

}
