package com.smapley.powerwork.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.smapley.powerwork.R;
import com.smapley.powerwork.entity.ProjectEntity;
import com.smapley.powerwork.fragment.BaseFragment;
import com.smapley.powerwork.fragment.Pro_Item1;
import com.smapley.powerwork.fragment.Pro_Item2;
import com.smapley.powerwork.fragment.Pro_Item3;
import com.smapley.powerwork.fragment.Pro_Item4;
import com.smapley.powerwork.fragment.Pro_Item5;

import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by smapley on 15/11/16.
 */
@ContentView(R.layout.activity_project)
public class Project extends BaseActivity {
    @ViewInject(R.id.pro_ct_layout)
    private CollapsingToolbarLayout pro_ct_layout;

    @ViewInject(R.id.pro_fl_content)
    private FrameLayout pro_fl_content;

    @ViewInject(R.id.pro_tv_btn_item1)
    private TextView pro_tv_btn_item1;
    @ViewInject(R.id.pro_tv_btn_item2)
    private TextView pro_tv_btn_item2;
    @ViewInject(R.id.pro_tv_btn_item3)
    private TextView pro_tv_btn_item3;
    @ViewInject(R.id.pro_tv_btn_item4)
    private TextView pro_tv_btn_item4;
    @ViewInject(R.id.pro_tv_btn_item5)
    private TextView pro_tv_btn_item5;
    @ViewInject(R.id.pro_iv_btn_image1)
    private ImageView pro_iv_btn_image1;
    @ViewInject(R.id.pro_iv_btn_image2)
    private ImageView pro_iv_btn_image2;
    @ViewInject(R.id.pro_iv_btn_image3)
    private ImageView pro_iv_btn_image3;
    @ViewInject(R.id.pro_iv_btn_image4)
    private ImageView pro_iv_btn_image4;
    @ViewInject(R.id.pro_iv_btn_image5)
    private ImageView pro_iv_btn_image5;

    private Pro_Item1 pro_item1;
    private Pro_Item2 pro_item2;
    private Pro_Item3 pro_item3;
    private Pro_Item4 pro_item4;
    private Pro_Item5 pro_item5;

    private List<BaseFragment> pro_lt_fragment;

    private FragmentManager fragmentManager;

    private Bundle bundle;
    private ProjectEntity projectEntity;
    private int pro_id;

    @Override
    protected void initParams() {
        //使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上则不会显示
        pro_ct_layout.setExpandedTitleTextAppearance(R.style.pro_name_expanded);
        pro_ct_layout.setCollapsedTitleTextAppearance(R.style.per_name_collapsed);
        //通过CollapsingToolbarLayout修改字体颜色
        pro_ct_layout.setExpandedTitleColor(getResources().getColor(R.color.cal_text));//设置还没收缩时状态下字体颜色
        pro_ct_layout.setCollapsedTitleTextColor(getResources().getColor(R.color.cal_text));//设置收缩后Toolbar上字体的颜色
        initData();
        initView();
        initFragment();
    }

    private void initView() {
        if (projectEntity != null) {
            pro_ct_layout.setTitle(projectEntity.getName());
        }
    }

    private void initFragment() {
        pro_lt_fragment = new ArrayList<>();
        pro_item1 = new Pro_Item1();
        pro_item2 = new Pro_Item2();
        pro_item3 = new Pro_Item3();
        pro_item4 = new Pro_Item4();
        pro_item5 = new Pro_Item5();
        pro_lt_fragment.add(pro_item1);
        pro_lt_fragment.add(pro_item2);
        pro_lt_fragment.add(pro_item3);
        pro_lt_fragment.add(pro_item4);
        pro_lt_fragment.add(pro_item5);
        for (BaseFragment fragment : pro_lt_fragment) {
            fragment.setArguments(bundle);
        }
        transactionTo(0);
    }

    private void initData() {
        bundle = getIntent().getExtras();
        pro_id = bundle.getInt("pro_id");
        try {
            projectEntity = dbUtils.findById(ProjectEntity.class, pro_id);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Event({R.id.pro_ll_btn_layout1, R.id.pro_ll_btn_layout2, R.id.pro_ll_btn_layout3, R.id.pro_ll_btn_layout4, R.id.pro_ll_btn_layout5})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.pro_ll_btn_layout1:
                changeView(0);
                transactionTo(0);
                break;
            case R.id.pro_ll_btn_layout2:
                changeView(1);
                transactionTo(1);
                break;
            case R.id.pro_ll_btn_layout3:
                changeView(2);
                transactionTo(2);
                break;
            case R.id.pro_ll_btn_layout4:
                changeView(3);
                transactionTo(3);
                break;
            case R.id.pro_ll_btn_layout5:
                changeView(4);
                transactionTo(4);
                break;
        }
    }

    private void changeView(int i) {
        pro_iv_btn_image1.setImageResource(R.mipmap.home);
        pro_tv_btn_item1.setTextColor(getResources().getColor(R.color.cal_text));
        pro_iv_btn_image2.setImageResource(R.mipmap.task);
        pro_tv_btn_item2.setTextColor(getResources().getColor(R.color.cal_text));
        pro_iv_btn_image3.setImageResource(R.mipmap.file);
        pro_tv_btn_item3.setTextColor(getResources().getColor(R.color.cal_text));
        pro_iv_btn_image4.setImageResource(R.mipmap.statistics);
        pro_tv_btn_item4.setTextColor(getResources().getColor(R.color.cal_text));
        pro_iv_btn_image5.setImageResource(R.mipmap.team);
        pro_tv_btn_item5.setTextColor(getResources().getColor(R.color.cal_text));

        switch (i) {
            case 0:
                pro_iv_btn_image1.setImageResource(R.mipmap.home_press);
                pro_tv_btn_item1.setTextColor(getResources().getColor(R.color.default_text));
                break;
            case 1:
                pro_iv_btn_image2.setImageResource(R.mipmap.task_press);
                pro_tv_btn_item2.setTextColor(getResources().getColor(R.color.default_text));
                break;
            case 2:
                pro_iv_btn_image3.setImageResource(R.mipmap.file_press);
                pro_tv_btn_item3.setTextColor(getResources().getColor(R.color.default_text));
                break;
            case 3:
                pro_iv_btn_image4.setImageResource(R.mipmap.statistics_press);
                pro_tv_btn_item4.setTextColor(getResources().getColor(R.color.default_text));
                break;
            case 4:
                pro_iv_btn_image5.setImageResource(R.mipmap.team_press);
                pro_tv_btn_item5.setTextColor(getResources().getColor(R.color.default_text));
                break;
        }

    }

    private void transactionTo(int position) {
        if (fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();
        }

        fragmentManager.beginTransaction().replace(R.id.pro_fl_content, pro_lt_fragment.get(position)).commit();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    pro_item3.addFolder(data.getStringExtra("name"));
                }
                break;
            case 2:
                //选择图片
                if (resultCode == RESULT_OK) {
                    List<String> resultList = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    pro_item3.addFile(1,resultList);
                }
                break;
        }
    }
}
