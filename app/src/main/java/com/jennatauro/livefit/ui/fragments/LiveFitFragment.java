package com.jennatauro.livefit.ui.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.jennatauro.livefit.LivefitApplication;
import com.squareup.otto.Bus;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by jennatauro on 2014-10-09.
 */
public abstract class LiveFitFragment extends Fragment {

    @Inject
    Bus bus;

    public enum MenuOptions {
        SEEALLWORKOUTS(0), SCHEDULE(1);

        private int code;

        private MenuOptions(int value){
            this.code = value;
        }
        private static final Map<Integer,MenuOptions> lookup
                = new HashMap<Integer,MenuOptions>();

        static {
            for(MenuOptions s : EnumSet.allOf(MenuOptions.class))
                lookup.put(s.getCode(), s);
        }

        public int getCode() { return this.code; }

        public static MenuOptions get(int value){
            return lookup.get(value);
        }
    }

    public static LiveFitFragment getInstance(MenuOptions menuSelection){
        switch(menuSelection) {
            case SEEALLWORKOUTS:
                return new SeeAllWorkoutsFragment();
            case SCHEDULE:
                return new WorkoutScheduleFragment();
            default:
                return null;
        }
    }

    public abstract String getTitle();

    @Override
    public void onResume() {
        super.onResume();
        bus.register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        bus.unregister(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        LivefitApplication app = LivefitApplication.getApplication();
        app.inject(this);
    }

}
