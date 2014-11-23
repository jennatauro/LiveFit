package com.jennatauro.livefit.eventBus.events;

/**
 * Created by jennatauro on 2014-11-23.
 */
public class WorkoutClickedEvent {
    private int workoutPosition;

    public WorkoutClickedEvent(int workoutId) {
        this.workoutPosition = workoutId;
    }

    public int getWorkoutPosition(){
        return workoutPosition;
    }
}
