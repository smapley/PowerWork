package com.smapley.powerwork.holder;

import android.view.View;
import android.widget.TextView;

import com.smapley.powerwork.R;
import com.smapley.powerwork.mode.Per_Not_Text_Mode;

/**
 * Created by smapley on 15/10/26.
 */
public class Per_Not_Text_Holder extends BaseHolder {

    private View contentView;
    private TextView per_tv_notice_text;
    private TextView per_tv_notice_text_time;

    public Per_Not_Text_Holder(View view){
        super(view);
        per_tv_notice_text=(TextView)view.findViewById(R.id.per_tv_notice_text);
        per_tv_notice_text_time=(TextView)view.findViewById(R.id.per_tv_notice_text_time);
    }

    public void setData(Per_Not_Text_Mode  mode){
        per_tv_notice_text.setText(mode.getName());
        per_tv_notice_text_time.setText(mode.getTime());
    }
}
