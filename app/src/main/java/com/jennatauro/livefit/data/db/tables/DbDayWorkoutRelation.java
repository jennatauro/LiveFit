package com.jennatauro.livefit.data.db.tables;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.jennatauro.livefit.data.models.WorkoutDayRelation;

import java.sql.SQLException;

/**
 * Created by jennatauro on 2015-01-04.
 */
public class DbDayWorkoutRelation extends DbTable<WorkoutDayRelation> {

    public static final String WORKOUT_ID_FIELD_NAME = "workout_id";
    public static final String DAY_OF_WEEK_ID_FIELD_NAME = "day_of_week_id";

    @DatabaseField(columnName = WORKOUT_ID_FIELD_NAME, canBeNull = false)
    private int workoutId;

    @DatabaseField(columnName = DAY_OF_WEEK_ID_FIELD_NAME, canBeNull = false)
    private int dayOfTheWeekId;

    public int getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(int workoutId) {
        this.workoutId = workoutId;
    }

    public int getDayOfTheWeekId() {
        return dayOfTheWeekId;
    }

    public void setDayOfTheWeekId(int dayOfTheWeekId) {
        this.dayOfTheWeekId = dayOfTheWeekId;
    }

    public static Dao<DbDayWorkoutRelation, Integer> getDao(OrmLiteSqliteOpenHelper helper) throws SQLException {
        return helper.getDao(DbDayWorkoutRelation.class);
    }

    @Override
    protected WorkoutDayRelation getNewLocalObject() {
        return new WorkoutDayRelation();
    }

    public WorkoutDayRelation mapToLocalObject() {
        WorkoutDayRelation localObject = getNewLocalObject();
        mapTo(localObject);
        return localObject;
    }

    @Override
    public void mapTo(WorkoutDayRelation object) {
        super.mapTo(object);
        if (object != null) {
            object.setmWorkoutId(getWorkoutId());
            object.setmDayId(getDayOfTheWeekId());
        }
    }


    @Override
    public DbDayWorkoutRelation mapFrom(WorkoutDayRelation object) {
        super.mapFrom(object);
        if (object != null) {
            setWorkoutId(object.getmWorkoutId());
            setDayOfTheWeekId(object.getmDayId());
        }
        return this;
    }
}
