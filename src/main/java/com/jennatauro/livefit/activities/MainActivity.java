package com.jennatauro.livefit.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;

import com.jennatauro.livefit.R;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.ll_fitness).setOnClickListener(this);
        findViewById(R.id.ll_health).setOnClickListener(this);
        findViewById(R.id.ll_goals).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.ll_fitness:
                FitnessHome.start(this);
                break;
        }
    }
}
