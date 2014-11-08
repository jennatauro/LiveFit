package com.jennatauro.livefit.ui.activities;

import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jennatauro.livefit.Constants;
import com.jennatauro.livefit.R;
import com.jennatauro.livefit.ui.fragments.LiveFitFragment;

/**
 * Created by jennatauro on 2014-10-09.
 */
public class FitnessActivity extends FragmentActivity {

    private String[] mFragmentTitles;
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;

    private ActionBarDrawerToggle mDrawerToggle;
    private LiveFitFragment.MenuOptions currentFragmentMenuOption;
    private String CURRENT_FRAGMENT = "current_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness);

        mFragmentTitles = new String[]{Constants.NAV_TITLE_HOME, Constants.NAV_TITLE_SEE_ALL_WORKOUTS, Constants.NAV_TITLE_SCHEDULE};
        mDrawerList = (ListView) findViewById(R.id.fitness_activity_drawer_list);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.fitness_activity_drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_launcher, R.string.drawer_open, R.string.drawer_close) {

            // drawer has settled in a completely closed state
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            // drawer has settled in a completely opened state
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };

        //Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, mFragmentTitles));

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
        if (menuSelection != LiveFitFragment.MenuOptions.HOME) {

            // Create a new fragment
            LiveFitFragment fragment = LiveFitFragment.getInstance(menuSelection);

            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.activity_fitness_content_frame, fragment)
                    .commit();

            // Highlight the selected item, update the title, and close the drawer
            mDrawerList.setItemChecked(menuSelection.getCode(), true);

            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            finish();
        }
    }
}
