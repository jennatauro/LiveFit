package com.jennatauro.livefit.eventBus.events;

import com.jennatauro.livefit.data.models.Exercise;

/**
 * Created by jennatauro on 2014-11-22.
 */
public class ExerciseDeletedEvent {
    private Exercise deletedExercise;

    public ExerciseDeletedEvent(Exercise exercise){
        deletedExercise = exercise;
    }

    public Exercise getDeletedExercise(){
        return deletedExercise;
    }
}
