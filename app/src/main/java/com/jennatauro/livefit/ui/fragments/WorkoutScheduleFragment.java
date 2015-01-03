package com.jennatauro.livefit.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jennatauro.livefit.R;
import com.viewpagerindicator.IconPagerAdapter;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by jennatauro on 2014-10-09.
 */
public class WorkoutScheduleFragment extends LiveFitFragment {

    @InjectView(R.id.viewPager)
    ViewPager mViewPager;

    private LiveFitFragmentStatePagerAdapter mAdapter;
    private List<Tab> mTabs = new ArrayList<>();

    @InjectView(R.id.tabs)
    TabPageIndicator mTabPageIndicator;

    protected static class Tab {
        public static final int TYPE_MONDAY = 0;
        public static final int TYPE_TUESDAY = 1;
        public static final int TYPE_WEDNESDAY = 2;
        public static final int TYPE_THURSDAY = 3;
        public static final int TYPE_FRIDAY = 4;
        public static final int TYPE_SATURDAY = 5;
        public static final int TYPE_SUNDAY = 6;

        final int titleId;
        final int fragmentType;

        public Tab(int titleId, int fragmentType) {
            this.titleId = titleId;
            this.fragmentType = fragmentType;
        }

        public String getTitle(Context context) {
            return context.getResources().getString(titleId).toUpperCase();
        }

        public Fragment getFragment() {
            if(fragmentType >= 0 && fragmentType <=6) {
                return DayFragment.newInstance(fragmentType);
            } else {
                throw new UnsupportedOperationException();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        addTabs();
        View rootView = getActivity().getLayoutInflater().inflate(R.layout.fragment_workout_schedule, container, false);
        ButterKnife.inject(this, rootView);
        setupViewPager(savedInstanceState);
        mTabPageIndicator.setViewPager(mViewPager);


        //Padding is duplicated across outside layout and inside tabs without this line in code
        //Xml does not work - will cause duplicate padding
        mTabPageIndicator.setPadding(0, 0, 0, 0);

        return rootView;
    }

    protected void addTabs() {
        addTab(new Tab(R.string.monday, Tab.TYPE_MONDAY));
        addTab(new Tab(R.string.tuesday, Tab.TYPE_TUESDAY));
        addTab(new Tab(R.string.wednesday, Tab.TYPE_WEDNESDAY));
        addTab(new Tab(R.string.thursday, Tab.TYPE_THURSDAY));
        addTab(new Tab(R.string.friday, Tab.TYPE_FRIDAY));
        addTab(new Tab(R.string.saturday, Tab.TYPE_SATURDAY));
        addTab(new Tab(R.string.sunday, Tab.TYPE_SUNDAY));
    }

    protected void addTab(Tab tab) {
        mTabs.add(tab);
    }

    private void setupViewPager(Bundle savedInstanceState) {
        mAdapter = new LiveFitFragmentStatePagerAdapter(getChildFragmentManager());

        mViewPager.setAdapter(mAdapter);
        if(savedInstanceState == null){
            mViewPager.setCurrentItem(0);
        }
    }

    class LiveFitFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

        private Fragment currentFragment;

        public LiveFitFragmentStatePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mTabs.get(position).getFragment();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabs.get(position).getTitle(getActivity());
        }


        @Override
        public int getCount() {
            return mTabs.size();
        }

    }

    @Override
    public String getTitle() {
        return "Workout Schedule";
    }
}
