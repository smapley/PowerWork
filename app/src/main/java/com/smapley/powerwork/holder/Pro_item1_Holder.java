package com.smapley.powerwork.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.smapley.powerwork.R;
import com.smapley.powerwork.application.LocalApplication;
import com.smapley.powerwork.entity.DynamicEntity;
import com.smapley.powerwork.utils.DateUtil;
import com.smapley.powerwork.utils.MyData;

import org.xutils.x;

/**
 * Created by smapley on 15/11/27.
 */
public class Pro_Item1_Holder extends BaseHolder {


    private ImageView use_pic;
    private TextView use_name;
    private TextView type;
    private TextView cre_date;
    private ImageView dynamic_pic;
    private TextView dynamic_name;
    private TextView praise_text;
    private TextView discuss_text;
    private String[] types;


    public Pro_Item1_Holder(View view) {
        super(view);

        use_pic = (ImageView) view.findViewById(R.id.adapter_pro_item1_iv_user_pic);
        use_name = (TextView) view.findViewById(R.id.adapter_pro_item1_tv_user_name);
        type = (TextView) view.findViewById(R.id.adapter_pro_item1_tv_type);
        cre_date = (TextView) view.findViewById(R.id.adapter_pro_item1_tv_cre_date);
        dynamic_pic = (ImageView) view.findViewById(R.id.adapter_pro_item1_iv_durl);
        dynamic_name = (TextView) view.findViewById(R.id.adapter_pro_item1_tv_dname);
        praise_text = (TextView) view.findViewById(R.id.adapter_pro_item1_tv_praise);
        discuss_text = (TextView) view.findViewById(R.id.adapter_pro_item1_tv_discuss);

    }

    public void setData(Context context, DynamicEntity mode) {
        types = context.getResources().getStringArray(R.array.dynamic_type);
        x.image().bind(use_pic, MyData.URL_PIC + mode.getPic_url(), LocalApplication.getInstance().CirtlesImage);
        use_name.setText(mode.getUse_name());
        type.setText(types[mode.getType()]);
        cre_date.setText(DateUtil.getDateString(mode.getCre_date(), DateUtil.formatDate));
        switch (mode.getType()){
            case 2:
                x.image().bind(dynamic_pic, MyData.URL_File + mode.getdPic_url(), LocalApplication.getInstance().CirtlesImage);
                break;
        }
        dynamic_name.setText(mode.getDetai());

        praise_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        discuss_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
