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

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.smapley.powerwork.application.LocalApplication;

import java.net.ProtocolException;

/**
 * Created by smapley on 15/10/22.
 */
public abstract class BaseFragment extends Fragment {

    private Context context;
    protected SharedPreferences sp_user;
    protected SharedPreferences sp_set;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        ViewUtils.inject(this, view);
        sp_user = LocalApplication.getInstance().sp_user;
        sp_set = LocalApplication.getInstance().sp_set;
        initParams();
        return view;
    }

    /**
     * 初始化布局
     */
    protected abstract int getLayoutId();

    /**
     * 设置参数
     */
    protected abstract void initParams();

    protected void showToast(int data) {
        Toast.makeText(getActivity(), data, Toast.LENGTH_SHORT).show();
    }
    protected void showToast(String data) {
        Toast.makeText(getActivity(), data, Toast.LENGTH_SHORT).show();
    }
}
