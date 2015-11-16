package com.smapley.powerwork.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.smapley.powerwork.R;
import com.smapley.powerwork.adapter.AddTaskAdapter;

/**
 * Created by smapley on 15/10/30.
 */
public class Add_Menu_Holder extends BaseHolder {

    private ImageView add_im_menu_annex;
    private ImageView add_im_menu_pic;
    private ImageView add_im_menu_voice;
    private ImageView add_im_menu_write;

    public Add_Menu_Holder(View view) {
        super(view);

        add_im_menu_annex = (ImageView) view.findViewById(R.id.add_im_menu_annex);
        add_im_menu_pic = (ImageView) view.findViewById(R.id.add_im_menu_pic);
        add_im_menu_voice = (ImageView) view.findViewById(R.id.add_im_menu_voice);
        add_im_menu_write = (ImageView) view.findViewById(R.id.add_im_menu_write);
    }

    public void setData(Context context, AddTaskAdapter adapter, int position) {


        add_im_menu_annex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        add_im_menu_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        add_im_menu_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        add_im_menu_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}
