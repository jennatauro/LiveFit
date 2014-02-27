package com.jennatauro.livefit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jennatauro.livefit.R;
import com.jennatauro.livefit.models.Exercise;
import com.jennatauro.livefit.models.Workout;

import java.util.List;

/**
 * Created by jennatauro on 2/22/2014.
 */
public class ExercisesAdapter extends ArrayAdapter<Exercise> {

    List<Exercise> exercises;

    public ExercisesAdapter(Context context, int resource, List<Exercise> exercises) {
        super(context, resource);
        this.exercises = exercises;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            final LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item_exercises, null);
        }

        setRowView(position, convertView);

        return convertView;
    }

    private void setRowView(final int position, final View convertView) {
        Exercise item = getItem(position);
        ((TextView) convertView.findViewById(R.id.tv_exercise_name)).setText(item.getExerciseName());
        ((TextView) convertView.findViewById(R.id.tv_exercise_description)).setText(item.getExerciseDescription());
        if(item.isTimed()){
            ((TextView) convertView.findViewById(R.id.tv_reps_or_seconds)).setText(item.getSeconds() + " seconds");
        }
        else{
            ((TextView) convertView.findViewById(R.id.tv_reps_or_seconds)).setText(item.getReps() + " reps");
        }
    }

    @Override
    public Exercise getItem(int position) {
        return exercises.get(position);
    }

    @Override
    public int getCount() {
        return exercises.size();
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
}
