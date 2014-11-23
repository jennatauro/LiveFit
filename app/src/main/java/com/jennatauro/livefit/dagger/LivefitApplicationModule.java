package com.jennatauro.livefit.dagger;

import android.accounts.AccountManager;
import android.content.Context;

import com.jennatauro.livefit.LivefitApplication;
import com.jennatauro.livefit.eventBus.MainThreadBus;
import com.jennatauro.livefit.ui.activities.AddWorkoutActivity;
import com.jennatauro.livefit.ui.activities.FitnessActivity;
import com.jennatauro.livefit.ui.activities.LiveFitActivity;
import com.jennatauro.livefit.ui.activities.MainActivity;
import com.jennatauro.livefit.ui.activities.WorkoutDetailsActivity;
import com.jennatauro.livefit.ui.adapters.ExerciseAdapter;
import com.jennatauro.livefit.ui.adapters.WorkoutAdapter;
import com.jennatauro.livefit.ui.dynamiclist.StableArrayAdapter;
import com.jennatauro.livefit.ui.fragments.LiveFitFragment;
import com.jennatauro.livefit.ui.fragments.SeeAllWorkoutsFragment;
import com.jennatauro.livefit.ui.fragments.WorkoutScheduleFragment;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jennatauro on 2014-11-22.
 */
@Module(
        injects = {
                LivefitApplication.class,
                MainActivity.class,
                LiveFitActivity.class,
                FitnessActivity.class,
                AddWorkoutActivity.class,
                LiveFitFragment.class,
                SeeAllWorkoutsFragment.class,
                WorkoutScheduleFragment.class,
                ExerciseAdapter.class,
                StableArrayAdapter.class,
                WorkoutDetailsActivity.class,
                WorkoutAdapter.class
        },
        includes = {
                DataModule.class,
                AdaptersModule.class
        },
        library = true,
        complete = false
)
public class LivefitApplicationModule {
    private final LivefitApplication app;
    public LivefitApplicationModule(LivefitApplication app) {
        this.app = app;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return app.getApplicationContext();
    }

    @Provides
    @Singleton
    LivefitApplication provideApplication() {
        return app;
    }

    @Provides
    @Singleton
    Bus providesOttoBus(){
        return new Bus();
    }

    @Provides
    MainThreadBus provideMainThreadBus(Bus bus) {
        return new MainThreadBus(bus);
    }

}
