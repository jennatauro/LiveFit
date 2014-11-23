package com.jennatauro.livefit.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.jennatauro.livefit.R;
import com.jennatauro.livefit.data.db.DbHelper;
import com.jennatauro.livefit.data.models.Workout;
import com.jennatauro.livefit.ui.fragments.SeeAllWorkoutsFragment;

import javax.inject.Inject;

import butterknife.InjectView;

/**
 * Created by jennatauro on 2014-11-23.
 */
public class WorkoutDetailsActivity extends LiveFitActivity {

    @Inject
    DbHelper mDbHelper;

    @InjectView(R.id.workout_details_toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_workout_details);
        super.onCreate(savedInstanceState);

        int workoutId = getIntent().getIntExtra(SeeAllWorkoutsFragment.WORKOUT_ID, 0);

        Workout workout = mDbHelper.getWorkoutForId(workoutId);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("HEYYYYYY");
    }

}
