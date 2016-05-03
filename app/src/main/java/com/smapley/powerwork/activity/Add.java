package com.smapley.powerwork.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.smapley.powerwork.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by smapley on 15/11/17.
 */
@ContentView(R.layout.activity_add)
public class Add extends BaseActivity {

    @ViewInject(R.id.title_tv_name)
    private TextView title_tv_name;
    @ViewInject(R.id.title_iv_done)
    private ImageView title_iv_done;

    @ViewInject(R.id.add_ev_name)
    private EditText add_ev_name;
    @ViewInject(R.id.add_tv_name)
    private TextView add_tv_name;

    private int src = 0;

    @Override
    protected void initParams() {
        title_iv_done.setVisibility(View.VISIBLE);
        src = getIntent().getIntExtra("src", 0);
        switch (src) {
            case 1:
                title_tv_name.setText(R.string.addfolder);
                add_tv_name.setText(R.string.foldername);
                break;
            case 2:
                title_tv_name.setText(R.string.addproject);
                add_tv_name.setText(R.string.projectname);
                break;
        }

    }

    @Event({R.id.title_iv_back, R.id.title_iv_done})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_iv_back:
                finish();
                break;
            case R.id.title_iv_done:
                checkName();
                break;
        }
    }

    private void checkName() {
        String name = add_ev_name.getText().toString();
        if (name != null && !name.isEmpty()) {
            Intent intent = new Intent();
            intent.putExtra("name", name);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            showToast(R.string.namenull);
        }
    }
}
