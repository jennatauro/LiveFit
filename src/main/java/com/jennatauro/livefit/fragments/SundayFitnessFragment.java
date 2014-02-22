package com.jennatauro.livefit.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jennatauro.livefit.R;
import com.jennatauro.livefit.activities.WorkoutDetailsActivity;
import com.jennatauro.livefit.adapters.WorkoutsAdapter;
import com.jennatauro.livefit.models.Workout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jennatauro on 2/22/2014.
 */
public class SundayFitnessFragment extends Fragment implements AdapterView.OnItemClickListener {

    public static final String FRAGMENT_TAG = "fragment_monday_fitness";
    ListView listView;
    WorkoutsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        List<Workout> fakeworkouts = new ArrayList<Workout>();
        fakeworkouts.add(new Workout("workout7"));
        fakeworkouts.add(new Workout("workout7"));
        fakeworkouts.add(new Workout("workout7"));
        fakeworkouts.add(new Workout("workout7"));
        fakeworkouts.add(new Workout("workout7"));
        fakeworkouts.add(new Workout("workout7"));
        fakeworkouts.add(new Workout("workout7"));
        fakeworkouts.add(new Workout("workout7"));
        fakeworkouts.add(new Workout("workout7"));
        fakeworkouts.add(new Workout("workout7"));
        fakeworkouts.add(new Workout("workout7"));
        fakeworkouts.add(new Workout("workout7"));
        fakeworkouts.add(new Workout("workout7"));

        View rootView = inflater.inflate(R.layout.fragment_fitness_day, container, false);

        listView = (ListView) rootView.findViewById(R.id.lv_workouts);
        listView.setOnItemClickListener(this);

        adapter = new WorkoutsAdapter(getActivity(), R.layout.list_item_workouts, fakeworkouts);
        listView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Workout workout = adapter.getItem(i);
        WorkoutDetailsActivity.start(getActivity(), workout.getWorkoutname());
    }
}
