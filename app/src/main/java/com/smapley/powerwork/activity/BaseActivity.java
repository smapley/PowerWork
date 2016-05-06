package com.smapley.powerwork.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.smapley.powerwork.R;
import com.smapley.powerwork.application.LocalApplication;
import com.smapley.powerwork.db.entity.UserEntity;
import com.smapley.powerwork.utils.ActivityStack;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by smapley on 15/10/22.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private boolean isCreate = false;
    protected SharedPreferences sp_user;
    protected SharedPreferences sp_set;
    protected DbManager dbUtils;
    protected SweetAlertDialog dialog;
    protected UserEntity userEntity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(this);
        x.view().inject(this);
        dbUtils = LocalApplication.getInstance().dbUtils;
        sp_user = LocalApplication.getInstance().sp_user;
        sp_set = LocalApplication.getInstance().sp_set;

        try {
            userEntity = dbUtils.findById(UserEntity.class, sp_user.getInt("id", 0));
        } catch (DbException e) {
            e.printStackTrace();
        }
        dialog = new SweetAlertDialog(this);
        isCreate = true;
        initParams();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isCreate) {
            isCreate = false;
            initParams();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        //Activity堆栈管理
        ActivityStack.getInstance().removeActivity(this);
        super.onDestroy();
    }

    /**
     * 参数设置
     */
    protected abstract void initParams();


    protected void showToast(int data) {
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(String data) {
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
    }

    protected void showOutLoginDialog(final Context context, String details) {
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
