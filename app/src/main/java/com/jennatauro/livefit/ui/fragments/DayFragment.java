package com.jennatauro.livefit.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jennatauro.livefit.R;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by jennatauro on 2015-01-03.
 */
public class DayFragment extends LiveFitFragment {

    public static final String EXTRA_DAY_OF_THE_WEEK = "extra_day_of_the_week";

    @InjectView(R.id.add_workout_to_day)
    Button addWorkoutButton;

    private static final HashMap<Integer, String> dayOfWeekMap = new HashMap<>();
    private int mDay;

    public static DayFragment newInstance(int dayOfTheWeek) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_DAY_OF_THE_WEEK, dayOfTheWeek);

        DayFragment dayFragment = new DayFragment();
        dayFragment.setArguments(args);

        dayOfWeekMap.put(1, "Monday");
        dayOfWeekMap.put(2, "Tuesday");
        dayOfWeekMap.put(3, "Wednesday");
        dayOfWeekMap.put(4, "Thursday");
        dayOfWeekMap.put(5, "Friday");
        dayOfWeekMap.put(6, "Saturday");
        dayOfWeekMap.put(7, "Sunday");

        return dayFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mDay = getArguments().getInt(EXTRA_DAY_OF_THE_WEEK);
        View rootView = getActivity().getLayoutInflater().inflate(R.layout.fragment_day, container, false);
        ButterKnife.inject(this, rootView);

        addWorkoutButton.setText("Add Workout to " + dayOfWeekMap.get(mDay));

        return rootView;
    }

    @OnClick(R.id.add_workout_to_day)
    public void addWorkoutToDay() {
        showDialogWithAllWorkouts();
    }

    private void showDialogWithAllWorkouts() {
        AllWorkoutsFragmentDialog.displayAllWorkoutsDialog(getFragmentManager(), mDay);
    }

    @Override
    public String getTitle() {
        return null;
    }
}
