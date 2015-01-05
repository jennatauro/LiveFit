package com.jennatauro.livefit.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.jennatauro.livefit.R;
import com.jennatauro.livefit.data.db.DbHelper;
import com.jennatauro.livefit.data.models.Workout;

import java.sql.SQLException;

import javax.inject.Inject;

import butterknife.InjectView;

/**
 * Created by jennatauro on 2015-01-04.
 */
public class DoWorkoutActivity extends LiveFitActivity {

    public static final String EXTRA_WORKOUT_ID = "extra_workout_id";

    @Inject
    DbHelper mDbHelper;

    @InjectView(R.id.do_workout_toolbar)
    Toolbar mToolbar;

    int mWorkoutId;

    Workout mWorkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_do_workout);
        super.onCreate(savedInstanceState);

        mWorkoutId = getIntent().getIntExtra(EXTRA_WORKOUT_ID, 0);

        try {
            mWorkout = mDbHelper.getWorkoutForId(mWorkoutId);
        } catch(SQLException e) {
            Log.e(e.getMessage(), "Error getting workout for id");
        }

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        if(mWorkout != null) {
            getSupportActionBar().setTitle(mWorkout.getTitle());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
