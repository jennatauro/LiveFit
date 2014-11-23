package com.jennatauro.livefit.eventBus;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;

/**
 * Created by jennatauro on 2014-11-22.
 */
public class MainThreadBus extends Bus {
    private Bus mBus;
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    public MainThreadBus(final Bus bus) {
        if (bus == null) {
            throw new NullPointerException("bus must not be null");
        }
        this.mBus = bus;
    }

    @Override
    public void register(Object obj) {
        mBus.register(obj);
    }

    @Override
    public void unregister(Object obj) {
        mBus.unregister(obj);
    }

    @Override
    public void post(final Object event) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            mBus.post(event);
        } else {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mBus.post(event);
                }
            });
        }
    }
}

