package com.smapley.powerwork.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.smapley.powerwork.application.LocalApplication;
import com.smapley.powerwork.entity.UserEntity;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by smapley on 15/10/22.
 */
public abstract class BaseFragment extends Fragment {

    private boolean injected = false;
    private Context context;
    protected DbManager dbUtils;
    protected SharedPreferences sp_user;
    protected SharedPreferences sp_set;
    protected SweetAlertDialog dialog;
    protected UserEntity user_entity = null;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = x.view().inject(this, inflater, container);
        dbUtils = LocalApplication.getInstance().dbUtils;
        sp_user = LocalApplication.getInstance().sp_user;
        sp_set = LocalApplication.getInstance().sp_set;
        try {
            user_entity = dbUtils.findById(UserEntity.class, sp_user.getInt("id", 0));
        } catch (DbException e) {
            e.printStackTrace();
        }
        dialog = new SweetAlertDialog(context);
        initParams(view);
        injected = true;
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!injected) {
            x.view().inject(this, this.getView());
        }
    }


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
