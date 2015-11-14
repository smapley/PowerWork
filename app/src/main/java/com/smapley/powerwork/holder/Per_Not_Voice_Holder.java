package com.smapley.powerwork.holder;

import android.view.View;
import android.widget.TextView;

import com.smapley.powerwork.R;
import com.smapley.powerwork.mode.Per_Not_Voice_Mode;

/**
 * Created by smapley on 15/10/26.
 */
public class Per_Not_Voice_Holder extends BaseHolder {

    private View contentView;
    private TextView per_tv_notice_voice_length;
    private TextView per_tv_notice_voice_time;

    public Per_Not_Voice_Holder(View view) {
        super(view);
        per_tv_notice_voice_length = (TextView) view.findViewById(R.id.per_tv_notice_voice_length);
        per_tv_notice_voice_time = (TextView) view.findViewById(R.id.per_tv_notice_voice_time);
    }

    public void setData(Per_Not_Voice_Mode mode) {
        per_tv_notice_voice_length.setText(mode.getLength());
        per_tv_notice_voice_time.setText(mode.getTime());
    }
}
