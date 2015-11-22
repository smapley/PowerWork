package com.smapley.powerwork.http;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.smapley.powerwork.R;
import com.smapley.powerwork.activity.Login;
import com.smapley.powerwork.application.LocalApplication;
import com.smapley.powerwork.utils.ActivityStack;
import com.smapley.powerwork.utils.MyData;

import org.xutils.common.Callback;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by smapley on 15/11/20.
 */
public abstract class HttpCallBack implements
        Callback.CommonCallback<MyResponse>,
        Callback.ProgressCallback<MyResponse> {

    private SweetAlertDialog dialog;
    private Context context;

    public HttpCallBack(Context context, int id) {

        this.context = context;
        dialog = new SweetAlertDialog(context);
        dialog.showText(id);
    }

    @Override
    public void onStarted() {
        dialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
        dialog.show();
    }

    @Override
    public void onSuccess(MyResponse result) {
        if (MyData.SUCC.equals(result.flag)) {
            this.onResult(result.data, dialog);
        } else if (MyData.OutLogin.equals(result.flag)) {
            dialog.changeAlertType(SweetAlertDialog.ERROR_TYPE)
                    .showText(result.details)
                    .showConfirmButton(R.string.login)
                    .showCancelButton()
                    .commit().setOnSweetClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onConfirmClick(SweetAlertDialog dialog) {
                    SharedPreferences.Editor editor = LocalApplication.getInstance().sp_user.edit();
                    editor.putBoolean("islogin", false);
                    editor.commit();
                    ActivityStack.getInstance().finishAllActivity();
                    context.startActivity(new Intent(context, Login.class));
                    dialog.dismiss();
                }

                @Override
                public void onFirstClick(SweetAlertDialog dialog) {

                }

                @Override
                public void onCancelClick(SweetAlertDialog dialog) {
                    dialog.dismiss();
                }
            });
        } else {
            dialog.changeAlertType(SweetAlertDialog.ERROR_TYPE)
                    .showText(result.details)
                    .showCancelButton()
                    .commit();
        }
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        ex.printStackTrace();
        dialog.changeAlertType(SweetAlertDialog.ERROR_TYPE)
                .showText(R.id.connect_fai)
                .commit()
                .dismiss(2000);
    }

    @Override
    public void onCancelled(CancelledException cex) {

    }

    @Override
    public void onFinished() {

    }

    @Override
    public void onLoading(long total, long current, boolean isDownloading) {

    }

    @Override
    public void onWaiting() {
        
    }

    public abstract void onResult(String result, SweetAlertDialog dialog);
}
