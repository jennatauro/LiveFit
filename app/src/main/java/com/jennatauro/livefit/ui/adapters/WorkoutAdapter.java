package com.jennatauro.livefit.ui.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.jennatauro.livefit.LivefitApplication;
import com.jennatauro.livefit.R;
import com.jennatauro.livefit.data.models.Workout;
import com.jennatauro.livefit.ui.activities.WorkoutDetailsActivity;
import com.squareup.otto.Bus;

import javax.inject.Inject;

/**
 * Created by jennatauro on 2014-11-22.
 */
public class WorkoutAdapter extends RecyclerViewAdapter<Workout> {

    @Inject
    Bus bus;

    boolean mDoWorkout = false;


    public WorkoutAdapter(boolean doWorkout) {
        mDoWorkout = doWorkout;
    }

    @Override
    public WorkoutsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_workout, viewGroup, false);
        WorkoutsViewHolder viewHolder = new WorkoutsViewHolder(view, this);

        LivefitApplication app = LivefitApplication.getApplication();
        app.inject(this);

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
            workoutName = (TextView) view.findViewById(R.id.workout_name);
        }
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public void onItemClick(RecyclerViewAdapter.RecyclerViewBaseHolder viewHolder, View view){
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(null, viewHolder.itemView, viewHolder.getPosition(), viewHolder.getItemId());
        }
    }
}
