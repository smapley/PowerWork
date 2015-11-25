package com.smapley.powerwork.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.smapley.powerwork.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by smapley on 15/11/16.
 */
@ContentView(R.layout.fragment_pro_item4)
public class Pro_Item4 extends BaseFragment {

    @ViewInject(R.id.title_tv_name)
    private TextView title_tv_name;
    @ViewInject(R.id.title_iv_edit)
    private ImageView title_iv_edit;
    @ViewInject(R.id.title_iv_add)
    private ImageView title_iv_add;


    @Override
    protected void initParams(View view) {
        title_tv_name.setText(R.string.team);
        title_iv_add.setVisibility(View.VISIBLE);
        title_iv_edit.setVisibility(View.VISIBLE);
    }

    @Event({R.id.title_iv_edit,R.id.title_iv_add})
    private void onClick(View view){
        switch (view.getId()){
            case R.id.title_iv_back:
                getActivity().finish();
                break;
            case R.id.title_iv_add:
                break;
            case R.id.title_iv_edit:
                break;
        }
    }
}
