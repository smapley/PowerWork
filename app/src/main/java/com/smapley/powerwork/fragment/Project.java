package com.smapley.powerwork.fragment;

import android.support.v7.widget.RecyclerView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.smapley.powerwork.R;

/**
 * Created by smapley on 15/10/25.
 */
public class Project extends BaseFragment {


    @ViewInject(R.id.pro_rv_list)
    private RecyclerView pro_rv_list;

    @Override
    protected void initParams() {


    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_project;
    }
}
