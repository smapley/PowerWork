package com.smapley.powerwork.holder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest;
import com.smapley.powerwork.R;
import com.smapley.powerwork.activity.BaseActivity;
import com.smapley.powerwork.activity.Project;
import com.smapley.powerwork.entity.Project_Entity;
import com.smapley.powerwork.http.HttpCallBack;
import com.smapley.powerwork.http.MyRequstParams;
import com.smapley.powerwork.mode.Pro_AddItem_Mode;
import com.smapley.powerwork.utils.MyData;
import com.smapley.powerwork.utils.ThreadSleep;

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
        RequestParams params = new MyRequstParams(((BaseActivity) context).user_entity);
        params.addBodyParameter("name", data);
        ((BaseActivity) context).httpUtils.send(HttpRequest.HttpMethod.POST, MyData.URL_AddProject, params, new HttpCallBack(context, R.string.addproject_ing) {
            @Override
            public void onResult(String result, SweetAlertDialog dialog) {
                dialog.showText(R.string.addproject_ed).commit().dismiss(2000);
                final Project_Entity project_entity = JSON.parseObject(result, new TypeReference<Project_Entity>() {
                });
                new ThreadSleep().sleep(2000, new ThreadSleep.Callback() {
                    @Override
                    public void onCallback(int number) {
                        Intent intent = new Intent(context, Project.class);
                        intent.putExtra("pro_id", project_entity.getPro_id());
                        intent.putExtra("pro_name", project_entity.getName());
                        context.startActivity(intent);
                    }
                });
            }
        });
    }
}
