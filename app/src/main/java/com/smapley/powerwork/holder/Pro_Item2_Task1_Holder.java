package com.smapley.powerwork.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.smapley.powerwork.R;
import com.smapley.powerwork.entity.PTaskEntity;
import com.smapley.powerwork.utils.DateUtil;

/**
 * Created by smapley on 15/11/28.
 */
public class Pro_Item2_Task1_Holder extends BaseHolder {

    private TextView pro_item2_task1_tv_name;
    private TextView pro_item2_task1_tv_time;

    public Pro_Item2_Task1_Holder(View view) {
        super(view);
        pro_item2_task1_tv_name = (TextView) view.findViewById(R.id.pro_item2_task1_tv_name);
        pro_item2_task1_tv_time = (TextView) view.findViewById(R.id.pro_item2_task1_tv_time);
    }

    public void setData(Context context, PTaskEntity mode) {
        pro_item2_task1_tv_name.setText(mode.getName());
        pro_item2_task1_tv_time.setText(DateUtil.getDateString(mode.getTime(), DateUtil.formatDate));

    }

}
