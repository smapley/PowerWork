package com.smapley.powerwork.fragment;

import android.content.Intent;
import android.view.View;

import com.smapley.powerwork.R;
import com.smapley.powerwork.activity.AddTask;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

/**
 * Created by smapley on 15/11/16.
 */
@ContentView(R.layout.fragment_pro_item5)
public class Pro_Item5 extends BaseFragment {





    @Override
    protected void initParams(View view) {
    }

    @Override
    public void refresh() {

    }

    @Override
    public void getDataForWeb() {

    }

    @Event({R.id.pro_item5_tv_add})
    private void onClick(View view){
        switch (view.getId()){
            case R.id.pro_item5_tv_add:
                Intent intent=new Intent(getActivity(), AddTask.class);
                startActivity(intent);
                break;
        }
    }
}
