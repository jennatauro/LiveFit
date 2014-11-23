package com.jennatauro.livefit;

import android.app.Application;

import com.jennatauro.livefit.dagger.LivefitApplicationModule;
import com.squareup.otto.Subscribe;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.ObjectGraph;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by jennatauro on 2014-11-22.
 */
public class LivefitApplication extends Application {

    private static LivefitApplication mContext;

    private ObjectGraph graph;


    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault("fonts/ProximaNova-Light.otf", R.attr.fontPath);

        mContext = this;

        graph = ObjectGraph.create(getModules().toArray());
    }

    public static LivefitApplication getApplication() {
        return mContext;
    }

    protected List<Object> getModules() {

        return Arrays.asList(
                (Object) new LivefitApplicationModule(this)
        );
    }

    public ObjectGraph getObjectGraph() {
        return graph;
    }

    public void inject(Object object) {
        graph.inject(object);
    }

}
