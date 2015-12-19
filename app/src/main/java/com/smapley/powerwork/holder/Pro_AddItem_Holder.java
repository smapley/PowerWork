package com.smapley.powerwork.holder;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.smapley.powerwork.R;
import com.smapley.powerwork.activity.Add;
import com.smapley.powerwork.activity.MainActivity;
import com.smapley.powerwork.activity.Search;
import com.smapley.powerwork.mode.Pro_AddItem_Mode;


/**
 * Created by smapley on 15/11/16.
 */
public class Pro_AddItem_Holder extends BaseHolder {

    private ImageView pro_additem_iv_add;
    private PopupWindow pop_project;

    public Pro_AddItem_Holder(View view) {
        super(view);
        pro_additem_iv_add = (ImageView) view.findViewById(R.id.pro_additem_iv_add);

    }

    public void setData(final Context context, final Pro_AddItem_Mode mode) {

        pro_additem_iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProjectPopupWindow(view, context);
            }
        });
    }
    /**
     * 显示选择文件PopupWindow
     */
    private void showProjectPopupWindow(View view,Context context) {
        //初始化
        initProjectPopupWindow(context);
        pop_project.showAtLocation(view, Gravity.CENTER, 0, 0);
        //设置背景变暗
        pop_project.setBackgroundDrawable(new ColorDrawable(0));
        WindowManager.LayoutParams layoutParams = ((MainActivity)context).getWindow().getAttributes();
        layoutParams.alpha = 0.4f;
        ((MainActivity)context).getWindow().setAttributes(layoutParams);
    }
    /**
     * 初始化选择文件PopupWindow
     */
    private void initProjectPopupWindow(final Context context) {
        View popView = LayoutInflater.from(context).inflate(R.layout.popup_pro_add, null);
        pop_project = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置不可点击背景
        pop_project.setFocusable(true);
        pop_project.setBackgroundDrawable(new BitmapDrawable());
        //设置popwindow出现和消失动画
        pop_project.setAnimationStyle(R.style.pop_voice);
        //监听popupwindow消失动作
        pop_project.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                hitProjectPopupWindow(context);
            }
        });

        //监听按钮点击
        TextView item1 = (TextView) popView.findViewById(R.id.pro_add_tv_item1);
        TextView item2 = (TextView) popView.findViewById(R.id.pro_add_tv_item2);
        item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hitProjectPopupWindow(context);
                Intent intent = new Intent(context, Add.class);
                intent.putExtra("src", 2);
                ((MainActivity) context).startActivityForResult(intent, 1);
            }
        });

        item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hitProjectPopupWindow(context);
                context.startActivity(new Intent(context, Search.class));
            }
        });
    }

    /**
     * 隐藏选择文件PopupWindow
     */
    private void hitProjectPopupWindow(Context context) {
        if (pop_project.isShowing())
            pop_project.dismiss();
        WindowManager.LayoutParams layoutParams = ((MainActivity)context).getWindow().getAttributes();
        layoutParams.alpha = 1f;
        ((MainActivity)context).getWindow().setAttributes(layoutParams);
    }
}
