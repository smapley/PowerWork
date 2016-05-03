package com.smapley.powerwork.activity;

import android.content.Intent;

import com.smapley.powerwork.R;
import com.smapley.powerwork.utils.ThreadSleep;

import org.xutils.view.annotation.ContentView;

/**
 * Created by smapley on 15/12/21.
 */
@ContentView(R.layout.activity_welcom)
public class Welcom extends BaseActivity {
    @Override
    protected void initParams() {
        new ThreadSleep().sleep(2000, new ThreadSleep.Callback() {
            @Override
            public void onCallback(int number) {
                startActivity(new Intent(Welcom.this,MainActivity.class));
                finish();
            }
        });
    }
}
