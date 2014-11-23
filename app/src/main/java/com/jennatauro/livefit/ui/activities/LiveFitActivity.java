package com.jennatauro.livefit.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.jennatauro.livefit.LivefitApplication;
import com.jennatauro.livefit.R;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by jennatauro on 2014-11-22.
 */
public class LiveFitActivity extends ActionBarActivity{

    @Inject
    Bus bus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);

        LivefitApplication app = LivefitApplication.getApplication();
        app.inject(this);

    }

    protected void onResume() {
        super.onResume();
        bus.register(this);
    }

    protected void onPause(){
        super.onPause();
        bus.unregister(this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }
}
