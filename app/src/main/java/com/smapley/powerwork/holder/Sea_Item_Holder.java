package com.smapley.powerwork.holder;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.smapley.powerwork.R;
import com.smapley.powerwork.activity.Account;
import com.smapley.powerwork.application.LocalApplication;
import com.smapley.powerwork.mode.SearchMode;
import com.smapley.powerwork.utils.MyData;
import com.smapley.powerwork.utils.ThreadSleep;

import org.xutils.x;

/**
 * Created by smapley on 15/10/30.
 */
public class Sea_Item_Holder extends BaseHolder {

    private ImageView image;
    private TextView name;
    private TextView user;
    private View view;

    public Sea_Item_Holder(View view) {
        super(view);
        this.view = view;
        image = (ImageView) view.findViewById(R.id.adapter_sea_item_iv_image);
        name = (TextView) view.findViewById(R.id.adapter_sea_item_tv_name);
        user = (TextView) view.findViewById(R.id.adapter_sea_item_tv_user);
    }


    public void setData(final Context context, final SearchMode mode, final int projectId) {
        x.image().bind(image, MyData.URL_PIC + mode.getPic_url(), LocalApplication.getInstance().CirtlesImage);
        name.setText(mode.getName());
        user.setText(mode.getCre_date() + "");
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mode.getType()) {
                    case 0:
                        //用户
                        Intent intent = new Intent(context, Account.class);
                        intent.putExtra("userId", mode.getId());
                        intent.putExtra("edit", false);
                        if (projectId > 0)
                            intent.putExtra("join", true);
                        intent.putExtra("projectId", projectId);
                        intent.putExtra("userId2", mode.getId());
                        context.startActivity(intent);

                        break;
                    case 1:
                        //项目
                        AlertDialog.Builder builder=new AlertDialog.Builder(context);
                        builder.setMessage("发送加入请求？");
                        builder.setNegativeButton("取消",null);
                        builder.setPositiveButton("发送", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final ProgressDialog progressDialog=new ProgressDialog(context);
                                progressDialog.show();
                                new ThreadSleep().sleep(1000, new ThreadSleep.Callback() {
                                    @Override
                                    public void onCallback(int number) {
                                        progressDialog.dismiss();
                                        Toast.makeText(context,"请求发送成功！",Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        });
                        builder.create().show();
                        break;
                }

            }
        });
    }
}
