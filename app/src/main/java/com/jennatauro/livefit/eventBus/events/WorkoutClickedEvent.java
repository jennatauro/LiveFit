package com.jennatauro.livefit.eventBus.events;

/**
 * Created by jennatauro on 2014-11-23.
 */
public class WorkoutClickedEvent {
    private int workoutPosition;
    private boolean doWorkout;

    public WorkoutClickedEvent(int workoutId, boolean doWorkout) {
        this.workoutPosition = workoutId;
        this.doWorkout = doWorkout;
    }

    public int getWorkoutPosition(){
        return workoutPosition;
    }

    public boolean getDoWorkout() { return doWorkout; }
}
