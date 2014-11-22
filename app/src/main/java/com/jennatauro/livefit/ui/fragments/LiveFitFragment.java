package com.jennatauro.livefit.ui.fragments;

import android.support.v4.app.Fragment;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jennatauro on 2014-10-09.
 */
public abstract class LiveFitFragment extends Fragment {

    public enum MenuOptions {
        HOME(0), SEEALLWORKOUTS(1), SCHEDULE(2);

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
}
