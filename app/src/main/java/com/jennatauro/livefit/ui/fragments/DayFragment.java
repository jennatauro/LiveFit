package com.jennatauro.livefit.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jennatauro.livefit.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by jennatauro on 2015-01-03.
 */
public class DayFragment extends LiveFitFragment {

    public static final String EXTRA_DAY_OF_THE_WEEK = "extra_day_of_the_week";

    @InjectView(R.id.text)
    TextView text;

    public static DayFragment newInstance(int dayOfTheWeek) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_DAY_OF_THE_WEEK, dayOfTheWeek);

        DayFragment dayFragment = new DayFragment();
        dayFragment.setArguments(args);

        return dayFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int day = getArguments().getInt(EXTRA_DAY_OF_THE_WEEK);
        View rootView = getActivity().getLayoutInflater().inflate(R.layout.fragment_day, container, false);
        ButterKnife.inject(this, rootView);

        text.setText("day number " + day);

        return rootView;
    }

    @Override
    public String getTitle() {
        return null;
    }
}
