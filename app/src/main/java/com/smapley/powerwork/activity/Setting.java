package com.smapley.powerwork.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.smapley.powerwork.R;

/**
 * Created by smapley on 15/10/24.
 */
@ContentView(R.layout.activity_setting)
public class Setting extends BaseActivity {

    @ViewInject(R.id.title_tv_name)
    private TextView title_tv_name;

    @ViewInject(R.id.set_tv_account)
    private TextView set_tv_account;
    @ViewInject(R.id.set_tv_info)
    private TextView set_tv_info;
    @ViewInject(R.id.set_tv_feedback)
    private TextView set_tv_feedback;
    @ViewInject(R.id.set_tv_aboutus)
    private TextView set_tv_aboutus;


    @Override
    protected void initParams() {
        title_tv_name.setText(R.string.setting);

    }

    @OnClick({ R.id.set_tv_account, R.id.set_tv_info, R.id.set_tv_feedback, R.id.set_tv_aboutus,R.id.title_iv_back})
    public void viewOnClick(View view) {
        switch (view.getId()) {
            case R.id.title_iv_back:
                finish();
                break;
            case R.id.set_tv_account:
                startActivity(new Intent(Setting.this, Account.class));
                break;
            case R.id.set_tv_info:
                startActivity(new Intent(Setting.this, Notification.class));
                break;
            case R.id.set_tv_feedback:
                startActivity(new Intent(Setting.this, Feedback.class));
                break;
            case R.id.set_tv_aboutus:
                startActivity(new Intent(Setting.this, AboutUs.class));
                break;
        }
    }
}
