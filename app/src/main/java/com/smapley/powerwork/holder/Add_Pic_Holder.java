package com.smapley.powerwork.holder;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.smapley.powerwork.R;
import com.smapley.powerwork.application.LocalApplication;
import com.smapley.powerwork.db.entity.NoteDetailsEntity;
import com.smapley.powerwork.utils.BitmapUtil;

import java.io.FileNotFoundException;

/**
 * Created by smapley on 15/10/30.
 */
public class Add_Pic_Holder extends BaseHolder {

    private ImageView add_iv_pic;

    public Add_Pic_Holder(View view) {
        super(view);
        add_iv_pic = (ImageView) view.findViewById(R.id.add_iv_pic);
    }


    public void setData(Context context, NoteDetailsEntity mode) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapUtil.decodeSampledBitmap(mode.getPath(), LocalApplication.getInstance().screenW-70, 20);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        add_iv_pic.setImageBitmap(bitmap);

    }
}
