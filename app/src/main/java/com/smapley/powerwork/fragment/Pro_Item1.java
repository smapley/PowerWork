package com.smapley.powerwork.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.smapley.powerwork.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by smapley on 15/11/16.
 */
@ContentView(R.layout.fragment_pro_item1)
public class Pro_Item1 extends BaseFragment {



    @ViewInject(R.id.pro_item1_rv_list)
    private RecyclerView pro_item1_rv_list;





    @Override
    protected void initParams(View view) {
        initData();
        getData();
        setView();


    }

    private void initData() {


    }

    private void setView() {

    }

    private void getData() {

    }

}
