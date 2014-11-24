package com.jennatauro.livefit.ui.activities;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.jennatauro.livefit.R;
import com.jennatauro.livefit.data.db.DbHelper;
import com.jennatauro.livefit.data.models.Exercise;
import com.jennatauro.livefit.data.models.Workout;
import com.jennatauro.livefit.eventBus.events.EditExerciseEvent;
import com.jennatauro.livefit.eventBus.events.ExerciseDeletedEvent;
import com.jennatauro.livefit.eventBus.events.SeeExerciseEvent;
import com.jennatauro.livefit.ui.dynamiclist.DynamicListView;
import com.jennatauro.livefit.ui.dynamiclist.StableArrayAdapter;
import com.squareup.otto.Subscribe;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by jennatauro on 2014-11-22.
 */
public class EditWorkoutActivity extends LiveFitActivity implements View.OnClickListener {

    @InjectView(R.id.activity_edit_workout_workout_name)
    EditText workoutNameEditText;

    @InjectView(R.id.activity_edit_workout_workout_description)
    EditText workoutDescriptionEditText;

    @Inject
    DbHelper mDbHelper;

    @InjectView(R.id.edit_workout_activity_toolbar)
    Toolbar mToolbar;

    private EditText exerciseNameEditText;
    private EditText exerciseDescriptionEditText;
    private EditText exerciseWeightEditText;
    private EditText exerciseRepsEditText;
    private EditText exerciseTimeEditText;
    private ArrayList<Exercise> mExercises = new ArrayList<Exercise>();
    private StableArrayAdapter mAdapter;
    private DynamicListView mListView;

    private Dialog createExerciseDialog;
    private Dialog editExerciseDialog;
    private Dialog seeExerciseDialog;
    private int editIndex;

