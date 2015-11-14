package com.smapley.powerwork.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.smapley.powerwork.R;
import com.smapley.powerwork.adapter.TeamAdapter;
import com.smapley.powerwork.mode.BaseMode;
import com.smapley.powerwork.mode.Tea_Btn_Mode;
import com.smapley.powerwork.mode.Tea_Mem_Mode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smapley on 15/10/25.
 */
public class Team extends BaseFragment {

    @ViewInject(R.id.tea_tv_name)
    private TextView tea_tv_name;
    @ViewInject(R.id.tea_tv_number)
    private TextView tea_tv_number;
    @ViewInject(R.id.tea_rc_member)
    private RecyclerView tea_rc_member;
    @ViewInject(R.id.tea_rc_btns)
    private RecyclerView tea_rc_btns;
    @ViewInject(R.id.tea_rc_list)
    private RecyclerView tea_rc_list;

    private List<BaseMode> tea_btn_list;
    private List<BaseMode> tea_mem_list;

    private TeamAdapter tea_btn_adapter;
    private TeamAdapter tea_mem_adapter;

    @Override
    protected void initParams() {

        tea_rc_member.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        tea_rc_btns.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        tea_rc_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        tea_btn_list=new ArrayList<>();
        for (int i = 0; i <3 ; i++) {
            Tea_Btn_Mode tea_Btn_Mode=new Tea_Btn_Mode();
            tea_Btn_Mode.setItem(i);
            tea_btn_list.add(tea_Btn_Mode);
        }
        tea_btn_adapter=new TeamAdapter(getActivity(),tea_btn_list);
        tea_rc_btns.setAdapter(tea_btn_adapter);

        tea_mem_list=new ArrayList<>();
        for (int i = 0; i <3 ; i++) {
            Tea_Mem_Mode tea_Mem_Mode=new Tea_Mem_Mode();
            //tea_Mem_Mode.setPic_url(i);
            tea_mem_list.add(tea_Mem_Mode);
        }
        tea_mem_adapter=new TeamAdapter(getActivity(),tea_mem_list);
        tea_rc_member.setAdapter(tea_mem_adapter);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_team;
    }
}
