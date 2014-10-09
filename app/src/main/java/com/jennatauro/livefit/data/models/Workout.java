package com.jennatauro.livefit.data.models;

/**
 * Created by jennatauro on 2014-10-08.
 */
public class Workout extends LocalObject {

    private String mTitle;


    private String mDescription;

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
}
