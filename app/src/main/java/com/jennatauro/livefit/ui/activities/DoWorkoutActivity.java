package com.jennatauro.livefit.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

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

    @InjectView(R.id.start_workout_button)
    Button startWorkoutButton;

    @InjectView(R.id.no_exercises_layout)
    LinearLayout noExercisesLayout;

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

            if(mWorkout.getExercises() == null || mWorkout.getExercises().size() == 0) {
                noExercisesLayout.setVisibility(View.VISIBLE);
                startWorkoutButton.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
