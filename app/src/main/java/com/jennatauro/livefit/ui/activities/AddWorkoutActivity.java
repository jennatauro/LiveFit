package com.jennatauro.livefit.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.jennatauro.livefit.R;
import com.jennatauro.livefit.data.db.DbHelper;
import com.jennatauro.livefit.data.models.Workout;

/**
 * Created by jennatauro on 2014-11-22.
 */
public class AddWorkoutActivity extends LiveFitActivity implements View.OnClickListener{

    private EditText workoutNameEditText;
    private EditText workoutDescriptionEditText;
    private DbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout);

        mDbHelper = new DbHelper(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.add_workout_activity_toolbar);

        workoutNameEditText = (EditText) findViewById(R.id.activity_add_workout_workout_name);
        workoutDescriptionEditText = (EditText) findViewById(R.id.activity_add_workout_workout_description);


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.add_workout));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        findViewById(R.id.create_workout_button).setOnClickListener(this);
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
        }
    }
}
