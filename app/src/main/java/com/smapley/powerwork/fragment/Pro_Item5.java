package com.smapley.powerwork.fragment;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.smapley.powerwork.R;
import com.smapley.powerwork.activity.NewFolder;
import com.smapley.powerwork.adapter.ProItem5Adapter;
import com.smapley.powerwork.mode.BaseMode;
import com.smapley.powerwork.mode.Pro_Item5_Folder_Mode;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smapley on 15/11/16.
 */
@ContentView(R.layout.fragment_pro_item5)
public class Pro_Item5 extends BaseFragment {


    @ViewInject(R.id.pro_item5_rv_list)
    private RecyclerView pro_item5_rv_list;

    @ViewInject(R.id.title_tv_name)
    private TextView title_tv_name;
    @ViewInject(R.id.title_iv_add)
    private ImageView title_iv_add;

    private List<BaseMode> list_data;

    private ProItem5Adapter adapter;

    private PopupWindow pop_add;


    @Override
    protected void initParams(View view) {
        title_tv_name.setText(R.string.pro_tv_btn_item5);
        title_iv_add.setVisibility(View.VISIBLE);

        pro_item5_rv_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        initData();
    }

    private void initData() {
        list_data = new ArrayList<>();
//        Pro_Item5_Title_Mode pro_item5_title_mode = new Pro_Item5_Title_Mode();
//        pro_item5_title_mode.setName("文件夹");
//        list_data.add(pro_item5_title_mode);
        adapter = new ProItem5Adapter(getActivity(), list_data);
        pro_item5_rv_list.setAdapter(adapter);
    }

    @Event({R.id.title_iv_back, R.id.title_iv_add})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_iv_back:
                getActivity().finish();
                break;
            case R.id.title_iv_add:
                showAddPopupWindow(view);
                break;
        }
    }

    /**
     * 初始化新建文件PopupWindow
     */
    private void initAddPopupWindow() {
        View popView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_pro_item5_add, null);
        pop_add = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置不可点击背景
        pop_add.setFocusable(true);
        pop_add.setBackgroundDrawable(new BitmapDrawable());
        //设置popwindow出现和消失动画
        pop_add.setAnimationStyle(R.style.pro_item5_add);
        //监听popupwindow消失动作
        pop_add.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                hitAddPopupWindow();
            }
        });

        //监听按钮点击
        TextView newfile = (TextView) popView.findViewById(R.id.pro_item5_tv_nowfile);
        TextView newfiles = (TextView) popView.findViewById(R.id.pro_item5_tv_nowfiles);
        newfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hitAddPopupWindow();

            }
        });

        newfiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hitAddPopupWindow();
                getActivity().startActivityForResult(new Intent(getActivity(), NewFolder.class), 1);
            }
        });
    }

    /**
     * 显示新建文件PopupWindow
     */
    private void showAddPopupWindow(View view) {
        //初始化
        initAddPopupWindow();

        pop_add.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        //设置背景变暗
        pop_add.setBackgroundDrawable(new ColorDrawable(0));
        WindowManager.LayoutParams layoutParams = getActivity().getWindow().getAttributes();
        layoutParams.alpha = 0.4f;
        getActivity().getWindow().setAttributes(layoutParams);


    }

    /**
     * 隐藏新建文件PopupWindow
     */
    private void hitAddPopupWindow() {
        if (pop_add.isShowing())
            pop_add.dismiss();
        WindowManager.LayoutParams layoutParams = getActivity().getWindow().getAttributes();
        layoutParams.alpha = 1f;
        getActivity().getWindow().setAttributes(layoutParams);
    }

    /**
     * 新建文件夹
     */
    public void addFolder(String name) {

        Pro_Item5_Folder_Mode pro_item5_folder_mode = new Pro_Item5_Folder_Mode();
        pro_item5_folder_mode.setName(name);
        adapter.addFolder(pro_item5_folder_mode);

    }

    /**
     * 新建文件
     */
    public void addFile(String path) {

    }
}
