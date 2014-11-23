package com.jennatauro.livefit.eventBus.events;

import com.jennatauro.livefit.data.models.Exercise;

/**
 * Created by jennatauro on 2014-11-23.
 */
public class SeeExerciseEvent {
    private Exercise exerciseToSee;

    public SeeExerciseEvent(Exercise exercise){
        exerciseToSee = exercise;
    }

    public Exercise getExerciseToSee(){
        return exerciseToSee;
    }
}
