package com.smapley.powerwork.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.smapley.powerwork.R;
import com.smapley.powerwork.adapter.PersonalAdapter;
import com.smapley.powerwork.mode.Per_Task_Details_Mode;
import com.smapley.powerwork.mode.Per_Task_Mode;

/**
 * Created by smapley on 15/10/26.
 */
public class Per_Task_Details_Holder extends BaseHolder {

    private View contentView;
    private ImageView per_iv_task_state;
    private TextView per_tv_task_details_name;
    private TextView per_tv_task_details_time;

    public Per_Task_Details_Holder(View view) {
        super(view);
        contentView = view;
        per_iv_task_state = (ImageView) view.findViewById(R.id.per_iv_task_state);
        per_tv_task_details_time = (TextView) view.findViewById(R.id.per_tv_task_details_time);
        per_tv_task_details_name = (TextView) view.findViewById(R.id.per_tv_task_details_name);
    }

    public void setData(final Context context, final PersonalAdapter adapter, final Per_Task_Details_Mode mode, final int position) {
        per_tv_task_details_name.setText(mode.getName());
        per_tv_task_details_time.setText(mode.getTime());
    }
}
