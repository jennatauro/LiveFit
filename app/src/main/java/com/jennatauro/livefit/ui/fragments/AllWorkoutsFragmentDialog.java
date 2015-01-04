package com.jennatauro.livefit.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.jennatauro.livefit.LivefitApplication;
import com.jennatauro.livefit.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by jennatauro on 2015-01-04.
 */
public class AllWorkoutsFragmentDialog extends DialogFragment {

    public static final String FRAGMENT_TAG = "com.jenatauro.livefit.AllWorkoutsFragmentDialog";

    public static AllWorkoutsFragmentDialog newInstance() {
        return new AllWorkoutsFragmentDialog();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_all_workouts, container, false);
        ButterKnife.inject(this, view);

        LivefitApplication app = LivefitApplication.getApplication();
        app.inject(this);

        return view;
    }

    public static void displayAllWorkoutsDialog(FragmentManager fm) {
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag(FRAGMENT_TAG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = AllWorkoutsFragmentDialog.newInstance();
        newFragment.show(ft, FRAGMENT_TAG);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(getDialog() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }

}
