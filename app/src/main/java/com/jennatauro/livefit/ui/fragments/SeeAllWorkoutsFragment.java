package com.jennatauro.livefit.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jennatauro.livefit.R;
import com.jennatauro.livefit.data.db.DbHelper;
import com.jennatauro.livefit.data.models.Workout;
import com.jennatauro.livefit.eventBus.events.WorkoutClickedEvent;
import com.jennatauro.livefit.ui.activities.AddWorkoutActivity;
import com.jennatauro.livefit.ui.activities.WorkoutDetailsActivity;
import com.jennatauro.livefit.ui.adapters.WorkoutAdapter;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * Created by jennatauro on 2014-10-09.
 */
public class SeeAllWorkoutsFragment extends LiveFitFragment {

    public static final String WORKOUT_ID = "workout_id";

    @Inject
    WorkoutAdapter mAdapter;

    private DbHelper mDbHelper;
    private RecyclerView.LayoutManager mLayoutManager;
    private WorkoutsViewHolder mViewHolder;

    private List<Workout> mWorkouts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = getActivity().getLayoutInflater().inflate(R.layout.fragment_see_all_workouts, container, false);
        ButterKnife.inject(this, rootView);
        mViewHolder = new WorkoutsViewHolder(rootView);

        mDbHelper = new DbHelper(getActivity());

        mLayoutManager = new LinearLayoutManager(getActivity());
        mViewHolder.mRecyclerView.setLayoutManager(mLayoutManager);

        mViewHolder.mRecyclerView.setAdapter(mAdapter);

        loadWorkouts();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadWorkouts();
    }

    private void loadWorkouts() {
        mWorkouts = mDbHelper.getWorkouts();
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

    @Override
    public String getTitle() {
        return "All Workouts";
    }

    @OnClick(R.id.add_workout_button)
    void addWorkout() {
        Intent intent = new Intent(getActivity(), AddWorkoutActivity.class);
        startActivity(intent);
    }


    static class WorkoutsViewHolder {

        @InjectView(R.id.all_workouts_recyclerview)
        RecyclerView mRecyclerView;

        @InjectView(R.id.no_workouts_yet_layout)
        LinearLayout noWorkoutsLayout;

        WorkoutsViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    @Subscribe
    public void workoutClicked(WorkoutClickedEvent e){
        Intent intent = new Intent(getActivity(), WorkoutDetailsActivity.class);
        int workoutId = mWorkouts.get(e.getWorkoutPosition()).getDbId();
        intent.putExtra(WORKOUT_ID, workoutId);
        startActivity(intent);
    }

}
