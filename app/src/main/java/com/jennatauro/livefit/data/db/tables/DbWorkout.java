package com.jennatauro.livefit.data.db.tables;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.jennatauro.livefit.data.models.Workout;

import java.sql.SQLException;

/**
 * Created by jennatauro on 2014-10-08.
 */
@DatabaseTable(tableName = "workouts")
public class DbWorkout extends DbTable<Workout>{

    public static final String TITLE_FIELD_NAME = "title";

    @DatabaseField(columnName = TITLE_FIELD_NAME, canBeNull = false)
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static Dao<DbWorkout, Integer> getDao(OrmLiteSqliteOpenHelper helper) throws SQLException {
        return helper.getDao(DbWorkout.class);
    }

    @Override
    protected Workout getNewLocalObject() {
        return new Workout();
    }

    public Workout mapToLocalObject() {
        Workout localObject = getNewLocalObject();
        mapTo(localObject);
        return localObject;
    }

    @Override
    public void mapTo(Workout object) {
        super.mapTo(object);
        if (object != null) {
            object.setTitle(getTitle());
        }
    }


    @Override
    public DbWorkout mapFrom(Workout object) {
        super.mapFrom(object);
        if (object != null) {
            setTitle(object.getTitle());
        }
        return this;
    }
}
