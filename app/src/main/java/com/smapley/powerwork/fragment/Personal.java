package com.smapley.powerwork.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.smapley.powerwork.R;
import com.smapley.powerwork.adapter.PersonalAdapter;
import com.smapley.powerwork.mode.BaseMode;
import com.smapley.powerwork.mode.Per_Group_Mode;
import com.smapley.powerwork.mode.Per_Not_Pic_Mode;
import com.smapley.powerwork.mode.Per_Not_Text_Mode;
import com.smapley.powerwork.mode.Per_Not_Voice_Mode;
import com.smapley.powerwork.mode.Per_Not_Write_Mode;
import com.smapley.powerwork.mode.Per_Task_Details_Mode;
import com.smapley.powerwork.mode.Per_Task_Mode;
import com.smapley.powerwork.utils.DullPolish;

import java.util.ArrayList;
import java.util.List;

import me.nereo.multi_image_selector.bean.Image;

/**
 * Created by smapley on 15/10/25.
 */
public class Personal extends BaseFragment {

    @ViewInject(R.id.per_ct_layout)
    private CollapsingToolbarLayout per_ct_layout;
    @ViewInject(R.id.per_iv_pic)
    private ImageView per_iv_pic;
    @ViewInject(R.id.per_tb_bar)
    private Toolbar per_tb_bar;
    @ViewInject(R.id.per_rv_listview)
    private RecyclerView per_rv_listview;

    private List<BaseMode> per_list;
    private PersonalAdapter per_adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_personal;
    }

    @Override
    protected void initParams() {
        //使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上则不会显示
        per_ct_layout.setTitle(sp_user.getString("nickname", getString(R.string.app_name)));
        per_ct_layout.setExpandedTitleTextAppearance(R.style.per_name_expanded);
        per_ct_layout.setCollapsedTitleTextAppearance(R.style.per_name_collapsed);
        //通过CollapsingToolbarLayout修改字体颜色
        per_ct_layout.setExpandedTitleColor(getResources().getColor(R.color.cal_text));//设置还没收缩时状态下字体颜色
        per_ct_layout.setCollapsedTitleTextColor(getResources().getColor(R.color.cal_text));//设置收缩后Toolbar上字体的颜色
        initRecyclerView();
        initData();

        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.user_pic);
        per_iv_pic.setImageBitmap(DullPolish.doPolish(getActivity(),bitmap,20));
    }

    private void initRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        per_rv_listview.setLayoutManager(layoutManager);

    }

    private void initData() {
        per_list = new ArrayList<>();
        Per_Group_Mode per_group_mode1 = new Per_Group_Mode();
        per_group_mode1.setName("我的任务");
        per_group_mode1.setItem(1);
        per_list.add(per_group_mode1);
        for (int i = 0; i < 3; i++) {
            Per_Task_Mode per_task_mode = new Per_Task_Mode();
            per_task_mode.setName("画界面");
            per_task_mode.setTime("10-23");
            Per_Task_Details_Mode per_task_details_mode = new Per_Task_Details_Mode();
            per_task_details_mode.setName("12软件工程");
            per_task_details_mode.setTime("10 - 29  21:59");
            per_task_mode.setPer_task_details_mode(per_task_details_mode);
            per_list.add(per_task_mode);
        }


        Per_Group_Mode per_group_mode2 = new Per_Group_Mode();
        per_group_mode2.setName("我的记录");
        per_group_mode2.setItem(2);
        per_list.add(per_group_mode2);

        Per_Not_Voice_Mode Per_Not_Voice_Mode = new Per_Not_Voice_Mode();
        Per_Not_Voice_Mode.setLength("5");
        Per_Not_Voice_Mode.setTime("10-25");
        per_list.add(Per_Not_Voice_Mode);

        Per_Not_Text_Mode Per_Not_Text_Mode = new Per_Not_Text_Mode();
        Per_Not_Text_Mode.setName("*********");
        Per_Not_Text_Mode.setTime("10-26");
        per_list.add(Per_Not_Text_Mode);

        Per_Not_Pic_Mode Per_Not_Pic_Mode = new Per_Not_Pic_Mode();
        Per_Not_Pic_Mode.setName("图片1");
        Per_Not_Pic_Mode.setTime("10-26");
        per_list.add(Per_Not_Pic_Mode);

        Per_Not_Write_Mode per_Not_Write_Mode = new Per_Not_Write_Mode();
        per_Not_Write_Mode.setName("手写1");
        per_Not_Write_Mode.setTime("10-26");
        per_list.add(per_Not_Write_Mode);

        Per_Group_Mode per_group_mode3 = new Per_Group_Mode();
        per_group_mode3.setName("我的成就");
        per_group_mode3.setItem(3);
        per_list.add(per_group_mode3);
        Per_Group_Mode per_group_mode4 = new Per_Group_Mode();
        per_group_mode4.setName("设置");
        per_group_mode4.setItem(4);
        per_list.add(per_group_mode4);


        per_adapter = new PersonalAdapter(getActivity(), per_list);
        per_rv_listview.setAdapter(per_adapter);
    }

}
