package com.jennatauro.livefit.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.jennatauro.livefit.R;
import com.jennatauro.livefit.fragments.FridayFitnessFragment;
import com.jennatauro.livefit.fragments.MondayFitnessFragment;
import com.jennatauro.livefit.fragments.SaturdayFitnessFragment;
import com.jennatauro.livefit.fragments.SundayFitnessFragment;
import com.jennatauro.livefit.fragments.ThursdayFitnessFragment;
import com.jennatauro.livefit.fragments.TuesdayFitnessFragment;
import com.jennatauro.livefit.fragments.WednesdayFitnessFragment;

public class FitnessHome extends AbsTabActivity {

    public static void start(Context context) {
        final Intent intent = new Intent(context, FitnessHome.class);
        context.startActivity(intent);
    }

    @Override
    protected void addTabs() {
        addTab(new Tab(R.string.monday, MondayFitnessFragment.class));
        addTab(new Tab(R.string.tuesday, TuesdayFitnessFragment.class));
        addTab(new Tab(R.string.wednesday, WednesdayFitnessFragment.class));
        addTab(new Tab(R.string.thursday, ThursdayFitnessFragment.class));
        addTab(new Tab(R.string.friday, FridayFitnessFragment.class));
        addTab(new Tab(R.string.saturday, SaturdayFitnessFragment.class));
        addTab(new Tab(R.string.sunday, SundayFitnessFragment.class));
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
