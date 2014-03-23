package com.jennatauro.livefit.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.jennatauro.livefit.models.Workout;

/**
 * Created by jennatauro on 3/5/2014.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    static final String dbName="workoutsDB";
    static final String workoutTable="Workouts";
    static final String colID="WorkoutID";
    static final String colName="WorkoutName";

    private static final int databaseVersion = 1;

    public static final String DATABASE_CREATE = "CREATE TABLE "+workoutTable+" " +
            "("+colID+ " INTEGER PRIMARY KEY AUTOINCREMENT , "+
            colName+ " TEXT)";

    public DatabaseHelper(Context context) {
        super(context, dbName, null,databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

        db.execSQL(DATABASE_CREATE);

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");

        db.execSQL("DROP TABLE IF EXISTS "+workoutTable);
        onCreate(db);
    }

}
