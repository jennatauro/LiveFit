package com.jennatauro.livefit.ui.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jennatauro.livefit.Constants;
import com.jennatauro.livefit.R;
import com.jennatauro.livefit.ui.fragments.LiveFitFragment;

import butterknife.InjectView;

/**
 * Created by jennatauro on 2014-10-09.
 */
public class FitnessActivity extends LiveFitActivity {

    private String[] mFragmentTitles;
    private ActionBarDrawerToggle mDrawerToggle;
    private LiveFitFragment.MenuOptions currentFragmentMenuOption;
    private String CURRENT_FRAGMENT = "current_fragment";
    private CharSequence mTitle;

    @InjectView(R.id.fitness_activity_toolbar)
    Toolbar mToolbar;

    @InjectView(R.id.fitness_activity_drawer_list)
    ListView mDrawerList;

    @InjectView(R.id.fitness_activity_drawer_layout)
    DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_fitness);
        super.onCreate(savedInstanceState);

        setSupportActionBar(mToolbar);

        mFragmentTitles = new String[]{Constants.NAV_TITLE_SEE_ALL_WORKOUTS, Constants.NAV_TITLE_SCHEDULE};
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(getResources().getString(R.string.fitness));
                invalidateOptionsMenu();
            }
        };

        //Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item_drawer, mFragmentTitles));

        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // set the default fragment to the first one, only if the screen was not rotated
        if (savedInstanceState == null) {
            selectItem(LiveFitFragment.MenuOptions.SEEALLWORKOUTS);
        } else {
            int previousFragmentValue = savedInstanceState.getInt(CURRENT_FRAGMENT);
            currentFragmentMenuOption = LiveFitFragment.MenuOptions.get(previousFragmentValue);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            LiveFitFragment.MenuOptions menuOptions = LiveFitFragment.MenuOptions.values()[position];
            selectItem(menuOptions);
        }
    }

    private void selectItem(LiveFitFragment.MenuOptions menuSelection) {
        if (currentFragmentMenuOption != null) {
            if (currentFragmentMenuOption == menuSelection) {
                mDrawerLayout.closeDrawer(mDrawerList);
                return;
            }
        }
        currentFragmentMenuOption = menuSelection;

        // Create a new fragment
        LiveFitFragment fragment = LiveFitFragment.getInstance(menuSelection);
        mTitle = fragment.getTitle();

        getSupportActionBar().setTitle(mTitle);

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.activity_fitness_content_frame, fragment)
                .commit();

        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(menuSelection.getCode(), true);

        mDrawerLayout.closeDrawer(mDrawerList);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }
}