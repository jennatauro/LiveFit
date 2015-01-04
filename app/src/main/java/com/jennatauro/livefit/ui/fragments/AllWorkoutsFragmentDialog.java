package com.jennatauro.livefit.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import com.jennatauro.livefit.LivefitApplication;
import com.jennatauro.livefit.R;
import com.jennatauro.livefit.data.db.DbHelper;
import com.jennatauro.livefit.data.models.Workout;
import com.jennatauro.livefit.data.models.WorkoutDayRelation;
import com.jennatauro.livefit.eventBus.events.WorkoutClickedEvent;
import com.jennatauro.livefit.eventBus.events.WorkoutDayRelationUpdateEvent;
import com.jennatauro.livefit.ui.activities.WorkoutDetailsActivity;
import com.jennatauro.livefit.ui.adapters.WorkoutAdapter;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by jennatauro on 2015-01-04.
 */
public class AllWorkoutsFragmentDialog extends DialogFragment {

    @Inject
    Bus bus;

    public static final String FRAGMENT_TAG = "com.jenatauro.livefit.AllWorkoutsFragmentDialog";
    private static final String EXTRA_DAY = "extra_day";

    WorkoutAdapter mAdapter;

    private DbHelper mDbHelper;
    private RecyclerView.LayoutManager mLayoutManager;
    private WorkoutsViewHolder mViewHolder;

    private List<Workout> mWorkouts;

    private int mDay;

    public static AllWorkoutsFragmentDialog newInstance(int day) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_DAY, day);

        AllWorkoutsFragmentDialog fragment = new AllWorkoutsFragmentDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_all_workouts, container, false);
        ButterKnife.inject(this, view);

        mAdapter = new WorkoutAdapter(false);

        LivefitApplication app = LivefitApplication.getApplication();
        app.inject(this);

        mDay = getArguments().getInt(EXTRA_DAY);

        mViewHolder = new WorkoutsViewHolder(view);

        mDbHelper = new DbHelper(getActivity());

        mLayoutManager = new LinearLayoutManager(getActivity());
        mViewHolder.mRecyclerView.setLayoutManager(mLayoutManager);

        mViewHolder.mRecyclerView.setAdapter(mAdapter);

        loadWorkouts();

        return view;
    }

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

    public static void displayAllWorkoutsDialog(FragmentManager fm, int day) {
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag(FRAGMENT_TAG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = AllWorkoutsFragmentDialog.newInstance(day);
        newFragment.show(ft, FRAGMENT_TAG);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(getDialog() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
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
        int workoutId = mWorkouts.get(e.getWorkoutPosition()).getDbId();
        createWorkoutDateRelation(workoutId);
        bus.post(new WorkoutDayRelationUpdateEvent());
        dismiss();
    }

    private void createWorkoutDateRelation(int workoutId) {
        try {
            WorkoutDayRelation workoutDayRelation = new WorkoutDayRelation();
            workoutDayRelation.setmDayId(mDay);
            workoutDayRelation.setmWorkoutId(workoutId);
            mDbHelper.createOrUpdateDayWorkoutRelation(workoutDayRelation);
        } catch (SQLException e) {
            Log.e(e.getMessage(), "Error creating workout day relation");
        }
    }

}
