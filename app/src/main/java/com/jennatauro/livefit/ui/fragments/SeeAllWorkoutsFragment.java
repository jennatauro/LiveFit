package com.jennatauro.livefit.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jennatauro.livefit.R;

/**
 * Created by jennatauro on 2014-10-09.
 */
public class SeeAllWorkoutsFragment extends LiveFitFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_see_all_workouts, container, false);

        return rootView;
    }

    @Override
    public String getTitle() {
        return "All Workouts";
    }
}
