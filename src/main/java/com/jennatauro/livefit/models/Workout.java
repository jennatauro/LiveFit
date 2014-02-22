package com.jennatauro.livefit.models;

import java.util.List;

/**
 * Created by jennatauro on 2/22/2014.
 */
public class Workout {
    private String workoutname;
    private List<Exercise> exercises;

    public Workout(String workoutname){
        this.workoutname = workoutname;
    }

    public String getWorkoutname() {
        return workoutname;
    }
}
