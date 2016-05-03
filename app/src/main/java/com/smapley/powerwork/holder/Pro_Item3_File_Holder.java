package com.smapley.powerwork.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.smapley.powerwork.R;
import com.smapley.powerwork.application.LocalApplication;
import com.smapley.powerwork.db.entity.FileEntity;
import com.smapley.powerwork.fragment.Pro_Item3;
import com.smapley.powerwork.utils.MyData;

import org.xutils.x;

/**
 * Created by smapley on 15/11/17.
 */
public class Pro_Item3_File_Holder extends BaseHolder {

    private TextView pro_item3_file_tv_name;
    private ImageView pro_item3_file_iv_pic;

    public Pro_Item3_File_Holder(View view) {
        super(view);
        pro_item3_file_tv_name = (TextView) view.findViewById(R.id.pro_item3_file_tv_name);
        pro_item3_file_iv_pic = (ImageView) view.findViewById(R.id.pro_item3_file_iv_pic);

    }

    public void setData(final Pro_Item3 pro_item3, final FileEntity mode) {
        pro_item3_file_tv_name.setText(mode.getName());
        switch (mode.getType()) {
            case 1:
                x.image().bind(pro_item3_file_iv_pic, MyData.URL_File+mode.getUrl(), LocalApplication.getInstance().CirtlesImage);
                break;
        }
    }
}
