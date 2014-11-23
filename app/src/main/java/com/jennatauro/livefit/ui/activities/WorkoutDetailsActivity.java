package com.jennatauro.livefit.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.jennatauro.livefit.R;
import com.jennatauro.livefit.data.db.DbHelper;
import com.jennatauro.livefit.data.models.Exercise;
import com.jennatauro.livefit.data.models.Workout;
import com.jennatauro.livefit.ui.adapters.ExerciseAdapter;
import com.jennatauro.livefit.ui.fragments.SeeAllWorkoutsFragment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;

/**
 * Created by jennatauro on 2014-11-23.
 */
public class WorkoutDetailsActivity extends LiveFitActivity {

    @Inject
    DbHelper mDbHelper;

    @Inject
    ExerciseAdapter mAdapter;

    @InjectView(R.id.workout_details_toolbar)
    Toolbar mToolbar;

    Workout mWorkout;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Exercise> mExercises = new ArrayList<Exercise>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_workout_details);
        super.onCreate(savedInstanceState);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.exercises_recyclerview);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        int workoutId = getIntent().getIntExtra(SeeAllWorkoutsFragment.WORKOUT_ID, 0);

        setSupportActionBar(mToolbar);
        try {
            mWorkout = mDbHelper.getWorkoutForId(workoutId);
            getSupportActionBar().setTitle(mWorkout.getTitle());
        } catch (SQLException e) {
            getSupportActionBar().setTitle(getString(R.string.app_name));
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        ((TextView) findViewById(R.id.workout_description)).setText(mWorkout.getDescription());

        displayExercises();
    }

    private void displayExercises() {
        mExercises = mDbHelper.getExercisesForWorkout(mWorkout);
        mAdapter.replace(mExercises);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

}
