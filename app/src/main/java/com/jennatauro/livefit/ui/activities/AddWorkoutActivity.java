package com.jennatauro.livefit.ui.activities;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.jennatauro.livefit.R;
import com.jennatauro.livefit.data.db.DbHelper;
import com.jennatauro.livefit.data.models.Exercise;
import com.jennatauro.livefit.data.models.Workout;
import com.jennatauro.livefit.eventBus.events.EditExerciseEvent;
import com.jennatauro.livefit.eventBus.events.ExerciseDeletedEvent;
import com.jennatauro.livefit.eventBus.events.SeeExerciseEvent;
import com.jennatauro.livefit.ui.adapters.ExerciseAdapter;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by jennatauro on 2014-11-22.
 */
public class AddWorkoutActivity extends LiveFitActivity implements View.OnClickListener {

    @InjectView(R.id.activity_add_workout_workout_name)
    EditText workoutNameEditText;

    @InjectView(R.id.activity_add_workout_workout_description)
    EditText workoutDescriptionEditText;

    @Inject
    DbHelper mDbHelper;

    @Inject
    ExerciseAdapter mAdapter;

    @InjectView(R.id.add_workout_activity_toolbar)
    Toolbar mToolbar;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    private EditText exerciseNameEditText;
    private EditText exerciseDescriptionEditText;
    private EditText exerciseWeightEditText;
    private EditText exerciseRepsEditText;
    private EditText exerciseTimeEditText;
    private List<Exercise> mExercises = new ArrayList<Exercise>();

    private Dialog createExerciseDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_workout);
        super.onCreate(savedInstanceState);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.exercises_recyclerview);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.add_workout));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        findViewById(R.id.create_workout_button).setOnClickListener(this);
        findViewById(R.id.add_exercise_button).setOnClickListener(this);

        displayExercises();
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayExercises();
    }

    private void displayExercises() {
        mAdapter.replace(mExercises);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.create_workout_button)
    void createWorkout() {
        Workout workout = new Workout();
        workout.setTitle(workoutNameEditText.getText().toString());
        workout.setDescription(workoutDescriptionEditText.getText().toString());
        workout.setExercises(mExercises);

        mDbHelper.createOrUpdateWorkoutWithExercises(workout);
        finish();
    }

    @Subscribe
    public void seeExercise(SeeExerciseEvent e){
        Exercise exercise = e.getExerciseToSee();
    }

    @Subscribe
    public void editExercise(EditExerciseEvent e){
        Exercise exercise = e.getExerciseToEdit();
    }

    @Subscribe
    public void exerciseDeleted(ExerciseDeletedEvent e){
        Exercise exercise = e.getDeletedExercise();
        mExercises.remove(exercise);
        displayExercises();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.create_workout_button): {
                Workout workout = new Workout();
                workout.setTitle(workoutNameEditText.getText().toString());
                workout.setDescription(workoutDescriptionEditText.getText().toString());
                workout.setExercises(mExercises);

                mDbHelper.createOrUpdateWorkoutWithExercises(workout);
                finish();
            }
            case (R.id.add_exercise_button): {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                createExerciseDialog = new Dialog(this);
                createExerciseDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                createExerciseDialog.setContentView(R.layout.dialog_create_exercise);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(createExerciseDialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                createExerciseDialog.show();
                createExerciseDialog.getWindow().setAttributes(lp);

                createExerciseDialog.findViewById(R.id.create_exercise_button).setOnClickListener(this);
                exerciseNameEditText = (EditText) createExerciseDialog.findViewById(R.id.dialog_exercise_name);
                exerciseDescriptionEditText = (EditText) createExerciseDialog.findViewById(R.id.dialog_exercise_description);
                exerciseWeightEditText = (EditText) createExerciseDialog.findViewById(R.id.dialog_exercise_weight);
                exerciseRepsEditText = (EditText) createExerciseDialog.findViewById(R.id.dialog_exercise_reps);
                exerciseTimeEditText = (EditText) createExerciseDialog.findViewById(R.id.dialog_exercise_seconds);
                break;
            }
            case (R.id.create_exercise_button): {
                Exercise exercise = new Exercise();
                exercise.setTitle(exerciseNameEditText.getText().toString());
                exercise.setDescription(exerciseDescriptionEditText.getText().toString());
                exercise.setWeight(Integer.parseInt(exerciseWeightEditText.getText().toString()));
                exercise.setReps(Integer.parseInt(exerciseRepsEditText.getText().toString()));
                exercise.setSeconds(Integer.parseInt(exerciseTimeEditText.getText().toString()));
                mExercises.add(exercise);
                displayExercises();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(exerciseNameEditText.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(exerciseDescriptionEditText.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(exerciseWeightEditText.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(exerciseRepsEditText.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(exerciseTimeEditText.getWindowToken(), 0);
                createExerciseDialog.dismiss();

                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                break;
            }
        }
    }
}
