package com.smapley.powerwork.activity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.smapley.powerwork.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by smapley on 15/10/24.
 */
@ContentView(R.layout.activity_aboutus)
public class AboutUs extends BaseActivity {

    @ViewInject(R.id.title_tv_name)
    private TextView title_tv_name;

    @ViewInject(R.id.abo_bt_good)
    private Button abo_bt_good;


    @Override
    protected void initParams() {
        title_tv_name.setText(R.string.aboutus);

    }

    @Event(value = {R.id.abo_bt_good,R.id.title_iv_back})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_iv_back:
                finish();
                break;
            case R.id.abo_bt_good:
                showToast("好评~");
                break;
        }
    }


}
