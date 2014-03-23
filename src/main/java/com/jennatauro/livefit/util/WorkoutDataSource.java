package com.jennatauro.livefit.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.util.Log;

import com.jennatauro.livefit.models.Workout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jennatauro on 3/6/2014.
 */
public class WorkoutDataSource {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private String[] allColumns = { DatabaseHelper.colID, DatabaseHelper.colName};

    public WorkoutDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void createTable() {
        database.execSQL(DatabaseHelper.DATABASE_CREATE);
    }

    public void deleteTable() {
        database.execSQL("DROP TABLE IF EXISTS " + DatabaseHelper.workoutTable);
    }

    public Workout createWorkout(Workout workout){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.colName, workout.getWorkoutname() );
        long insertId = database.insert(DatabaseHelper.workoutTable, null, values);

        if(insertId !=-1){
            Cursor cursor = database.query(DatabaseHelper.workoutTable, allColumns, DatabaseHelper.colID + " = " + insertId, null, null, null, null);

            cursor.moveToFirst();
            Workout newWorkout = cursorToWorkout(cursor);

            cursor.close();
            return newWorkout;
        }
        else{
            return null;
        }
    }

    //TODO currently deleting by name
    public void deleteWorkout(Workout workout) {
        String workoutName = workout.getWorkoutname();
        database.delete(DatabaseHelper.workoutTable, DatabaseHelper.colName
                + " = '" + workoutName + "'", null);
    }

    public List<Workout> getAllWorkouts() {
        List<Workout> workouts = new ArrayList<Workout>();

        Cursor cursor = database.query(DatabaseHelper.workoutTable,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Workout workout = cursorToWorkout(cursor);
            workouts.add(workout);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return workouts;
    }

    //TODO currently doing by name as well
    public boolean has(Workout workout) {
        Cursor cursor = database.rawQuery("select 1 from " + DatabaseHelper.workoutTable + " where " + DatabaseHelper.colName + "=?",
                new String[] { workout.getWorkoutname() });
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    public void logColumnNames () {
        Cursor ti = database.rawQuery("PRAGMA table_info("+DatabaseHelper.workoutTable+")", null);
        if ( ti.moveToFirst() ) {
            do {
                Log.d("DATABASE TABLE NAME", "col: " + ti.getString(1));
            } while (ti.moveToNext());
        }

        ti.close();
    }

    private Workout cursorToWorkout(Cursor cursor) {
        return new Workout(cursor.getString(1));
    }
}
