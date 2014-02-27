package com.jennatauro.livefit.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.jennatauro.livefit.R;
import com.jennatauro.livefit.adapters.ExercisesAdapter;
import com.jennatauro.livefit.models.Exercise;
import com.jennatauro.livefit.models.Workout;

import java.util.ArrayList;

public class WorkoutDetailsActivity extends ActionBarActivity {

    public static final String EXTRA_WORKOUT_NAME_KEY = "EXTRA_WORKOUT_NAME_KEY";
    public static final String EXTRA_EXERCISE_LIST_KEY = "EXTRA_EXERCISE_LIST_KEY";

    ArrayList<Exercise> exercises;
    ListView listView;

    ExercisesAdapter adapter;

    public static void start(Context context, String workoutName, ArrayList<Exercise> exercises){
        context.startActivity(getIntent(context, workoutName, exercises));
    }

    private static Intent getIntent(Context context, String workoutName, ArrayList<Exercise> exercises) {
        final Intent intent = new Intent(context, WorkoutDetailsActivity.class);
        intent.putExtra(EXTRA_WORKOUT_NAME_KEY, workoutName);
        intent.putParcelableArrayListExtra(EXTRA_EXERCISE_LIST_KEY, exercises);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_details);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        listView =  (ListView) findViewById(R.id.lv_exercises);

        exercises = new ArrayList<Exercise>();
        exercises = getIntent().getParcelableArrayListExtra(EXTRA_EXERCISE_LIST_KEY);

        adapter = new ExercisesAdapter(this, R.layout.list_item_exercises, exercises);
        listView.setAdapter(adapter);

                ((TextView) findViewById(R.id.tv_workout_name)).setText(getIntent().getStringExtra(EXTRA_WORKOUT_NAME_KEY));
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
}
