package com.smapley.powerwork.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.smapley.powerwork.R;
import com.smapley.powerwork.activity.Login;
import com.smapley.powerwork.application.LocalApplication;
import com.smapley.powerwork.db.entity.UserBaseEntity;
import com.smapley.powerwork.db.entity.UserEntity;
import com.smapley.powerwork.utils.ActivityStack;

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
    protected UserBaseEntity userBaseEntity=null;
    protected UserEntity userEntity=null;

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
            userBaseEntity=dbUtils.findById(UserBaseEntity.class,sp_user.getInt("id", 0));

        } catch (DbException e) {
            e.printStackTrace();
        }
        if(userBaseEntity!=null)
            try {
                userEntity=dbUtils.findById(UserEntity.class,userBaseEntity.getUseId());
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

    public abstract void getDataForDb();
    public abstract void getDataForWeb();

    protected void showToast(int data) {
        Toast.makeText(getActivity(), data, Toast.LENGTH_SHORT).show();
    }
    protected void showToast(String data) {
        Toast.makeText(getActivity(), data, Toast.LENGTH_SHORT).show();
    }

    protected void showOutLoginDialog(final Context context,String details) {
        SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
        dialog.showText(details)
                .showConfirmButton(R.string.login)
                .showCancelButton()
                .setOnSweetClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onConfirmClick(SweetAlertDialog dialog) {
                        SharedPreferences.Editor editor = LocalApplication.getInstance().sp_user.edit();
                        editor.putBoolean("islogin", false);
                        editor.commit();
                        ActivityStack.getInstance().finishAllActivity();
                        startActivity(new Intent(context, Login.class));
                        dialog.dismiss();
                    }

                    @Override
                    public void onFirstClick(SweetAlertDialog dialog) {

                    }

                    @Override
                    public void onCancelClick(SweetAlertDialog dialog) {
                        dialog.dismiss();
                    }
                }).show();
    }
}
