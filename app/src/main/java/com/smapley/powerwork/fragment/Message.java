package com.smapley.powerwork.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.smapley.powerwork.R;
import com.smapley.powerwork.adapter.PersonalAdapter;
import com.smapley.powerwork.mode.BaseMode;
import com.smapley.powerwork.mode.Cal_Task_Mode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smapley on 15/10/25.
 */
public class Message extends BaseFragment {

    @ViewInject(R.id.mes_rv_list)
    private RecyclerView mes_rv_list;

    private List<BaseMode> mes_lis_data;
    private PersonalAdapter mes_pa_adapter;

    @Override
    protected void initParams() {

        mes_rv_list.setLayoutManager(new LinearLayoutManager(getActivity()));

        mes_lis_data = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Cal_Task_Mode mode = new Cal_Task_Mode();
            mode.setName("画界面");
            mode.setTime("10 - 29  17:54");
            mes_lis_data.add(mode);
        }

        mes_pa_adapter = new PersonalAdapter(getActivity(), mes_lis_data);
        mes_rv_list.setAdapter(mes_pa_adapter);


    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }
}