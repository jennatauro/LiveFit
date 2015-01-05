package com.jennatauro.livefit.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jennatauro.livefit.R;
import com.jennatauro.livefit.ui.activities.WorkoutViewPagerActivity;

/**
 * Created by jennatauro on 2015-01-04.
 */
public class ExerciseViewPagerFragment extends LiveFitFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = getActivity().getLayoutInflater().inflate(R.layout.fragment_exercise_viewpager, container, false);

        Bundle args = getArguments();
        ((TextView) root.findViewById(R.id.exercise_name)).setText(args.getString(WorkoutViewPagerActivity.VIEW_PAGER_TITLE_KEY));

        return root;
    }

    @Override
    public String getTitle() {
        return null;
    }
}
