package com.jennatauro.livefit.fragments;

import android.database.SQLException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jennatauro.livefit.R;
import com.jennatauro.livefit.activities.CreateNewWorkoutActivity;
import com.jennatauro.livefit.activities.WorkoutDetailsActivity;
import com.jennatauro.livefit.adapters.WorkoutsAdapter;
import com.jennatauro.livefit.models.Exercise;
import com.jennatauro.livefit.models.Workout;
import com.jennatauro.livefit.util.WorkoutDataSource;

import java.util.ArrayList;
import java.util.List;

public class DayFitnessFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    ListView listView;
    WorkoutsAdapter adapter;

    List<Workout> workouts;

    private WorkoutDataSource dataSource;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fitness_day, container, false);

        try {
            dataSource = new WorkoutDataSource(getActivity());
            dataSource.open();
        } catch (SQLException e) {
            Log.d("OPENINGDATABASEEXCEPTION", "OPENINGDATABASEEXCEPTION " + e.getMessage());
            e.printStackTrace();
        }

        workouts = dataSource.getAllWorkouts();

        listView = (ListView) rootView.findViewById(R.id.lv_workouts);
        listView.setOnItemClickListener(this);

        adapter = new WorkoutsAdapter(getActivity(), R.layout.list_item_workouts, workouts, dataSource);
        listView.setAdapter(adapter);

        rootView.findViewById(R.id.iv_add_workout).setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        workouts.clear();
        workouts.addAll(dataSource.getAllWorkouts());
        Log.d("Workouts Size", workouts.size() + "");
        Log.d("Database Size", dataSource.getAllWorkouts().size() + "");
        adapter.notifyDataSetChanged();
        Log.d("Adapter Size", adapter.getCount() + "");
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Workout workout = adapter.getItem(i);
        WorkoutDetailsActivity.start(getActivity(), workout.getWorkoutname(), (ArrayList<Exercise>) workout.getExercises());
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.iv_add_workout:
                CreateNewWorkoutActivity.start(getActivity());
            break;
        }
    }
}
