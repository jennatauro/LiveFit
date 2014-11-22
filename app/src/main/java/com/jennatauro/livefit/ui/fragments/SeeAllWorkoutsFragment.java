package com.jennatauro.livefit.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jennatauro.livefit.R;
import com.jennatauro.livefit.data.db.DbHelper;
import com.jennatauro.livefit.data.models.Workout;
import com.jennatauro.livefit.ui.activities.AddWorkoutActivity;
import com.jennatauro.livefit.ui.adapters.WorkoutAdapter;

import java.util.List;


/**
 * Created by jennatauro on 2014-10-09.
 */
public class SeeAllWorkoutsFragment extends LiveFitFragment implements View.OnClickListener {

    private WorkoutAdapter mAdapter;

    private RecyclerView mRecyclerView;

    private DbHelper mDbHelper;

    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = getActivity().getLayoutInflater().inflate(R.layout.fragment_see_all_workouts, container, false);

        mDbHelper = new DbHelper(getActivity());
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.all_workouts_recyclerview);
        mAdapter = new WorkoutAdapter();

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(mAdapter);

        rootView.findViewById(R.id.add_workout_button).setOnClickListener(this);

        loadWorkouts();

        return rootView;
    }

    private void loadWorkouts() {
        List<Workout> allWorkouts = mDbHelper.getWorkouts();
        mAdapter.replace(allWorkouts);
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
