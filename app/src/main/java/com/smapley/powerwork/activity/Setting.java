package com.smapley.powerwork.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.smapley.powerwork.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

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

    @Event({ R.id.set_tv_account, R.id.set_tv_info, R.id.set_tv_feedback, R.id.set_tv_aboutus,R.id.title_iv_back})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_iv_back:
                finish();
                break;
            case R.id.set_tv_account:
                Intent intent = new Intent(Setting.this, Account.class);
                intent.putExtra("userId",userEntity.getUseId());
                intent.putExtra("edit",true);
                startActivity(intent);
                break;
            case R.id.set_tv_info:
                startActivity(new Intent(Setting.this, Notification.class));
                break;
            case R.id.set_tv_feedback:
                Intent intent1=new Intent(Setting.this, Feedback.class);
                intent1.putExtra("type",0);
                startActivity(intent1);
                break;
            case R.id.set_tv_aboutus:
                startActivity(new Intent(Setting.this, AboutUs.class));
                break;
        }
    }
}
