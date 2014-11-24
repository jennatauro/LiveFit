package com.jennatauro.livefit.ui.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.jennatauro.livefit.R;
import com.jennatauro.livefit.data.db.DbHelper;
import com.jennatauro.livefit.data.models.Exercise;
import com.jennatauro.livefit.data.models.Workout;
import com.jennatauro.livefit.eventBus.events.SeeExerciseEvent;
import com.jennatauro.livefit.ui.adapters.ExerciseAdapter;
import com.jennatauro.livefit.ui.fragments.SeeAllWorkoutsFragment;
import com.squareup.otto.Subscribe;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;

/**
 * Created by jennatauro on 2014-11-23.
 */
public class WorkoutDetailsActivity extends LiveFitActivity {

    public static final String WORKOUT_ID = "workout_id";

    @Inject
    DbHelper mDbHelper;

    @Inject
    ExerciseAdapter mAdapter;

    @InjectView(R.id.workout_details_toolbar)
    Toolbar mToolbar;

    Workout mWorkout;

    private Dialog seeExerciseDialog;

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
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.edit_workout:
                Intent intent = new Intent(this, EditWorkoutActivity.class);
                intent.putExtra(WORKOUT_ID, mWorkout.getDbId());
                startActivity(intent);
                return true;
            default:
                finish();
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_workout_details, menu);
        return true;
    }

    @Subscribe
    public void seeExercise(SeeExerciseEvent e){
        Exercise exercise = e.getExerciseToSee();

        seeExerciseDialog = new Dialog(this);
        seeExerciseDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        seeExerciseDialog.setContentView(R.layout.dialog_see_exercise);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(seeExerciseDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        seeExerciseDialog.show();
        seeExerciseDialog.getWindow().setAttributes(lp);

        ((TextView) seeExerciseDialog.findViewById(R.id.exercise_name)).setText(exercise.getTitle());
        ((TextView) seeExerciseDialog.findViewById(R.id.dialog_exercise_description)).setText(exercise.getDescription());
        ((TextView) seeExerciseDialog.findViewById(R.id.dialog_exercise_weight)).setText(exercise.getWeight() + "");
        ((TextView) seeExerciseDialog.findViewById(R.id.dialog_exercise_reps)).setText(exercise.getReps() + "");
        ((TextView) seeExerciseDialog.findViewById(R.id.dialog_exercise_seconds)).setText(exercise.getSeconds() + "");
    }

}
