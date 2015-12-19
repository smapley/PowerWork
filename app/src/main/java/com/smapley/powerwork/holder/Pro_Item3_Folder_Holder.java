package com.smapley.powerwork.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.smapley.powerwork.R;
import com.smapley.powerwork.db.entity.FolderEntity;
import com.smapley.powerwork.fragment.Pro_Item3;

/**
 * Created by smapley on 15/11/17.
 */
public class Pro_Item3_Folder_Holder extends BaseHolder {

    private TextView pro_item3_folder_tv_name;
    private ImageView pro_item3_folder_iv_pic;
    private int[] pics = new int[]{R.mipmap.file_grey_iv,
            R.mipmap.file_blue_iv,
            R.mipmap.file_green_iv,
            R.mipmap.file_orange_iv,
            R.mipmap.file_red_iv};

    public Pro_Item3_Folder_Holder(View view) {
        super(view);
        pro_item3_folder_tv_name = (TextView) view.findViewById(R.id.pro_item3_folder_tv_name);
        pro_item3_folder_iv_pic = (ImageView) view.findViewById(R.id.pro_item3_folder_iv_pic);

    }

    public void setData(final Pro_Item3 pro_item3, final FolderEntity mode) {
        pro_item3_folder_tv_name.setText(mode.getName());
        if (mode.isBack())
            pro_item3_folder_iv_pic.setImageResource(pics[0]);
        else
            pro_item3_folder_iv_pic.setImageResource(pics[1]);

        pro_item3_folder_tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mode.isBack())
                    pro_item3.mhandler.obtainMessage(6, mode.getFol_id2()).sendToTarget();
                else
                    pro_item3.mhandler.obtainMessage(5, mode.getFol_id()).sendToTarget();
            }
        });

    }
}
