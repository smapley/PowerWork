package com.smapley.powerwork.holder;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.smapley.powerwork.R;
import com.smapley.powerwork.mode.Pro_Item5_Folder_Mode;
import com.smapley.powerwork.mode.Pro_Item5_Title_Mode;

import java.util.List;

/**
 * Created by smapley on 15/11/17.
 */
public class Pro_Item5_Folder_Holder extends BaseHolder {

    private TextView pro_item5_folder_tv_name;
    private ImageView pro_item5_folder_iv_pic;
    private int[] pics = new int[]{R.mipmap.file_grey_iv,
            R.mipmap.file_blue_iv,
            R.mipmap.file_green_iv,
            R.mipmap.file_orange_iv,
            R.mipmap.file_red_iv};

    public Pro_Item5_Folder_Holder(View view) {
        super(view);
        pro_item5_folder_tv_name = (TextView) view.findViewById(R.id.pro_item5_folder_tv_name);
        pro_item5_folder_iv_pic = (ImageView) view.findViewById(R.id.pro_item5_folder_iv_pic);

    }

    public void setData(Pro_Item5_Folder_Mode mode) {
        pro_item5_folder_tv_name.setText(mode.getName());
        pro_item5_folder_iv_pic.setImageResource(pics[mode.getFolderType()]);

    }
}
