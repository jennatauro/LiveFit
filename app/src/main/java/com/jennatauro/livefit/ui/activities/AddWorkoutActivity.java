package com.jennatauro.livefit.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jennatauro.livefit.R;

/**
 * Created by jennatauro on 2014-11-22.
 */
public class AddWorkoutActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.add_workout_activity_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.add_workout));
    }

}
