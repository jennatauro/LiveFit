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
    public static final String DESCRIPTION_FIELD_NAME = "description";

    @DatabaseField(columnName = TITLE_FIELD_NAME, canBeNull = false)
    private String title;


    @DatabaseField(columnName = DESCRIPTION_FIELD_NAME)
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
            object.setDescription(getDescription());
        }
    }


    @Override
    public DbWorkout mapFrom(Workout object) {
        super.mapFrom(object);
        if (object != null) {
            setTitle(object.getTitle());
            setDescription(object.getDescription());
        }
        return this;
    }
}
