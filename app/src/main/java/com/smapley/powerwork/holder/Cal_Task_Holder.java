package com.smapley.powerwork.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.smapley.powerwork.R;
import com.smapley.powerwork.db.entity.TaskEntity;
import com.smapley.powerwork.utils.DateUtil;

/**
 * Created by smapley on 15/10/26.
 */
public class Cal_Task_Holder extends BaseHolder {

    private View contentView;
    private ImageView cal_iv_task_state;
    private TextView cal_tv_task_name;
    private TextView cal_tv_task_time;

    public Cal_Task_Holder(View view) {
        super(view);
        contentView = view;
        cal_iv_task_state = (ImageView) view.findViewById(R.id.cal_iv_task_state);
        cal_tv_task_time = (TextView) view.findViewById(R.id.cal_tv_task_time);
        cal_tv_task_name = (TextView) view.findViewById(R.id.cal_tv_task_name);
    }

    public void setData(final Context context, final TaskEntity mode) {
        if(mode.getProgress()==100){
            cal_iv_task_state.setImageResource(R.drawable.task_iv_checked_select);
            cal_tv_task_name.setTextColor(context.getResources().getColor(R.color.gray_text));
        }else {
            cal_iv_task_state.setImageResource(R.drawable.task_iv_select);
            cal_tv_task_name.setTextColor(context.getResources().getColor(R.color.default_text));
        }
        cal_tv_task_name.setText(mode.getName());
        cal_tv_task_time.setText(DateUtil.getDateString(mode.getEnd_date(),DateUtil.formatDateAndTime));

        cal_iv_task_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mode.getProgress()==100){
                    cal_iv_task_state.setImageResource(R.drawable.task_iv_select);
                    cal_tv_task_name.setTextColor(context.getResources().getColor(R.color.default_text));
                    mode.setProgress(0);
                }else {
                    cal_iv_task_state.setImageResource(R.drawable.task_iv_checked_select);
                    cal_tv_task_name.setTextColor(context.getResources().getColor(R.color.gray_text));
                    mode.setProgress(100);
                }
            }
        });
    }


}
