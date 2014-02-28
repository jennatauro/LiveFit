package com.jennatauro.livefit.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.jennatauro.livefit.R;
import com.jennatauro.livefit.adapters.ExercisesAdapter;
import com.jennatauro.livefit.models.Exercise;

import java.util.ArrayList;


public class CreateNewWorkoutActivity extends ActionBarActivity implements View.OnClickListener{

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

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.btn_add_exercise).setOnClickListener(this);
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
        }
    }
}
