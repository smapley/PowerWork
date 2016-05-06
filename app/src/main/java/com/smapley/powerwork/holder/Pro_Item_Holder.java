package com.smapley.powerwork.holder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.smapley.powerwork.R;
import com.smapley.powerwork.activity.Project;
import com.smapley.powerwork.application.LocalApplication;
import com.smapley.powerwork.db.entity.ProjectEntity;
import com.smapley.powerwork.utils.MyData;

import org.xutils.x;


/**
 * Created by smapley on 15/11/16.
 */
public class Pro_Item_Holder extends BaseHolder {

    private ImageView pros_item_iv_pic;
    private TextView pros_item_tv_name;

    public Pro_Item_Holder(View view) {
        super(view);
        pros_item_iv_pic = (ImageView) view.findViewById(R.id.pros_item_iv_pic);
        pros_item_tv_name = (TextView) view.findViewById(R.id.pros_item_tv_name);

    }

    public void setData(final Context context, final ProjectEntity mode) {
        pros_item_tv_name.setText(mode.getName());
        x.image().bind(pros_item_iv_pic, MyData.URL_PIC + mode.getPic_url(), LocalApplication.getInstance().FilletImage);

        pros_item_iv_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Project.class);
                Bundle bundle=new Bundle();
                bundle.putInt("pro_id", mode.getPro_id());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }
}
