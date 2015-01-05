package com.jennatauro.livefit.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jennatauro.livefit.LivefitApplication;
import com.jennatauro.livefit.R;
import com.jennatauro.livefit.data.models.Exercise;
import com.jennatauro.livefit.eventBus.events.EditExerciseEvent;
import com.jennatauro.livefit.eventBus.events.ExerciseDeletedEvent;
import com.jennatauro.livefit.eventBus.events.SeeExerciseEvent;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import butterknife.ButterKnife;
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

        ButterKnife.inject(this, view);

        LivefitApplication app = LivefitApplication.getApplication();
        app.inject(this);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewBaseHolder recyclerViewBaseHolder, int position) {
        if (recyclerViewBaseHolder instanceof ExercisesViewHolder) {
            ExercisesViewHolder exercisesViewHolder = (ExercisesViewHolder) recyclerViewBaseHolder;
            Exercise exercise = items.get(position);
            exercisesViewHolder.exerciseName.setText(exercise.getTitle());
            exercisesViewHolder.seeExercise.setTag(position);
        }
    }

    @OnClick(R.id.see_exercise)
    void seeExercise(View v) {
        Integer index = (Integer) v.getTag();
        Exercise exercise = items.get(index);
        bus.post(new SeeExerciseEvent(exercise));
    }

    @Override
    public void onItemClick(RecyclerViewBaseHolder viewHolder, View clickedView) {}

    public static class ExercisesViewHolder extends RecyclerViewBaseHolder {
        TextView exerciseName;
        View seeExercise;

        public ExercisesViewHolder(View view, ExerciseAdapter adapter) {
            super(view, adapter);
            exerciseName = (TextView) view.findViewById(R.id.exercise_name);
            seeExercise = view.findViewById(R.id.see_exercise);
        }
    }

}
