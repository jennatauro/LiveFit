package com.jennatauro.livefit.data.db.tables;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.jennatauro.livefit.data.models.Exercise;
import com.jennatauro.livefit.data.models.Workout;

import java.sql.SQLException;

/**
 * Created by jennatauro on 2014-10-09.
 */
@DatabaseTable(tableName = "exercises")
public class DbExercise extends DbTable<Exercise>{

    public static final String WORKOUT_ID_FIELD_NAME = "workoutid";
    public static final String TITLE_FIELD_NAME = "title";
    public static final String DESCRIPTION_FIELD_NAME = "description";
    public static final String WEIGHT_FIELD_NAME = "weight";
    public static final String REPS_FIELD_NAME = "reps";
    public static final String SECONDS_FIELD_NAME = "seconds";

    @DatabaseField(foreign = true, columnName = WORKOUT_ID_FIELD_NAME, canBeNull = false)
    private DbWorkout workout;

    @DatabaseField(columnName = TITLE_FIELD_NAME, canBeNull = false)
    private String title;

    @DatabaseField(columnName = DESCRIPTION_FIELD_NAME)
    private String description;


    @DatabaseField(columnName = WEIGHT_FIELD_NAME)
    private int weight;

    @DatabaseField(columnName = REPS_FIELD_NAME)
    private int reps;

    @DatabaseField(columnName = SECONDS_FIELD_NAME)
    private int seconds;

    public DbWorkout getWorkout() {
        return workout;
    }

    public void setWorkout(DbWorkout workout) {
        this.workout = workout;
    }

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

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public static Dao<DbExercise, Integer> getDao(OrmLiteSqliteOpenHelper helper) throws SQLException {
        return helper.getDao(DbExercise.class);
    }

    @Override
    protected Exercise getNewLocalObject() {
        return new Exercise();
    }

    public Exercise mapToLocalObject() {
        Exercise localObject = getNewLocalObject();
        mapTo(localObject);
        return localObject;
    }

    @Override
    public void mapTo(Exercise object) {
        super.mapTo(object);
        if (object != null) {
            object.setTitle(getTitle());
            object.setDescription(getDescription());
            object.setWeight(getWeight());
            object.setReps(getReps());
            object.setSeconds(getSeconds());
        }
    }


    @Override
    public DbExercise mapFrom(Exercise object) {
        super.mapFrom(object);
        if (object != null) {
            setTitle(object.getTitle());
            setDescription(object.getDescription());
            setWeight(object.getWeight());
            setReps(object.getReps());
            setSeconds(object.getSeconds());
        }
        return this;
    }

}
