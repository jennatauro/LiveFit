package com.jennatauro.livefit.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jennatauro.livefit.R;

import java.util.ArrayList;
import java.util.List;

public abstract class AbsTabActivity extends ActionBarActivity{
    ViewPager viewPager;
    FragmentStatePagerAdapter adapter;
    ViewPager.SimpleOnPageChangeListener onPageChangeListener;
    ActionBar.TabListener tabListener;
    List<Tab> tabs = new ArrayList<Tab>();

    protected abstract void addTabs();

    protected void addTab(Tab tab) {
        tabs.add(tab);
    }

    protected static class Tab {
        final int titleId;
        final Class<? extends Fragment> clazz;

        public Tab(int titleId, Class<? extends Fragment> clazz) {
            this.titleId = titleId;
            this.clazz = clazz;
        }

        public String getTitle(Context context) {
            return context.getResources().getString(titleId).toUpperCase();
        }

        public Fragment getFragment() {
            try {
                return clazz.newInstance();
            } catch (InstantiationException e) {
                return null;
            } catch (IllegalAccessException e) {
                return null;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addTabs();

        viewPager = new ViewPager(this);
        viewPager.setId(R.id.view_pager);
        setContentView(viewPager);

        setupActionBarTabs();
        setupViewPager();
    }

    private void setupViewPager() {
        adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return tabs.size();
            }

            @Override
            public Fragment getItem(int i) {
                return tabs.get(i).getFragment();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return tabs.get(position).getTitle(AbsTabActivity.this);
            }
        };

        onPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                getActionBar().setSelectedNavigationItem(position);
            }
        };

        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(onPageChangeListener);
        viewPager.setCurrentItem(0);
    }

    private void setupActionBarTabs() {
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        addActionBarTabs(actionBar, getTabListener());
        if (tabs.size() > 2) {
            try {
                overrideDefaultMaxWidthOfActionBarTabs(actionBar);
            } catch (Exception e) {
                Log.d("error", "Failed to force action bar tabs to wrap_content", e);
            }
        }
    }


    private ActionBar.TabListener getTabListener() {
        if (tabListener == null) {
            tabListener = new ActionBar.TabListener() {
                @Override
                public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                    setTabColor(tab, true);
                    viewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                    setTabColor(tab, false);
                }

                @Override
                public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                }

                private void setTabColor(ActionBar.Tab tab, boolean selected) {
                    int colorId = selected ? R.color.text_color : R.color.text_color_unselected_tab;
                    ((TextView) ((LinearLayout) tab.getTag()).findViewById(R.id.tv_tab_title)).setTextColor(getResources().getColor(colorId));
                }
            };
        }
        return tabListener;
    }


    private void addActionBarTabs(ActionBar actionBar, ActionBar.TabListener listener) {
        for (Tab tab : tabs) {
            LinearLayout tabTitleLayout = inflateTitleLayout(tab.getTitle(this));
            addActionBarTab(actionBar, listener, tabTitleLayout);
        }
    }

    private void addActionBarTab(ActionBar actionBar, ActionBar.TabListener listener, LinearLayout tabTitleLayout) {
        ActionBar.Tab tab = actionBar.newTab();
        tab.setTag(tabTitleLayout);
        tab.setCustomView(tabTitleLayout);
        tab.setTabListener(listener);
        actionBar.addTab(tab);
    }

    private void overrideDefaultMaxWidthOfActionBarTabs(ActionBar actionBar) {
        if (actionBarIsCompatible()) {
            for (int i = 0; i < tabs.size(); i++) {
                View parent = (View) actionBar.getTabAt(i).getCustomView().getParent();
                parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
            }

            LinearLayout grandParent = (LinearLayout) actionBar.getTabAt(0).getCustomView().getParent().getParent();
            grandParent.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.MATCH_PARENT));
            grandParent.setMeasureWithLargestChildEnabled(false);

            View greatGrandParent = (View) grandParent.getParent();
            greatGrandParent.setLayoutParams(new FrameLayout.LayoutParams(View.MeasureSpec.UNSPECIFIED, FrameLayout.LayoutParams.MATCH_PARENT));
        }
    }

    private boolean actionBarIsCompatible() {
        ActionBar actionBar = getActionBar();
        if (actionBar.getTabCount() <= 0) {
            return false;
        }

        View tabView = actionBar.getTabAt(0).getCustomView();
        View parent = (tabView != null && tabView.getParent() != null) ? (View) tabView.getParent() : null;
        if (parent == null) {
            return false;
        }

        View grandParent = parent.getParent() != null ? (View) parent.getParent() : null;
        if (grandParent == null || !(grandParent instanceof LinearLayout)) {
            return false;
        }

        View greatGrandParent = grandParent.getParent() != null ? (View) grandParent.getParent() : null;
        if (greatGrandParent == null || !(greatGrandParent instanceof FrameLayout)) {
            return false;
        }

        View greatGreatGrandParent = greatGrandParent.getParent() != null ? (View) greatGrandParent.getParent() : null;
        if (greatGreatGrandParent == null || !(greatGreatGrandParent instanceof FrameLayout)) {
            return false;
        }

        return true;
    }

    protected LinearLayout inflateTitleLayout(String title) {
        LinearLayout tabTitleLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.tab_title, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER_VERTICAL;
        tabTitleLayout.setLayoutParams(lp);
        ((TextView) tabTitleLayout.findViewById(R.id.tv_tab_title)).setText(title);
        return tabTitleLayout;
    }
}
