package com.jennatauro.livefit.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jennatauro.livefit.R;
import com.jennatauro.livefit.ui.activities.AddWorkoutActivity;

/**
 * Created by jennatauro on 2014-10-09.
 */
public class SeeAllWorkoutsFragment extends LiveFitFragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_see_all_workouts, container, false);

        rootView.findViewById(R.id.add_workout_button).setOnClickListener(this);

        return rootView;
    }

    @Override
    public String getTitle() {
        return "All Workouts";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.add_workout_button): {
                Intent intent = new Intent(getActivity(), AddWorkoutActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
