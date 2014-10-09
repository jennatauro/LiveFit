package com.jennatauro.livefit.data.models;

/**
 * Created by jennatauro on 2014-10-08.
 */
public abstract class LocalObject {

    private int mDbId;

    public LocalObject() { }

    protected LocalObject(LocalObject other) {
        this.mDbId = other.mDbId;
    }

    public int getDbId() {
        return mDbId;
    }

    public void setDbId(int dbId) {
        this.mDbId = dbId;
    }
}
