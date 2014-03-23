package com.jennatauro.livefit.adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jennatauro.livefit.R;
import com.jennatauro.livefit.models.Workout;
import com.jennatauro.livefit.util.WorkoutDataSource;

import java.util.List;

/**
 * Created by jennatauro on 2/22/2014.
 */
public class WorkoutsAdapter extends ArrayAdapter<Workout> implements View.OnClickListener{

    List<Workout> workouts;
    WorkoutDataSource dataSource;

    public WorkoutsAdapter(Context context, int resource, List<Workout> workouts, WorkoutDataSource dataSource) {
        super(context, resource);
        this.workouts = workouts;
        this.dataSource = dataSource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            final LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item_workouts, null);
        }

        Workout workout = getItem(position);

        setRowView(position, convertView);

        ImageView deleteButton = (ImageView) convertView.findViewById(R.id.iv_delete_workout_item);
        deleteButton.setOnClickListener(this);
        deleteButton.setTag(workout);

        return convertView;
    }

    private void setRowView(final int position, final View convertView) {
        Workout item = getItem(position);
        String workoutName = item.getWorkoutname();
        ((TextView) convertView.findViewById(R.id.tv_workout_name)).setText(workoutName);
    }

    @Override
    public Workout getItem(int position) {
        return workouts.get(position);
    }

    @Override
    public int getCount() {
        return workouts.size();
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.iv_delete_workout_item:
                final Workout workout = (Workout) view.getTag();
                dataSource.deleteWorkout(workout);
                workouts.clear();
                workouts = dataSource.getAllWorkouts();
                notifyDataSetChanged();
                Log.d("adaptersize", this.getCount() + "");
            break;
        }
    }
}
