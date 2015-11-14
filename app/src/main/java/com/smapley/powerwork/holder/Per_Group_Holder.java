package com.smapley.powerwork.holder;

import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.smapley.powerwork.R;
import com.smapley.powerwork.activity.Achievement;
import com.smapley.powerwork.activity.Notes;
import com.smapley.powerwork.activity.Setting;
import com.smapley.powerwork.activity.Task;
import com.smapley.powerwork.mode.Per_Group_Mode;

/**
 * Created by smapley on 15/10/26.
 */
public class Per_Group_Holder extends BaseHolder {

    private View contentView;
    private TextView per_tv_group_name;
    private ImageView per_iv_group_pic;

    public Per_Group_Holder(View view) {
        super(view);
        contentView = view;
        per_tv_group_name = (TextView) view.findViewById(R.id.per_tv_group_name);
        per_iv_group_pic = (ImageView) view.findViewById(R.id.per_iv_group_pic);
    }

    public void setData(final Context context, final Per_Group_Mode mode) {
        per_tv_group_name.setText(mode.getName());
        changeView(mode.getItem(), false, context);

        contentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        changeView(mode.getItem(), true, context);
                        break;
                    case MotionEvent.ACTION_UP:
                        changeView(mode.getItem(), false, context);
                        break;
                }
                return false;
            }
        });
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (mode.getItem()) {
                    case 1:
                        context.startActivity(new Intent(context, Task.class));
                        break;
                    case 2:
                        context.startActivity(new Intent(context, Notes.class));
                        break;
                    case 3:
                        context.startActivity(new Intent(context, Achievement.class));
                        break;
                    case 4:
                        context.startActivity(new Intent(context, Setting.class));
                        break;
                }
            }
        });
    }

    private void changeView(int item, boolean checked, Context context) {
        int id = R.mipmap.task_iv_checked;
        switch (item) {
            case 1:
                id = checked ? R.mipmap.task_iv_checked_press : R.mipmap.task_iv_checked;
                break;
            case 2:
                id = checked ? R.mipmap.notes_iv_press : R.mipmap.notes_iv;
                break;
            case 3:
                id = checked ? R.mipmap.achievement_iv_press : R.mipmap.achievement_iv;
                break;
            case 4:
                id = checked ? R.mipmap.set_iv_press : R.mipmap.set_iv;
                break;
        }
        per_iv_group_pic.setImageResource(id);
        per_tv_group_name.setTextColor(context.getResources().getColor(checked ? R.color.default_text : R.color.cal_text));
    }
}
