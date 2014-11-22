package com.jennatauro.livefit.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jennatauro.livefit.R;


public class MainActivity extends LiveFitActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.ll_fitness).setOnClickListener(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_activity_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.ll_fitness:
                Intent intent = new Intent(this, FitnessActivity.class);
                startActivity(intent);
                break;
        }
    }
}
