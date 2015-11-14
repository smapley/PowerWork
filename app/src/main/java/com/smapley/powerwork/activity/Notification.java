package com.smapley.powerwork.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.smapley.powerwork.R;

/**
 * Created by smapley on 15/10/24.
 */
@ContentView(R.layout.activity_notification)
public class Notification extends BaseActivity {

    @ViewInject(R.id.title_tv_name)
    private TextView title_tv_name;

    @ViewInject(R.id.not_cb_type1)
    private CheckBox not_cb_type1;
    @ViewInject(R.id.not_cb_type2)
    private CheckBox not_cb_type2;
    @ViewInject(R.id.not_cb_type3)
    private CheckBox not_cb_type3;
    @ViewInject(R.id.not_cb_info1)
    private CheckBox not_cb_info1;

    @Override
    protected void initParams() {
        title_tv_name.setText(R.string.notification);

    }

    @OnClick({R.id.title_iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_iv_back:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor = sp_set.edit();
        editor.putBoolean("not_type1", not_cb_type1.isChecked());
        editor.putBoolean("not_type2", not_cb_type2.isChecked());
        editor.putBoolean("not_type3", not_cb_type3.isChecked());
        editor.putBoolean("not_info1", not_cb_info1.isChecked());
        editor.commit();
    }
}
