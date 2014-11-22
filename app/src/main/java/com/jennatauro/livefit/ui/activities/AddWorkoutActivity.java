package com.jennatauro.livefit.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.jennatauro.livefit.R;
import com.jennatauro.livefit.data.db.DbHelper;
import com.jennatauro.livefit.data.models.Exercise;
import com.jennatauro.livefit.data.models.Workout;
import com.jennatauro.livefit.ui.adapters.ExerciseAdapter;
import com.jennatauro.livefit.ui.dialogs.ExerciseDialog;

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
    private List<Exercise> mExercises = new ArrayList<Exercise>();

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

                mDbHelper.createOrUpdateWorkoutWithExercises(workout);
                finish();
            }
            case (R.id.add_exercise_button): {
                ExerciseDialog exerciseDialog = new ExerciseDialog(this);
                exerciseDialog.show();
            }
        }
    }
}
