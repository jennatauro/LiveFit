package com.jennatauro.livefit.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.jennatauro.livefit.R;
import com.jennatauro.livefit.fragments.DayFitnessFragment;

public class FitnessHome extends AbsTabActivity {

    public static void start(Context context) {
        final Intent intent = new Intent(context, FitnessHome.class);
        context.startActivity(intent);
    }

    @Override
    protected void addTabs() {
        addTab(new Tab(R.string.monday, DayFitnessFragment.class));
        addTab(new Tab(R.string.tuesday, DayFitnessFragment.class));
        addTab(new Tab(R.string.wednesday, DayFitnessFragment.class));
        addTab(new Tab(R.string.thursday, DayFitnessFragment.class));
        addTab(new Tab(R.string.friday, DayFitnessFragment.class));
        addTab(new Tab(R.string.saturday, DayFitnessFragment.class));
        addTab(new Tab(R.string.sunday, DayFitnessFragment.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected LinearLayout inflateTitleLayout(String title) {
        return super.inflateTitleLayout(title);
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
