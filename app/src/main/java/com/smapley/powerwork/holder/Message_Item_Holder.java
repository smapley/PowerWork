package com.smapley.powerwork.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.smapley.powerwork.R;
import com.smapley.powerwork.application.LocalApplication;
import com.smapley.powerwork.mode.Message_Mode;
import com.smapley.powerwork.utils.DateUtil;
import com.smapley.powerwork.utils.MyData;

import org.xutils.x;

/**
 * Created by smapley on 15/10/30.
 */
public class Message_Item_Holder extends BaseHolder {

    private ImageView image;
    private TextView name;
    private TextView user;
    private View view;

    public Message_Item_Holder(View view) {
        super(view);
        this.view = view;
        image = (ImageView) view.findViewById(R.id.adapter_sea_item_iv_image);
        name = (TextView) view.findViewById(R.id.adapter_sea_item_tv_name);
        user = (TextView) view.findViewById(R.id.adapter_sea_item_tv_user);
    }


    public void setData(final Context context, final Message_Mode mode) {
        x.image().bind(image, MyData.URL_PIC + mode.getPic_url(), LocalApplication.getInstance().CirtlesImage);
        name.setText(mode.getName());
        user.setText(DateUtil.getDateString(mode.getCre_date(),DateUtil.formatDayAndTime));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mode.getType()) {
                    case 0:
                        //消息

                        break;
                    case 1:
                        //邀请加入项目


                        break;
                }

            }
        });
    }

}
