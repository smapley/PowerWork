package com.smapley.powerwork.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.smapley.powerwork.R;
import com.smapley.powerwork.activity.AddTask;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by smapley on 15/11/16.
 */
@ContentView(R.layout.fragment_pro_item3)
public class Pro_Item3 extends BaseFragment {

    @ViewInject(R.id.title_tv_name)
    private TextView title_tv_name;
    @ViewInject(R.id.title_iv_add)
    private ImageView title_iv_add;


    @Override
    protected void initParams(View view) {
        title_tv_name.setText(R.string.task);
    }

    @Event({R.id.title_iv_add,R.id.title_iv_back})
    private void onClick(View view){
        switch (view.getId()){
            case R.id.title_iv_back:
                getActivity().finish();
                break;
            case R.id.title_iv_add:
                Intent intent=new Intent(getActivity(), AddTask.class);
                startActivity(intent);
                break;
        }
    }
}
