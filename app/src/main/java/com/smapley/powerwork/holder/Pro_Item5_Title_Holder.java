package com.smapley.powerwork.holder;

import android.view.View;
import android.widget.TextView;

import com.smapley.powerwork.R;
import com.smapley.powerwork.mode.Pro_Item5_Title_Mode;

/**
 * Created by smapley on 15/11/17.
 */
public class Pro_Item5_Title_Holder extends BaseHolder {
    private TextView pro_item5_title_tv_name;

    public Pro_Item5_Title_Holder(View view) {
        super(view);
        pro_item5_title_tv_name = (TextView) view.findViewById(R.id.pro_item5_title_tv_name);
    }

    public void setData(Pro_Item5_Title_Mode mode) {
        pro_item5_title_tv_name.setText(mode.getName());
    }
}
