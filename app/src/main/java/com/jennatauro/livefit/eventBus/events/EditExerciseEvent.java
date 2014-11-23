package com.jennatauro.livefit.eventBus.events;

import com.jennatauro.livefit.data.models.Exercise;

/**
 * Created by jennatauro on 2014-11-23.
 */
public class EditExerciseEvent {
    private Exercise exerciseToEdit;

    public EditExerciseEvent(Exercise exercise){
        exerciseToEdit = exercise;
    }

    public Exercise getExerciseToEdit(){
        return exerciseToEdit;
    }
}
