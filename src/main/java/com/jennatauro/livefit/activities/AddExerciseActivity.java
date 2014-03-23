package com.jennatauro.livefit.activities;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.jennatauro.livefit.R;
import com.jennatauro.livefit.models.Exercise;

public class AddExerciseActivity extends ActionBarActivity implements View.OnClickListener{

    EditText etName;
    EditText etDescription;
    EditText etSeconds;
    EditText etReps;
    boolean isTimed=true;

    public static void start(Context context){
        context.startActivity(getIntent(context));
    }

    private static Intent getIntent(Context context) {
        final Intent intent = new Intent(context, AddExerciseActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        setOnClickListeners();

        etName = (EditText) findViewById(R.id.et_exercise_name);
        etDescription = (EditText) findViewById(R.id.et_exercise_description);
        etSeconds = (EditText) findViewById(R.id.et_seconds);
        etReps = (EditText) findViewById(R.id.et_reps);

    }

    private void setOnClickListeners() {
        findViewById(R.id.iv_seconds_check).setOnClickListener(this);
        findViewById(R.id.iv_reps_check).setOnClickListener(this);
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
            case R.id.iv_seconds_check:
                isTimed = true;
                setCheckBoxesandEditTextsVisible(isTimed);
            break;
            case R.id.iv_reps_check:
                isTimed= false;
                setCheckBoxesandEditTextsVisible(isTimed);
            break;
            case R.id.btn_add_exercise:
                if(isTimed){
                    Exercise exercise = new Exercise(etName.getText().toString(), etDescription.getText().toString(), isTimed, Integer.parseInt(etSeconds.getText().toString()), 0 );
                }
                else{
                    Exercise exercise = new Exercise(etName.getText().toString(), etDescription.getText().toString(), isTimed, 0, Integer.parseInt(etReps.getText().toString()));
                }
            break;
        }
    }

    private void setCheckBoxesandEditTextsVisible(boolean isTimed) {
        if(isTimed){
            findViewById(R.id.iv_seconds_check).setBackgroundResource(android.R.drawable.checkbox_on_background);
            findViewById(R.id.iv_reps_check).setBackgroundResource(android.R.drawable.checkbox_off_background);

            findViewById(R.id.tv_seconds).setVisibility(View.VISIBLE);
            findViewById(R.id.et_seconds).setVisibility(View.VISIBLE);

            findViewById(R.id.tv_reps).setVisibility(View.GONE);
            findViewById(R.id.et_reps).setVisibility(View.GONE);

            etReps.setText("");
        }
        else{
            findViewById(R.id.iv_seconds_check).setBackgroundResource(android.R.drawable.checkbox_off_background);
            findViewById(R.id.iv_reps_check).setBackgroundResource(android.R.drawable.checkbox_on_background);

            findViewById(R.id.tv_seconds).setVisibility(View.GONE);
            findViewById(R.id.et_seconds).setVisibility(View.GONE);

            findViewById(R.id.tv_reps).setVisibility(View.VISIBLE);
            findViewById(R.id.et_reps).setVisibility(View.VISIBLE);

            etSeconds.setText("");
        }
    }
}
