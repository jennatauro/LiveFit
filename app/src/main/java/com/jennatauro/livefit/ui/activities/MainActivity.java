package com.jennatauro.livefit.ui.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.jennatauro.livefit.R;
import com.jennatauro.livefit.data.db.DbHelper;
import com.jennatauro.livefit.data.models.Exercise;
import com.jennatauro.livefit.data.models.Workout;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DbHelper helper = new DbHelper(this);
        Workout workout = new Workout();
        workout.setTitle("Created Workout");
        workout.setDescription("Desc1");

        List<Exercise> list = new ArrayList<Exercise>();
        Exercise exercise = new Exercise();
        exercise.setTitle("Exercise Title");
        list.add(exercise);
        workout.setExercises(list);

        Workout workout1 = new Workout();
        workout1.setTitle("Second Workout");
        workout1.setDescription("Desc2");

        helper.createOrUpdateWorkoutWithExercises(workout);
        helper.createOrUpdateWorkoutWithExercises(workout1);


        List<Workout> workouts = helper.getWorkouts();
        ((TextView) findViewById(R.id.textview)).setText(workouts.get(0).getTitle());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