    private Workout mWorkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_edit_workout);
        super.onCreate(savedInstanceState);

        int workoutId = getIntent().getIntExtra(WorkoutDetailsActivity.WORKOUT_ID, 0);
        try {
            mWorkout = mDbHelper.getWorkoutForId(workoutId);
            mExercises = (ArrayList<Exercise>) mDbHelper.getExercisesForWorkout(mWorkout);
        } catch (SQLException e) {
            mWorkout = null;
        }

        mAdapter = new StableArrayAdapter(this, R.layout.list_item_create_exercise, mExercises);
        mListView = (DynamicListView) findViewById(R.id.listview);

        mListView.setExerciseList(mExercises);
        mListView.setAdapter(mAdapter);
        mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);


        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.edit_workout));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        findViewById(R.id.edit_workout_button).setOnClickListener(this);
        findViewById(R.id.add_exercise_button).setOnClickListener(this);

        setupWorkout();

        displayExercises();
    }

    private void setupWorkout() {
        if(!mWorkout.getTitle().equals(getString(R.string.not_set))){
            workoutNameEditText.setText(mWorkout.getTitle());
        }
        if(!mWorkout.getDescription().equals(getString(R.string.not_set))){
            workoutDescriptionEditText.setText(mWorkout.getDescription());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayExercises();
    }

    private void displayExercises() {
        mAdapter.replace(mExercises);
        mListView.setExerciseList(mExercises);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void seeExercise(SeeExerciseEvent e) {
        Exercise exercise = e.getExerciseToSee();

        seeExerciseDialog = new Dialog(this);
        seeExerciseDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        seeExerciseDialog.setContentView(R.layout.dialog_see_exercise);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(seeExerciseDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        seeExerciseDialog.show();
        seeExerciseDialog.getWindow().setAttributes(lp);

        ((TextView) seeExerciseDialog.findViewById(R.id.exercise_name)).setText(exercise.getTitle());
        ((TextView) seeExerciseDialog.findViewById(R.id.dialog_exercise_description)).setText(exercise.getDescription());
        if (exercise.getWeight() == 0) {
            seeExerciseDialog.findViewById(R.id.title_weight).setVisibility(View.GONE);
            seeExerciseDialog.findViewById(R.id.dialog_exercise_weight).setVisibility(View.GONE);
        } else {
            seeExerciseDialog.findViewById(R.id.title_weight).setVisibility(View.VISIBLE);
            seeExerciseDialog.findViewById(R.id.dialog_exercise_weight).setVisibility(View.VISIBLE);
            ((TextView) seeExerciseDialog.findViewById(R.id.dialog_exercise_weight)).setText(exercise.getWeight() + "");
        }
        if (exercise.getReps() == 0) {
            seeExerciseDialog.findViewById(R.id.title_reps).setVisibility(View.GONE);
            seeExerciseDialog.findViewById(R.id.dialog_exercise_reps).setVisibility(View.GONE);
        } else {
            seeExerciseDialog.findViewById(R.id.title_reps).setVisibility(View.VISIBLE);
            seeExerciseDialog.findViewById(R.id.dialog_exercise_reps).setVisibility(View.VISIBLE);
            ((TextView) seeExerciseDialog.findViewById(R.id.dialog_exercise_reps)).setText(exercise.getReps() + "");
        }
        if (exercise.getSeconds() == 0) {
            seeExerciseDialog.findViewById(R.id.title_seconds).setVisibility(View.GONE);
            seeExerciseDialog.findViewById(R.id.dialog_exercise_seconds).setVisibility(View.GONE);
        } else {
            seeExerciseDialog.findViewById(R.id.title_seconds).setVisibility(View.VISIBLE);
            seeExerciseDialog.findViewById(R.id.dialog_exercise_seconds).setVisibility(View.VISIBLE);
            ((TextView) seeExerciseDialog.findViewById(R.id.dialog_exercise_seconds)).setText(exercise.getSeconds() + "");
        }
    }

    @Subscribe
    public void editExercise(EditExerciseEvent e) {
        //TODO NEED TO ACTUALLY EDIT IN DB
        Exercise exercise = e.getExerciseToEdit();
        editIndex = e.getIndex();

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        editExerciseDialog = new Dialog(this);
        editExerciseDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        editExerciseDialog.setContentView(R.layout.dialog_edit_exercise);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(editExerciseDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        editExerciseDialog.show();
        editExerciseDialog.getWindow().setAttributes(lp);

        editExerciseDialog.findViewById(R.id.edit_exercise_button).setOnClickListener(this);
        exerciseNameEditText = (EditText) editExerciseDialog.findViewById(R.id.dialog_exercise_name);
        exerciseDescriptionEditText = (EditText) editExerciseDialog.findViewById(R.id.dialog_exercise_description);
        exerciseWeightEditText = (EditText) editExerciseDialog.findViewById(R.id.dialog_exercise_weight);
        exerciseRepsEditText = (EditText) editExerciseDialog.findViewById(R.id.dialog_exercise_reps);
        exerciseTimeEditText = (EditText) editExerciseDialog.findViewById(R.id.dialog_exercise_seconds);

        if (!exercise.getTitle().equals(getString(R.string.not_set))) {
            exerciseNameEditText.setText(exercise.getTitle());
        }
        if (!exercise.getDescription().equals(getString(R.string.not_set))) {
            exerciseDescriptionEditText.setText(exercise.getDescription());
        }
        if (exercise.getWeight() != 0) {
            exerciseWeightEditText.setText(exercise.getWeight() + "");
        }
        if (exercise.getReps() != 0) {
            exerciseRepsEditText.setText(exercise.getReps() + "");
        }
        if (exercise.getSeconds() != 0) {
            exerciseTimeEditText.setText(exercise.getSeconds() + "");
        }
    }

    @Subscribe
    public void exerciseDeleted(ExerciseDeletedEvent e) {
        //TODO NEED TO ACTUALLY DELETE IN DB
        Exercise exercise = e.getDeletedExercise();
        mExercises.remove(exercise);
        displayExercises();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.edit_workout_button): {
                if (workoutNameEditText.getText().toString().equals("")) {
                    mWorkout.setTitle(getString(R.string.not_set));
                } else {
                    mWorkout.setTitle(workoutNameEditText.getText().toString());
                }
                if (workoutDescriptionEditText.getText().toString().equals("")) {
                    mWorkout.setDescription(getString(R.string.not_set));
                } else {
                    mWorkout.setDescription(workoutDescriptionEditText.getText().toString());
                }
                mWorkout.setExercises(mExercises);

                mDbHelper.createOrUpdateWorkoutWithExercises(mWorkout);
                finish();
            }
            case (R.id.add_exercise_button): {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                createExerciseDialog = new Dialog(this);
                createExerciseDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                createExerciseDialog.setContentView(R.layout.dialog_create_exercise);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(createExerciseDialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                createExerciseDialog.show();
                createExerciseDialog.getWindow().setAttributes(lp);

                createExerciseDialog.findViewById(R.id.create_exercise_button).setOnClickListener(this);
                exerciseNameEditText = (EditText) createExerciseDialog.findViewById(R.id.dialog_exercise_name);
                exerciseDescriptionEditText = (EditText) createExerciseDialog.findViewById(R.id.dialog_exercise_description);
                exerciseWeightEditText = (EditText) createExerciseDialog.findViewById(R.id.dialog_exercise_weight);
                exerciseRepsEditText = (EditText) createExerciseDialog.findViewById(R.id.dialog_exercise_reps);
                exerciseTimeEditText = (EditText) createExerciseDialog.findViewById(R.id.dialog_exercise_seconds);
                break;
            }
            case (R.id.create_exercise_button): {
                Exercise exercise = new Exercise();
                if (exerciseNameEditText.getText().toString().equals("")) {
                    exercise.setTitle(getString(R.string.not_set));
                } else {
                    exercise.setTitle(exerciseNameEditText.getText().toString());
                }
                if (exerciseDescriptionEditText.getText().toString().equals("")) {
                    exercise.setDescription(getString(R.string.not_set));
                } else {
                    exercise.setDescription(exerciseDescriptionEditText.getText().toString());
                }
                if (exerciseWeightEditText.getText().toString().equals("")) {
                    exercise.setWeight(0);
                } else {
                    exercise.setWeight(Integer.parseInt(exerciseWeightEditText.getText().toString()));
                }
                if (exerciseRepsEditText.getText().toString().equals("")) {
                    exercise.setReps(0);
                } else {
                    exercise.setReps(Integer.parseInt(exerciseRepsEditText.getText().toString()));
                }
                if (exerciseTimeEditText.getText().toString().equals("")) {
                    exercise.setSeconds(0);
                } else {
                    exercise.setSeconds(Integer.parseInt(exerciseTimeEditText.getText().toString()));
                }
                mExercises.add(exercise);
                displayExercises();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(exerciseNameEditText.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(exerciseDescriptionEditText.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(exerciseWeightEditText.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(exerciseRepsEditText.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(exerciseTimeEditText.getWindowToken(), 0);
                createExerciseDialog.dismiss();

                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                break;
            }
            case (R.id.edit_exercise_button): {
                Exercise exercise = new Exercise();
                if (exerciseNameEditText.getText().toString().equals("")) {
                    exercise.setTitle(getString(R.string.not_set));
                } else {
                    exercise.setTitle(exerciseNameEditText.getText().toString());
                }
                if (exerciseDescriptionEditText.getText().toString().equals("")) {
                    exercise.setDescription(getString(R.string.not_set));
                } else {
                    exercise.setDescription(exerciseDescriptionEditText.getText().toString());
                }
                if (exerciseWeightEditText.getText().toString().equals("")) {
                    exercise.setWeight(0);
                } else {
                    exercise.setWeight(Integer.parseInt(exerciseWeightEditText.getText().toString()));
                }
                if (exerciseRepsEditText.getText().toString().equals("")) {
                    exercise.setReps(0);
                } else {
                    exercise.setReps(Integer.parseInt(exerciseRepsEditText.getText().toString()));
                }
                if (exerciseTimeEditText.getText().toString().equals("")) {
                    exercise.setSeconds(0);
                } else {
                    exercise.setSeconds(Integer.parseInt(exerciseTimeEditText.getText().toString()));
                }
                mExercises.set(editIndex, exercise);
                displayExercises();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(exerciseNameEditText.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(exerciseDescriptionEditText.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(exerciseWeightEditText.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(exerciseRepsEditText.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(exerciseTimeEditText.getWindowToken(), 0);
                editExerciseDialog.dismiss();

                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                break;
            }
        }
    }
}
