package com.jennatauro.livefit.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jennatauro.livefit.R;
import com.jennatauro.livefit.data.db.DbHelper;
import com.jennatauro.livefit.data.models.Exercise;
import com.jennatauro.livefit.ui.activities.WorkoutViewPagerActivity;

import java.sql.SQLException;

import javax.inject.Inject;

/**
 * Created by jennatauro on 2015-01-04.
 */
public class ExerciseViewPagerFragment extends LiveFitFragment {

    public static final String EXTRA_EXERCISE_ID = "extra_exercise_id";

    int mExerciseId;
    Exercise mExercise;

    @Inject
    DbHelper mDbHelper;

    boolean mIsTimed = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = getActivity().getLayoutInflater().inflate(R.layout.fragment_exercise_viewpager, container, false);

        mExerciseId = getArguments().getInt(EXTRA_EXERCISE_ID);
        try {
            mExercise = mDbHelper.getExerciseForId(mExerciseId);
            ((TextView) root.findViewById(R.id.exercise_name)).setText(mExercise.getTitle());
            if(!mExercise.getDescription().equals("Not Set")) {
                ((TextView) root.findViewById(R.id.exercise_description)).setText(mExercise.getDescription());
                ((TextView) root.findViewById(R.id.exercise_description)).setVisibility(View.VISIBLE);
            }
            if(mExercise.getWeight() != 0) {
                ((TextView) root.findViewById(R.id.exercise_weight)).setText("Weight: " + mExercise.getWeight() + "lbs");
                ((TextView) root.findViewById(R.id.exercise_weight)).setVisibility(View.VISIBLE);
            }
            if(mExercise.getReps() != 0) {
                ((TextView) root.findViewById(R.id.exercise_reps)).setText("Reps: " + mExercise.getReps());
                ((TextView) root.findViewById(R.id.exercise_reps)).setVisibility(View.VISIBLE);
            }
            if(mExercise.getSeconds() != 0) {
                mIsTimed = true;
                ((TextView) root.findViewById(R.id.exercise_time)).setText("Seconds: " + mExercise.getSeconds());
                ((TextView) root.findViewById(R.id.exercise_time)).setVisibility(View.VISIBLE);
            }
        } catch (SQLException e) {
            Log.e(e.getMessage(), "Error getting exercise for id");
        }


        return root;
    }

    @Override
    public String getTitle() {
        return null;
    }
}
