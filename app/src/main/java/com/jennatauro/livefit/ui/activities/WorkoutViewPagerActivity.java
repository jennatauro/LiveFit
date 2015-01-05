package com.jennatauro.livefit.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.jennatauro.livefit.R;
import com.jennatauro.livefit.data.db.DbHelper;
import com.jennatauro.livefit.data.models.Exercise;
import com.jennatauro.livefit.data.models.Workout;
import com.jennatauro.livefit.ui.fragments.ExerciseViewPagerFragment;
import com.viewpagerindicator.CirclePageIndicator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;

/**
 * Created by jennatauro on 2015-01-04.
 */
public class WorkoutViewPagerActivity extends LiveFitActivity {

    public static final String EXTRA_WORKOUT_ID = "extra_workout_id";
    public final static String VIEW_PAGER_TITLE_KEY = "view_pager_title";

    @InjectView(R.id.exercise_view_pager)
    ViewPager mViewPager;

    @InjectView(R.id.exericise_indicator)
    CirclePageIndicator mIndicator;

    @InjectView(R.id.workout_view_pager_toolbar)
    Toolbar mToolbar;

    @Inject
    DbHelper mDbHelper;

    int mWorkoutId;

    Workout mWorkout;

    private FragmentStatePagerAdapter mAdapter;
    private List<ViewPagerPage> mPages = new ArrayList<>();

    protected static class ViewPagerPage {
        final int exerciseId;
        final Class<? extends Fragment> clazz;

        public ViewPagerPage(int exerciseId, Class<? extends Fragment> clazz) {
            this.exerciseId = exerciseId;
            this.clazz = clazz;
        }

        public int getExerciseId() {
            return exerciseId;
        }

        public Fragment getFragment() {
            try {
                return clazz.newInstance();
            } catch (Fragment.InstantiationException e) {
                return null;
            } catch (IllegalAccessException e) {
                return null;
            } catch (java.lang.InstantiationException e) {
                return null;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_workout_viewpager);
        super.onCreate(savedInstanceState);

        mWorkoutId = getIntent().getIntExtra(EXTRA_WORKOUT_ID, 0);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        try {
            mWorkout = mDbHelper.getWorkoutForId(mWorkoutId);
        } catch (SQLException e) {
            Log.e(e.getMessage(), "Error getting workout for id");
        }

        getSupportActionBar().setTitle(mWorkout.getTitle());

        addViewPagerPages();
        setupViewPager(savedInstanceState);
    }

    protected void addViewPagerPages() {
        for (Exercise exercise : mWorkout.getExercises()) {
            addPage(new ViewPagerPage(exercise.getDbId(), ExerciseViewPagerFragment.class));
        }
    }

    protected void addPage(ViewPagerPage page) {
        mPages.add(page);
    }

    private void setupViewPager(Bundle savedInstanceState) {
        mAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mPages.size();
            }

            @Override
            public Fragment getItem(int i) {
                Fragment fragment = mPages.get(i).getFragment();
                Bundle args = new Bundle();
                args.putInt(ExerciseViewPagerFragment.EXTRA_EXERCISE_ID, mPages.get(i).getExerciseId());
                fragment.setArguments(args);
                return fragment;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return null;
            }
        };

        mViewPager.setAdapter(mAdapter);

        if (savedInstanceState == null) {
            mViewPager.setCurrentItem(0);
        }

        mIndicator.setViewPager(mViewPager);
        mIndicator.setFillColor(getResources().getColor(R.color.toolbar_blue));
        mIndicator.setStrokeColor(getResources().getColor(R.color.black));
        mIndicator.setPageColor(getResources().getColor(R.color.main_grey_background));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
