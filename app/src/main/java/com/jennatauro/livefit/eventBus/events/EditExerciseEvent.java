package com.jennatauro.livefit.eventBus.events;

import com.jennatauro.livefit.data.models.Exercise;

/**
 * Created by jennatauro on 2014-11-23.
 */
public class EditExerciseEvent {
    private Exercise exerciseToEdit;
    private int index;

    public EditExerciseEvent(Exercise exercise, int index){
        exerciseToEdit = exercise;
        this.index = index;
    }

    public Exercise getExerciseToEdit(){
        return exerciseToEdit;
    }

    public int getIndex() { return index; }
}
