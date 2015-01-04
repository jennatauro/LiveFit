package com.jennatauro.livefit.data.models;

/**
 * Created by jennatauro on 2015-01-04.
 */
public class WorkoutDayRelation extends LocalObject{

    private int mWorkoutId;
    private int mDayId;

    public int getmDayId() {
        return mDayId;
    }

    public void setmDayId(int mDayId) {
        this.mDayId = mDayId;
    }


    public int getmWorkoutId() {
        return mWorkoutId;
    }

    public void setmWorkoutId(int mWorkoutId) {
        this.mWorkoutId = mWorkoutId;
    }

}
