package com.jennatauro.livefit.ui.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.jennatauro.livefit.R;
import com.jennatauro.livefit.data.db.DbHelper;
import com.jennatauro.livefit.data.models.Workout;
import com.jennatauro.livefit.data.models.WorkoutDayRelation;
import com.jennatauro.livefit.eventBus.events.WorkoutClickedEvent;
import com.jennatauro.livefit.eventBus.events.WorkoutDayRelationUpdateEvent;
import com.jennatauro.livefit.ui.adapters.WorkoutAdapter;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

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

    @Inject
    WorkoutAdapter mAdapter;

    private DbHelper mDbHelper;
    private RecyclerView.LayoutManager mLayoutManager;
    private WorkoutsViewHolder mViewHolder;

    private List<Workout> mWorkouts;

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

        mViewHolder = new WorkoutsViewHolder(rootView);

        mDbHelper = new DbHelper(getActivity());

        mLayoutManager = new LinearLayoutManager(getActivity());
        mViewHolder.mRecyclerView.setLayoutManager(mLayoutManager);

        mViewHolder.mRecyclerView.setAdapter(mAdapter);
        
        loadWorkouts();

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

    @Subscribe
    public void workoutDayRelationUpdate(WorkoutDayRelationUpdateEvent e){
        loadWorkouts();
    }

    private void loadWorkouts() {
        mWorkouts = mDbHelper.getWorkoutsForDay(mDay);
        if (mWorkouts == null || mWorkouts.size() == 0) {
            mViewHolder.noWorkoutsLayout.setVisibility(View.VISIBLE);
            mViewHolder.mRecyclerView.setVisibility(View.GONE);
            mAdapter.replace(new ArrayList<Workout>());
        } else {
            mViewHolder.noWorkoutsLayout.setVisibility(View.GONE);
            mViewHolder.mRecyclerView.setVisibility(View.VISIBLE);
            mAdapter.replace(mWorkouts);
        }
    }

    static class WorkoutsViewHolder {

        @InjectView(R.id.day_workouts_recyclerview)
        RecyclerView mRecyclerView;

        @InjectView(R.id.no_workouts_for_this_day_yet_layout)
        LinearLayout noWorkoutsLayout;

        WorkoutsViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
