package com.jennatauro.livefit.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jennatauro.livefit.R;
import com.jennatauro.livefit.data.models.Workout;

/**
 * Created by jennatauro on 2014-11-22.
 */
public class WorkoutAdapter extends RecyclerViewAdapter<Workout> {
    @Override
    public WorkoutsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.workout_list_item, viewGroup, false);
        WorkoutsViewHolder viewHolder = new WorkoutsViewHolder(view, this);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewBaseHolder recyclerViewBaseHolder, int position) {
        if (recyclerViewBaseHolder instanceof WorkoutsViewHolder) {
            WorkoutsViewHolder workoutsViewHolder = (WorkoutsViewHolder) recyclerViewBaseHolder;
            Workout workout = items.get(position);
            workoutsViewHolder.workoutName.setText(workout.getTitle());
        }
    }

    public static class WorkoutsViewHolder extends RecyclerViewBaseHolder {
        TextView workoutName;

        public WorkoutsViewHolder(View view, WorkoutAdapter adapter) {
            super(view, adapter);
        }
    }

}
