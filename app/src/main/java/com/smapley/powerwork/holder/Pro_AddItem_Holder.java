package com.smapley.powerwork.holder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.powerwork.R;
import com.smapley.powerwork.activity.BaseActivity;
import com.smapley.powerwork.activity.Project;
import com.smapley.powerwork.application.LocalApplication;
import com.smapley.powerwork.entity.ProjectEntity;
import com.smapley.powerwork.http.BaseParams;
import com.smapley.powerwork.http.HttpCallBack;
import com.smapley.powerwork.mode.Pro_AddItem_Mode;
import com.smapley.powerwork.utils.MyData;
import com.smapley.powerwork.utils.ThreadSleep;

import org.xutils.ex.DbException;
import org.xutils.x;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * Created by smapley on 15/11/16.
 */
public class Pro_AddItem_Holder extends BaseHolder {

    private ImageView pro_additem_iv_add;

    public Pro_AddItem_Holder(View view) {
        super(view);
        pro_additem_iv_add = (ImageView) view.findViewById(R.id.pro_additem_iv_add);

    }

    public void setData(final Context context, final Pro_AddItem_Mode mode) {

        pro_additem_iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SweetAlertDialog dialog = new SweetAlertDialog(context);
                dialog.showTitle(R.string.addproject)
                        .showEditext()
                        .showConfirmButton()
                        .showCancelButton()
                        .setOnSweetClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onConfirmClick(SweetAlertDialog dialog) {
                                String data = dialog.getEditext();
                                if (data != null && !data.isEmpty()) {
                                    addProject(context, data);
                                    dialog.dismiss();
                                }
                            }

                            @Override
                            public void onFirstClick(SweetAlertDialog dialog) {

                            }

                            @Override
                            public void onCancelClick(SweetAlertDialog dialog) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
    }

    private void addProject(final Context context, String data) {
        BaseParams params = new BaseParams(MyData.URL_AddProject, ((BaseActivity) context).user_entity);
        params.addBodyParameter("name", data);
        x.http().post(params, new HttpCallBack(context, R.string.addproject_ing) {
            @Override
            public void onResult(String result, SweetAlertDialog dialog) {
                dialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                dialog.showText(R.string.addproject_ed).commit().dismiss(2000);
                final ProjectEntity project_entity = JSON.parseObject(result, new TypeReference<ProjectEntity>() {
                });
                try {
                    LocalApplication.getInstance().dbUtils.save(project_entity);
                } catch (DbException e) {
                    e.printStackTrace();
                }
                new ThreadSleep().sleep(2000, new ThreadSleep.Callback() {
                    @Override
                    public void onCallback(int number) {
                        Intent intent = new Intent(context, Project.class);
                        Bundle bundle=new Bundle();
                        bundle.putInt("pro_id",project_entity.getPro_id());
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                });
            }
        });
    }
}
