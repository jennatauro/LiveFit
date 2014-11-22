package com.jennatauro.livefit.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.jennatauro.livefit.R;

/**
 * Created by jennatauro on 2014-11-22.
 */
public class AddWorkoutActivity extends LiveFitActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.add_workout_activity_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.add_workout));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
