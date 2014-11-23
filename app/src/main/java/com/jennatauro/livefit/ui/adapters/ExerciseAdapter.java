package com.jennatauro.livefit.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jennatauro.livefit.R;
import com.jennatauro.livefit.data.models.Exercise;
import com.jennatauro.livefit.eventBus.events.ExerciseDeletedEvent;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import butterknife.OnClick;

/**
 * Created by jennatauro on 2014-11-22.
 */
public class ExerciseAdapter extends RecyclerViewAdapter<Exercise> {

    @Inject
    Bus bus;

    @Override
    public ExercisesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_exercise, viewGroup, false);
        ExercisesViewHolder viewHolder = new ExercisesViewHolder(view, this);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewBaseHolder recyclerViewBaseHolder, int position) {
        if (recyclerViewBaseHolder instanceof ExercisesViewHolder) {
            ExercisesViewHolder exercisesViewHolder = (ExercisesViewHolder) recyclerViewBaseHolder;
            Exercise exercise = items.get(position);
            exercisesViewHolder.exerciseName.setText(exercise.getTitle());
        }
    }

    @OnClick(R.id.delete_exercise)
    void deleteExercise(View v) {
        Exercise exercise = (Exercise) v.getTag();
        bus.post(new ExerciseDeletedEvent(exercise));
    }

    public static class ExercisesViewHolder extends RecyclerViewBaseHolder {
        TextView exerciseName;

        public ExercisesViewHolder(View view, ExerciseAdapter adapter) {
            super(view, adapter);
            exerciseName = (TextView) view.findViewById(R.id.exercise_name);

            view.findViewById(R.id.delete_exercise).setOnClickListener(this);
        }
    }

}
