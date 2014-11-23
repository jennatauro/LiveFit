package com.jennatauro.livefit.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jennatauro.livefit.R;

import butterknife.InjectView;
import butterknife.OnClick;


public class MainActivity extends LiveFitActivity{

    @InjectView(R.id.main_activity_toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
    }

    @OnClick(R.id.ll_fitness)
    void fitness() {
        Intent intent = new Intent(this, FitnessActivity.class);
        startActivity(intent);
    }

}
