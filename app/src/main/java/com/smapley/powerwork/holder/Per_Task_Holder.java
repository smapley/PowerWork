package com.smapley.powerwork.holder;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.smapley.powerwork.R;
import com.smapley.powerwork.adapter.PersonalAdapter;
import com.smapley.powerwork.mode.Per_Task_Details_Mode;
import com.smapley.powerwork.mode.Per_Task_Mode;

import java.util.Map;

import me.nereo.multi_image_selector.bean.Image;

/**
 * Created by smapley on 15/10/26.
 */
public class Per_Task_Holder extends BaseHolder {

    private View contentView;
    private ImageView per_iv_task_state;
    private TextView per_tv_task_name;
    private ImageView per_iv_task_change;

    public Per_Task_Holder(View view) {
        super(view);
        contentView = view;
        per_iv_task_state = (ImageView) view.findViewById(R.id.per_iv_task_state);
        per_iv_task_change = (ImageView) view.findViewById(R.id.per_iv_task_change);
        per_tv_task_name = (TextView) view.findViewById(R.id.per_tv_task_name);
    }

    public void setData(final Context context, final PersonalAdapter adapter, final Per_Task_Mode mode, final int position) {
        if (mode.isCheck()) {
            per_iv_task_state.setImageResource(R.drawable.task_iv_checked_select);
            per_tv_task_name.setTextColor(context.getResources().getColor(R.color.gray_text));
        } else {
            per_iv_task_state.setImageResource(R.drawable.task_iv_select);
            //  per_tv_task_name.setTextColor(context.getResources().getColor(R.color.default_text));
        }
        per_tv_task_name.setText(mode.getName());
        changeVisible(mode.isOpen());


        per_iv_task_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mode.isCheck()) {
                    per_iv_task_state.setImageResource(R.drawable.task_iv_select);
                    //         per_tv_task_name.setTextColor(context.getResources().getColor(R.color.default_text));
                } else {
                    per_iv_task_state.setImageResource(R.drawable.task_iv_checked_select);
                    per_tv_task_name.setTextColor(context.getResources().getColor(R.color.gray_text));
                }
                mode.setCheck(!mode.isCheck());
            }
        });

        //展开详情页面
        per_tv_task_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mode.isOpen()) {
                    adapter.removeItem(position + 1);
                } else {
                    adapter.addItem(mode.getPer_task_details_mode(), position + 1);
                }
                mode.setIsOpen(!mode.isOpen());
                changeVisible(mode.isOpen());
            }
        });
    }

    private void changeVisible(boolean isOpen){
        if (isOpen) {
            per_iv_task_change.setVisibility(View.VISIBLE);
        } else {
            per_iv_task_change.setVisibility(View.GONE);
        }
    }
}
