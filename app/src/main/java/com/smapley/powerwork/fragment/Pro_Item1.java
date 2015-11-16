package com.smapley.powerwork.fragment;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.smapley.powerwork.R;

/**
 * Created by smapley on 15/11/16.
 */
public class Pro_Item1 extends BaseFragment {

    @ViewInject(R.id.pro_ct_layout)
    private CollapsingToolbarLayout pro_ct_layout;

    @ViewInject(R.id.pro_item1_tv_number)
    private TextView pro_item1_tv_number;
    @ViewInject(R.id.pro_item1_iv_member1)
    private ImageView pro_item1_iv_member1;
    @ViewInject(R.id.pro_item1_iv_member2)
    private ImageView pro_item1_iv_member2;
    @ViewInject(R.id.pro_item1_iv_member3)
    private ImageView pro_item1_iv_member3;
    @ViewInject(R.id.pro_item1_iv_member4)
    private ImageView pro_item1_iv_member4;
    @ViewInject(R.id.pro_item1_iv_member5)
    private ImageView pro_item1_iv_member5;
    @ViewInject(R.id.pro_item1_rv_list)
    private RecyclerView pro_item1_rv_list;


    private String name;
    private String number;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pro_item1;
    }

    @Override
    protected void initParams() {
        //使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上则不会显示
        pro_ct_layout.setExpandedTitleTextAppearance(R.style.pro_name_expanded);
        pro_ct_layout.setCollapsedTitleTextAppearance(R.style.per_name_collapsed);
        //通过CollapsingToolbarLayout修改字体颜色
        pro_ct_layout.setExpandedTitleColor(getResources().getColor(R.color.cal_text));//设置还没收缩时状态下字体颜色
        pro_ct_layout.setCollapsedTitleTextColor(getResources().getColor(R.color.cal_text));//设置收缩后Toolbar上字体的颜色

        getData();


    }

    private void getData() {
        name = "12软件工程";
        number = "48成员";
        initData();
    }

    private void initData() {
        pro_ct_layout.setTitle(name);
        pro_item1_tv_number.setText(number);

    }

    @OnClick(R.id.pro_item1_iv_member5)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pro_item1_iv_member5:

                break;
        }
    }
}
