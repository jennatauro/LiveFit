package com.jennatauro.livefit.data.models;

/**
 * Created by jennatauro on 2014-10-09.
 */
public class Exercise extends LocalObject {

    private String mTitle;
    private String mDescription;


    private int mWeight;
    private int mReps;
    private int mSeconds;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public int getWeight() {
        return mWeight;
    }

    public void setWeight(int mWeight) {
        this.mWeight = mWeight;
    }

    public int getReps() {
        return mReps;
    }

    public void setReps(int mReps) {
        this.mReps = mReps;
    }

    public int getSeconds() {
        return mSeconds;
    }

    public void setSeconds(int mSeconds) {
        this.mSeconds = mSeconds;
    }
}
