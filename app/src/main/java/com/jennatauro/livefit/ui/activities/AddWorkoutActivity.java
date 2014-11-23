package com.jennatauro.livefit.ui.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.jennatauro.livefit.R;
import com.jennatauro.livefit.data.db.DbHelper;
import com.jennatauro.livefit.data.models.Exercise;
import com.jennatauro.livefit.data.models.Workout;
import com.jennatauro.livefit.ui.adapters.ExerciseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jennatauro on 2014-11-22.
 */
public class AddWorkoutActivity extends LiveFitActivity implements View.OnClickListener{

    private EditText workoutNameEditText;
    private EditText workoutDescriptionEditText;
    private DbHelper mDbHelper;
    private ExerciseAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    private EditText exerciseNameEditText;
    private EditText exerciseDescriptionEditText;
    private EditText exerciseWeightEditText;
    private EditText exerciseRepsEditText;
    private EditText exerciseTimeEditText;
    private List<Exercise> mExercises = new ArrayList<Exercise>();

    private Dialog exerciseDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout);

        mAdapter = new ExerciseAdapter();
        mDbHelper = new DbHelper(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.exercises_recyclerview);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.add_workout_activity_toolbar);

        workoutNameEditText = (EditText) findViewById(R.id.activity_add_workout_workout_name);
        workoutDescriptionEditText = (EditText) findViewById(R.id.activity_add_workout_workout_description);


        setSupportActionBar(toolbar);
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

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case (R.id.create_workout_button): {
                Workout workout = new Workout();
                workout.setTitle(workoutNameEditText.getText().toString());
                workout.setDescription(workoutDescriptionEditText.getText().toString());
                workout.setExercises(mExercises);

                mDbHelper.createOrUpdateWorkoutWithExercises(workout);
                finish();
            }
            case (R.id.add_exercise_button): {
                exerciseDialog = new Dialog(this);
                exerciseDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                exerciseDialog.setContentView(R.layout.dialog_exercise);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(exerciseDialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                exerciseDialog.show();
                exerciseDialog.getWindow().setAttributes(lp);

                exerciseDialog.findViewById(R.id.create_exercise_button).setOnClickListener(this);
                exerciseNameEditText = (EditText) exerciseDialog.findViewById(R.id.dialog_exercise_name);
                exerciseDescriptionEditText = (EditText) exerciseDialog.findViewById(R.id.dialog_exercise_description);
                exerciseWeightEditText = (EditText) exerciseDialog.findViewById(R.id.dialog_exercise_weight);
                exerciseRepsEditText = (EditText) exerciseDialog.findViewById(R.id.dialog_exercise_reps);
                exerciseTimeEditText = (EditText) exerciseDialog.findViewById(R.id.dialog_exercise_seconds);
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
                exerciseDialog.dismiss();
                break;
            }
        }
    }
}
