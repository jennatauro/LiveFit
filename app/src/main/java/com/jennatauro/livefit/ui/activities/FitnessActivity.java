package com.jennatauro.livefit.ui.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.jennatauro.livefit.R;

/**
 * Created by jennatauro on 2014-10-09.
 */
public class FitnessActivity extends ActionBarActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness);

        mToolbar = (Toolbar) findViewById(R.id.fitness_activity_toolbar);
        setSupportActionBar(mToolbar);

    }

}
