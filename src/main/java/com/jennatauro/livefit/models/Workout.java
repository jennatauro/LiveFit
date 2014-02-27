package com.jennatauro.livefit.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jennatauro on 2/22/2014.
 */
public class Workout {

    private String workoutname;

    private List<Exercise> exercises;

    public Workout(String workoutname, List<Exercise> exercises){
        this.workoutname = workoutname;
        this.exercises = exercises;
    }

    public String getWorkoutname() {
        return workoutname;
    }
    public List<Exercise> getExercises() {
        return exercises;
    }
}
