package com.jennatauro.livefit.dagger;

import android.app.Application;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.jennatauro.livefit.LivefitApplication;
import com.jennatauro.livefit.data.db.DbHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jennatauro on 2014-11-22.
 */
@Module(
        library = true,
        complete = false
)
public class DataModule {

    @Provides
    @Singleton
    DbHelper provideDbHelper(){
        return OpenHelperManager.getHelper(LivefitApplication.getApplication(), DbHelper.class);
    }
}
