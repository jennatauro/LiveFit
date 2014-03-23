package com.jennatauro.livefit.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.jennatauro.livefit.R;
import com.jennatauro.livefit.adapters.ExercisesAdapter;
import com.jennatauro.livefit.fragments.DayFitnessFragment;
import com.jennatauro.livefit.models.Exercise;
import com.jennatauro.livefit.models.Workout;
import com.jennatauro.livefit.util.WorkoutDataSource;

import java.util.ArrayList;

public class CreateNewWorkoutActivity extends ActionBarActivity implements View.OnClickListener{


    ListView listView;
    ExercisesAdapter adapter;

    ArrayList<Exercise> exercises;

    private WorkoutDataSource dataSource;

    public static void start(Context context){
        context.startActivity(getIntent(context));
    }

    private static Intent getIntent(Context context) {
        final Intent intent = new Intent(context, CreateNewWorkoutActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_workout);

        try {
            dataSource = new WorkoutDataSource(this);
            dataSource.open();
        } catch (SQLException e) {
            Log.d("OPENINGDATABASEEXCEPTION", "OPENINGDATABASEEXCEPTION " + e.getMessage());
            e.printStackTrace();
        }

        //adapter = new ExercisesAdapter(this, R.layout.list_item_exercises, exercises);

        listView = (ListView) findViewById(R.id.lv_workout_exercises);
        //listView.setAdapter(adapter);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.btn_add_exercise).setOnClickListener(this);
        findViewById(R.id.btn_save).setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_add_exercise:
                AddExerciseActivity.start(this);
            break;
            case R.id.btn_save:
                String newWorkoutName = ((EditText) findViewById(R.id.et_workout_name)).getText().toString();
                Workout workoutToAdd = new Workout(newWorkoutName);
                dataSource.createWorkout(workoutToAdd);
                finish();
            break;
        }
    }

    public void updateExercisesList(){
        //exercises.add();
        adapter.notifyDataSetChanged();
    }
}
