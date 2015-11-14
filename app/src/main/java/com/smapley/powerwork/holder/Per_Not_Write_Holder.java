package com.smapley.powerwork.holder;

import android.view.View;
import android.widget.TextView;

import com.smapley.powerwork.R;
import com.smapley.powerwork.mode.Per_Not_Write_Mode;

/**
 * Created by smapley on 15/10/26.
 */
public class Per_Not_Write_Holder extends BaseHolder {

    private View contentView;
    private TextView per_tv_notice_wri_time;
    private TextView per_tv_notice_wri_name;

    public Per_Not_Write_Holder(View view) {
        super(view);
        contentView = view;
        per_tv_notice_wri_time = (TextView) view.findViewById(R.id.per_tv_notice_wri_time);
        per_tv_notice_wri_name = (TextView) view.findViewById(R.id.per_tv_notice_wri_name);
    }


    public void setData(Per_Not_Write_Mode mode) {
        per_tv_notice_wri_name.setText(mode.getName());
        per_tv_notice_wri_time.setText(mode.getTime());
    }
}
