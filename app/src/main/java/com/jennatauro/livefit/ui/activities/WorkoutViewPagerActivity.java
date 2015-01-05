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
public class WorkoutViewPagerActivity extends LiveFitActivity {

    public static final String EXTRA_WORKOUT_ID = "extra_workout_id";

    @InjectView(R.id.workout_view_pager_toolbar)
    Toolbar mToolbar;

    @Inject
    DbHelper mDbHelper;

    int mWorkoutId;

    Workout mWorkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_workout_viewpager);
        super.onCreate(savedInstanceState);

        mWorkoutId = getIntent().getIntExtra(EXTRA_WORKOUT_ID, 0);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        try {
            mWorkout = mDbHelper.getWorkoutForId(mWorkoutId);
        } catch(SQLException e) {
            Log.e(e.getMessage(), "Error getting workout for id");
        }

        getSupportActionBar().setTitle(mWorkout.getTitle());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
