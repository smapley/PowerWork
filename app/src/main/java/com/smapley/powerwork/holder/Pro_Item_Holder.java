package com.smapley.powerwork.holder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.smapley.powerwork.R;
import com.smapley.powerwork.activity.Project;
import com.smapley.powerwork.mode.Pro_Item_Mode;


/**
 * Created by smapley on 15/11/16.
 */
public class Pro_Item_Holder extends BaseHolder {

    private ImageView pros_item_iv_pic;
    private TextView pros_item_tv_name;
    private ImageView pros_item_iv_hasnow;

    public Pro_Item_Holder(View view) {
        super(view);
        pros_item_iv_pic = (ImageView) view.findViewById(R.id.pros_item_iv_pic);
        pros_item_tv_name = (TextView) view.findViewById(R.id.pros_item_tv_name);
        pros_item_iv_hasnow = (ImageView) view.findViewById(R.id.pros_item_iv_hasnow);

    }

    public void setData(final Context context, final Pro_Item_Mode mode) {
        pros_item_tv_name.setText(mode.getName());

        pros_item_iv_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Project.class);
                intent.putExtra("pro_name", mode.getName());
                intent.putExtra("pro_id", mode.getPro_id());
                context.startActivity(intent);
            }
        });
    }
}
