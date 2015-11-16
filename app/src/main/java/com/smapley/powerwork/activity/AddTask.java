package com.smapley.powerwork.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Scene;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.smapley.powerwork.R;
import com.smapley.powerwork.adapter.AddTaskAdapter;
import com.smapley.powerwork.mode.Add_Menu_Mode;
import com.smapley.powerwork.mode.Add_Text_Mode;
import com.smapley.powerwork.mode.BaseMode;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by smapley on 15/10/24.
 */
@ContentView(R.layout.activity_addtask)
public class AddTask extends BaseActivity {
    @ViewInject(R.id.title_tv_name)
    private TextView title_tv_name;

    @ViewInject(R.id.add_rv_list)
    private RecyclerView add_rv_list;

    private List<BaseMode> add_lis_data;
    private AddTaskAdapter add_aa_adapter;

    private int TYPE_NOW = -1;

    @Override
    protected void initParams() {
        title_tv_name.setText(R.string.addtask);

        add_rv_list.setLayoutManager(new LinearLayoutManager(this));
        add_lis_data = new ArrayList<>();
        add_lis_data.add(new Add_Text_Mode());
        add_lis_data.add(new Add_Menu_Mode());
        add_aa_adapter = new AddTaskAdapter(this, add_lis_data);
        add_rv_list.setAdapter(add_aa_adapter);


        doJump();


    }


    @OnClick({R.id.title_iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_iv_back:
                finish();
                break;
        }
    }

    /**
     * 跳转到相应界面
     */
    public void doJump() {
        TYPE_NOW = getIntent().getIntExtra("type", -1);
        showToast(TYPE_NOW + "");
        switch (TYPE_NOW) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
        }
    }
}
