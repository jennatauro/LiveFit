package com.jennatauro.livefit.eventBus.events;

/**
 * Created by jennatauro on 2014-11-23.
 */
public class WorkoutClickedEvent {
    private int workoutId;

    public WorkoutClickedEvent(int workoutId) {
        this.workoutId = workoutId;
    }

    public int getWorkoutId(){
        return workoutId;
    }
}
