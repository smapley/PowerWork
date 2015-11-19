package com.smapley.powerwork.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.util.LogUtils;
import com.smapley.powerwork.application.LocalApplication;
import com.smapley.powerwork.bitmap.AsyncImageLoader;
import com.smapley.powerwork.entity.User_Entity;

import java.net.ProtocolException;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by smapley on 15/10/22.
 */
public abstract class BaseFragment extends Fragment {

    private Context context;
    protected HttpUtils httpUtils;
    protected DbUtils dbUtils;
    protected SharedPreferences sp_user;
    protected SharedPreferences sp_set;
    protected SweetAlertDialog dialog;
    protected User_Entity user_entity = null;
    protected AsyncImageLoader asyncImageLoader;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        ViewUtils.inject(this, view);
        httpUtils = LocalApplication.getInstance().httpUtils;
        dbUtils = LocalApplication.getInstance().dbUtils;
        sp_user = LocalApplication.getInstance().sp_user;
        sp_set = LocalApplication.getInstance().sp_set;
        asyncImageLoader = LocalApplication.getInstance().asyncImageLoader;
        try {
            user_entity = dbUtils.findById(User_Entity.class, sp_user.getInt("id", 0));
        } catch (DbException e) {
            e.printStackTrace();
        }
        dialog = new SweetAlertDialog(context);
        initParams(view);
        return view;
    }

    /**
     * 初始化布局
     */
    protected abstract int getLayoutId();

    /**
     * 设置参数
     */
    protected abstract void initParams(View view);

    protected void showToast(int data) {
        Toast.makeText(getActivity(), data, Toast.LENGTH_SHORT).show();
    }
    protected void showToast(String data) {
        Toast.makeText(getActivity(), data, Toast.LENGTH_SHORT).show();
    }
}
