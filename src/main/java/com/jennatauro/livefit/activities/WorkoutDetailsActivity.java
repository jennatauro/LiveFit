package com.jennatauro.livefit.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.jennatauro.livefit.R;

public class WorkoutDetailsActivity extends ActionBarActivity {

    public static final String EXTRA_WORKOUT_NAME_KEY = "EXTRA_WORKOUT_NAME_KEY";

    public static void start(Context context, String workoutName){
        context.startActivity(getIntent(context, workoutName));
    }

    private static Intent getIntent(Context context, String workoutName) {
        final Intent intent = new Intent(context, WorkoutDetailsActivity.class);
        intent.putExtra(EXTRA_WORKOUT_NAME_KEY, workoutName);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_details);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

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
