package com.smapley.powerwork.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.smapley.powerwork.R;
import com.smapley.powerwork.mode.Tea_Btn_Mode;

/**
 * Created by smapley on 15/10/28.
 */
public class Tea_Btn_Holder extends BaseHolder {

    private ImageView tea_iv_btn_pic;
    private TextView tea_tv_btn_name;

    public Tea_Btn_Holder(View view) {
        super(view);
        tea_iv_btn_pic=(ImageView)view.findViewById(R.id.tea_iv_btn_pic);
        tea_tv_btn_name=(TextView)view.findViewById(R.id.tea_tv_btn_name);
    }

    public void setData(Tea_Btn_Mode mode){

        tea_iv_btn_pic.setImageResource(R.mipmap.ic_launcher);
        tea_tv_btn_name.setText("asdf");
    }
}
