package com.jennatauro.livefit.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jennatauro on 2/22/2014.
 */
public class Exercise implements Parcelable {
    private String exerciseName;

    private String exerciseDescription;

    private boolean isTimed;
    private int reps;
    private int seconds;

    public Exercise(String exerciseName, String exerciseDescription, boolean isTimed, int reps, int seconds) {
        this.exerciseName = exerciseName;
        this.exerciseDescription = exerciseDescription;
        this.isTimed = isTimed;
        this.reps = reps;
        this.seconds = seconds;
    }

    public Exercise (Parcel in) {
        String[] data = new String[5];

        in.readStringArray(data);
        this.exerciseName = data[0];
        this.exerciseDescription = data[1];
        this.isTimed = Boolean.valueOf(data[2]);
        this.reps = Integer.parseInt(data[3]);
        this.seconds = Integer.parseInt(data[4]);
    }

    public Exercise () { }

    public static final Parcelable.Creator<Exercise> CREATOR = new Parcelable.Creator<Exercise>() {
        public Exercise createFromParcel(Parcel in) {
            return new Exercise(in);
        }

        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };

    public String getExerciseName() {
        return exerciseName;
    }

    public String getExerciseDescription() {
        return exerciseDescription;
    }

    public boolean isTimed() {
        return isTimed;
    }

    public int getReps() {
        return reps;
    }

    public int getSeconds() {
        return seconds;
    }


    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{
                this.exerciseName,
                this.exerciseDescription,
                String.valueOf(this.isTimed),
                Integer.toString(this.reps),
                Integer.toString(this.seconds),
        });
    }

}
