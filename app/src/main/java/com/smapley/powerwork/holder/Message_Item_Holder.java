package com.smapley.powerwork.holder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.powerwork.R;
import com.smapley.powerwork.application.LocalApplication;
import com.smapley.powerwork.db.modes.ProUseMode;
import com.smapley.powerwork.db.service.ProUseService;
import com.smapley.powerwork.http.MyResponse;
import com.smapley.powerwork.mode.Message_Mode;
import com.smapley.powerwork.utils.DateUtil;
import com.smapley.powerwork.utils.MyData;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by smapley on 15/10/30.
 */
public class Message_Item_Holder extends BaseHolder {

    private ImageView image;
    private TextView name;
    private TextView user;
    private View view;

    public Message_Item_Holder(View view) {
        super(view);
        this.view = view;
        image = (ImageView) view.findViewById(R.id.adapter_sea_item_iv_image);
        name = (TextView) view.findViewById(R.id.adapter_sea_item_tv_name);
        user = (TextView) view.findViewById(R.id.adapter_sea_item_tv_user);
    }


    public void setData(final Context context, final Message_Mode mode) {
        x.image().bind(image, MyData.URL_PIC + mode.getPic_url(), LocalApplication.getInstance().CirtlesImage);
        name.setText(mode.getName());
        user.setText(DateUtil.getDateString(mode.getCre_date(),DateUtil.formatDayAndTime));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mode.getType()) {
                    case 0:
                        //消息

                        break;
                    case 1:
                        //邀请加入项目
                        AlertDialog.Builder builder=new AlertDialog.Builder(context);
                        builder.setMessage("是否接收邀请？");
                        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                RequestParams params=new RequestParams(MyData.URL_JoinProject);
                                params.addBodyParameter("modeId",mode.getId()+"");
                                params.addBodyParameter("type",1+"");
                                x.http().post(params, new Callback.CommonCallback<MyResponse>() {
                                    @Override
                                    public void onSuccess(MyResponse result) {
                                        if(result.flag.equals(MyData.SUCC)){
                                            ProUseMode mode1= JSON.parseObject(result.data,new TypeReference<ProUseMode>(){});
                                            ProUseService.save(mode1);
                                            Toast.makeText(context,"加入成功！",Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(context,"网络连接失败！",Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onError(Throwable ex, boolean isOnCallback) {
                                        Toast.makeText(context,"网络连接失败！",Toast.LENGTH_SHORT).show();

                                    }

                                    @Override
                                    public void onCancelled(CancelledException cex) {

                                    }

                                    @Override
                                    public void onFinished() {

                                    }
                                });
                            }
                        });
                        builder.setNegativeButton("取消",null);
                        builder.create().show();

                        break;
                }

            }
        });
    }

}
