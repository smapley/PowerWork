package com.smapley.powerwork.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.smapley.powerwork.R;
import com.smapley.powerwork.entity.OtherTaskEntity;
import com.smapley.powerwork.utils.DateUtil;

/**
 * Created by smapley on 15/11/28.
 */
public class Pro_Item2_Task2_Holder extends BaseHolder {

    private TextView pro_item2_task2_tv_name;
    private TextView pro_item2_task2_tv_time;
    private ImageView pro_item2_task2_iv_image;

    public Pro_Item2_Task2_Holder(View view) {
        super(view);
        pro_item2_task2_tv_name = (TextView) view.findViewById(R.id.pro_item2_task2_tv_name);
        pro_item2_task2_tv_time = (TextView) view.findViewById(R.id.pro_item2_task2_tv_time);
        pro_item2_task2_iv_image = (ImageView) view.findViewById(R.id.pro_item2_task2_iv_image);
    }

    public void setData(Context context, OtherTaskEntity mode) {

        pro_item2_task2_tv_name.setText(mode.getName());
        pro_item2_task2_tv_time.setText(DateUtil.getDateString(mode.getEnd_date(), DateUtil.formatDate));


     //   x.image().bind(pro_item2_task2_iv_image, MyData.URL_PIC + mode.getPic_url(), LocalApplication.getInstance().CirtlesImage);
    }

}
