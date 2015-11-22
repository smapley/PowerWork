package com.smapley.powerwork.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.smapley.powerwork.R;
import com.smapley.powerwork.adapter.PersonalAdapter;
import com.smapley.powerwork.mode.BaseMode;
import com.smapley.powerwork.mode.Cal_Task_Mode;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smapley on 15/10/29.
 */
@ContentView(R.layout.activity_task)
public class Task extends BaseActivity {

    @ViewInject(R.id.title_tv_name)
    private TextView title_tv_name;

    @ViewInject(R.id.tas_rv_list)
    private RecyclerView tas_rv_list;

    private List<BaseMode> tas_lis_data;
    private PersonalAdapter tas_pa_adapter;

    @Override
    protected void initParams() {

        title_tv_name.setText(R.string.task);
        tas_rv_list.setLayoutManager(new LinearLayoutManager(this));

        tas_lis_data = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            Cal_Task_Mode cal_task_mode = new Cal_Task_Mode();
            cal_task_mode.setName("画界面");
            cal_task_mode.setTime("10-23");
            tas_lis_data.add(cal_task_mode);
        }


        tas_pa_adapter = new PersonalAdapter(this, tas_lis_data);
        tas_rv_list.setAdapter(tas_pa_adapter);

    }

    @Event({R.id.title_iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_iv_back:
                finish();
                break;
        }
    }
}
