package com.smapley.powerwork.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smapley.powerwork.R;
import com.smapley.powerwork.mode.Pro_Item2_Group_Mode;

/**
 * Created by smapley on 15/11/28.
 */
public class Pro_Item2_Group_Holder extends BaseHolder {

    private LinearLayout pro_item2_group_ll_add;
    private TextView pro_item2_group_tv_title;
    private TextView pro_item2_group_tv_add;
    private ImageView pro_item2_group_iv_add;

    public Pro_Item2_Group_Holder(View view) {
        super(view);
        pro_item2_group_ll_add = (LinearLayout) view.findViewById(R.id.pro_item2_group_ll_add);
        pro_item2_group_iv_add = (ImageView) view.findViewById(R.id.pro_item2_group_iv_add);
        pro_item2_group_tv_add = (TextView) view.findViewById(R.id.pro_item2_group_tv_add);
        pro_item2_group_tv_title = (TextView) view.findViewById(R.id.pro_item2_group_tv_title);
    }

    public void setData(Context context,Pro_Item2_Group_Mode mode) {

        pro_item2_group_tv_title.setText(mode.getName());
        if(mode.isShowAdd()){
            pro_item2_group_ll_add.setVisibility(View.VISIBLE);
            pro_item2_group_ll_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }else{
            pro_item2_group_ll_add.setVisibility(View.GONE);
        }

    }

}
