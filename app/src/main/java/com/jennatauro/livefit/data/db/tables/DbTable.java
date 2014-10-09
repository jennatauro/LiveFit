package com.jennatauro.livefit.data.db.tables;

import com.j256.ormlite.field.DatabaseField;
import com.jennatauro.livefit.data.models.LocalObject;

/**
 * Created by jennatauro on 2014-10-08.
 */
public abstract class DbTable<T extends LocalObject> {
    public static final String ID_FIELD_NAME = "_id";

    @DatabaseField(generatedId = true, columnName = ID_FIELD_NAME)
    protected int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    protected abstract T getNewLocalObject();


    protected DbTable mapFrom(T object) {
        if (object != null) {
            setId(object.getDbId());
        }
        return this;
    }

    protected void mapTo(T object) {
        if (object != null) {
            object.setDbId(getId());
        }
    }
}
