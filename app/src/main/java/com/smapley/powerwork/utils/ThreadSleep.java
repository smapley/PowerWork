package com.smapley.powerwork.utils;

import android.os.Handler;
import android.os.Message;

import java.security.PublicKey;

/**
 * Created by smapley on 15/11/18.
 */
public class ThreadSleep {

    private final int SLEEP = 1;
    private boolean mIsLoops = false;
    private Callback mcallback;
    private int mNumber = 0;


    public ThreadSleep() {

    }

    public boolean ismIsLoops() {
        return mIsLoops;
    }

    public void setmIsLoops(boolean mIsLoops) {
        this.mIsLoops = mIsLoops;
    }

    public void sleep(final long time, Callback callback) {
        mcallback = callback;
        mNumber = 0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mhandler.obtainMessage(SLEEP).sendToTarget();
                    if (mIsLoops == false)
                        return;
                }
            }
        }).start();
    }

    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SLEEP:
                    mNumber++;
                    mcallback.onCallback(mNumber);
                    break;
            }
        }
    };

    public interface Callback {
        void onCallback(int number);
    }
}
