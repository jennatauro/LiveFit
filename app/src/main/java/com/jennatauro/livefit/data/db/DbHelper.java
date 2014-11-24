package com.jennatauro.livefit.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.jennatauro.livefit.data.db.tables.DbExercise;
import com.jennatauro.livefit.data.db.tables.DbTable;
import com.jennatauro.livefit.data.db.tables.DbWorkout;
import com.jennatauro.livefit.data.models.Exercise;
import com.jennatauro.livefit.data.models.LocalObject;
import com.jennatauro.livefit.data.models.Workout;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

/**
 * Created by jennatauro on 2014-10-08.
 */
public class DbHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "livefitDb.db";
    private static final int DATABASE_VERSION = 1;

    public static final Class[] TABLES = {
            DbWorkout.class,
            DbExercise.class
    };

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        for( Class table : TABLES ) {
            try {
                TableUtils.createTable(connectionSource, table);
            } catch (SQLException e) {
                Log.e(e.getMessage(), "Fail to create db tables.");
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        //TODO Upgrade from v1
    }

    public void createOrUpdateWorkoutWithExercises(final Workout workout){
        if(workout==null) return;
        try {
            final Dao<DbWorkout, Integer> workoutDao = DbWorkout.getDao(this);
            workoutDao.callBatchTasks(new Callable<Void>() {
                @Override
                public Void call() throws SQLException {
                    createOrUpdateWorkout(workout);
                    return null;
                }
            });
        } catch (SQLException e) {
            Log.e(e.getMessage(), "Error inserting Workout");
        }
        catch (Exception e) {
            Log.e(e.getMessage(), "Unhandled exception inserting Workout");
        }
    }

    public DbExercise createOrUpdateExercise(Exercise exercise, DbWorkout dbWorkout) throws SQLException {
        Dao<DbExercise, Integer> dao = DbExercise.getDao(this);
        DbExercise dbExercise = new DbExercise();
        dbExercise.mapFrom(exercise);
        dbExercise.setWorkout(dbWorkout);
        dao.createOrUpdate(dbExercise);
        // If this was a create, we set the DB ID on the passed-in work item
        exercise.setDbId(dbExercise.getId());
        return dbExercise;
    }

    private DbWorkout createOrUpdateWorkout(Workout workout) throws SQLException {
        Dao<DbWorkout, Integer> dao = DbWorkout.getDao(this);
        DbWorkout dbWorkout = new DbWorkout();
        dbWorkout.mapFrom(workout);
        dao.createOrUpdate(dbWorkout);
        workout.setDbId(dbWorkout.getId());
        List<Integer> dbExerciseIds = new ArrayList<Integer>();
        if (workout.getExercises() != null) {
            for (Exercise exercise : workout.getExercises()) {
                DbExercise dbExercise = createOrUpdateExercise(exercise, dbWorkout);
                dbExerciseIds.add(dbExercise.getId());
            }
        }
        return dbWorkout;
    }

    public List<Workout> getWorkouts(){
        try {
            List<DbWorkout> dbWorkouts = getWorkoutsFromDb();
            List<Workout> result = new ArrayList<Workout>();
            for (DbWorkout dbWorkout : dbWorkouts) {
                Workout workout = dbWorkout.mapToLocalObject();
                result.add(workout);
            }
            return result;
        }
        catch(SQLException e){
            Log.e(e.getMessage(), "Error fetching workouts");
            return null;
        }
    }

    public List<DbWorkout> getWorkoutsFromDb() throws SQLException {
        List<DbWorkout> dbWorkouts = DbWorkout.getDao(this).queryForAll();
        return dbWorkouts;
    }

    public List<Exercise> getExercisesForWorkout(Workout workout) {
        if (workout == null) return null;
        try {
            Dao<DbWorkout, Integer> dbWorkoutIntegerDao = DbWorkout.getDao(this);
            DbWorkout dbWorkout = new DbWorkout();
            dbWorkout.mapFrom(workout);
            List<DbExercise> dbExercises = getExercisesForDbWorkout(dbWorkout);
            List<Exercise> result = new ArrayList<Exercise>();

            for (DbExercise dbExercise : dbExercises) {
                Exercise exercise = dbExercise.mapToLocalObject();
                exercise.setWorkout(workout);
                result.add(exercise);
            }
            return result;
        } catch (SQLException e) {
            Log.e(e.getMessage(), "Error fetching Exercises for Workout");
            return null;
        }
    }

    private List<DbExercise> getExercisesForDbWorkout(DbWorkout workout) throws SQLException {
        List<DbExercise> dbExercises = DbExercise.getDao(this).queryForEq(DbExercise.WORKOUT_ID_FIELD_NAME, workout);
        return dbExercises;
    }

    public Workout getWorkoutForId(int id) throws SQLException {
        DbWorkout dbWorkout = DbWorkout.getDao(this).queryForId(id);
        Workout workout = dbWorkout.mapToLocalObject();
        return workout;
    }

    public void deleteExercise(Exercise exercise) {
        try {
            DeleteBuilder<?, Integer> deleteBuilder = DbExercise.getDao(this).deleteBuilder();
            deleteBuilder.where().eq(DbExercise.ID_FIELD_NAME, exercise.getDbId());
            deleteBuilder.delete();
        } catch (SQLException e) {
            Log.e(e.getMessage(), "Error deleting exercise");
        }
    }
}
