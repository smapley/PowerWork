package com.smapley.powerwork.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.smapley.powerwork.R;
import com.smapley.powerwork.application.LocalApplication;
import com.smapley.powerwork.db.entity.ProjectEntity;
import com.smapley.powerwork.utils.MyData;

import org.xutils.x;

/**
 * Created by smapley on 15/10/30.
 */
public class Sea_Item_Holder extends BaseHolder {

    private ImageView image;
    private TextView name;
    private TextView user;

    public Sea_Item_Holder(View view) {
        super(view);
        image = (ImageView) view.findViewById(R.id.adapter_sea_item_iv_image);
        name = (TextView) view.findViewById(R.id.adapter_sea_item_tv_name);
        user = (TextView) view.findViewById(R.id.adapter_sea_item_tv_user);
    }


    public void setData(Context context,ProjectEntity projectEntity) {
        x.image().bind(image, MyData.URL_PIC+projectEntity.getPic_url(), LocalApplication.getInstance().CirtlesImage);
        name.setText(projectEntity.getName());
        user.setText(projectEntity.getCre_date()+"");
    }
}
