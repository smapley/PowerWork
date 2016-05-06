package com.smapley.powerwork.activity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.powerwork.R;
import com.smapley.powerwork.application.LocalApplication;
import com.smapley.powerwork.db.entity.MessageEntity;
import com.smapley.powerwork.db.entity.UserEntity;
import com.smapley.powerwork.http.callback.HttpCallBack;
import com.smapley.powerwork.http.params.BaseParams;
import com.smapley.powerwork.utils.MyData;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by smapley on 15/10/23.
 */
@ContentView(R.layout.activity_feedback)
public class Feedback extends BaseActivity {

    @ViewInject(R.id.title_tv_name)
    private TextView title_tv_name;

    @ViewInject(R.id.fee_iv_pic)
    private ImageView fee_iv_pic;
    @ViewInject(R.id.fee_tv_name)
    private TextView fee_tv_name;
    @ViewInject(R.id.fee_et_content)
    private EditText fee_et_content;
    @ViewInject(R.id.fee_bt_submit)
    private Button fee_bt_submit;

    private int type;
    private int userId;

    @Override
    protected void initParams() {

        type=getIntent().getIntExtra("type",0);
        userId=getIntent().getIntExtra("userId",0);
        switch (type){
            case 0:
                title_tv_name.setText(R.string.feedback);
                fee_bt_submit.setText(R.string.sendFeedback);
                if (userEntity != null) {
                    x.image().bind(fee_iv_pic, MyData.URL_PIC + userEntity.getPicUrl(), LocalApplication.getInstance().CirtlesImage);
                    fee_tv_name.setText(userEntity.getTruename());
                }
                break;
            case 1:
                title_tv_name.setText(R.string.sendMessage);
                fee_bt_submit.setText(R.string.send);
                try{
                    UserEntity userEntity=dbUtils.findById(UserEntity.class,userId);
                    if (userEntity != null) {
                        x.image().bind(fee_iv_pic, MyData.URL_PIC + userEntity.getPicUrl(), LocalApplication.getInstance().CirtlesImage);
                        fee_tv_name.setText(userEntity.getTruename());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                break;
        }



    }

    @Event({R.id.fee_bt_submit, R.id.title_iv_back})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_iv_back:
                finish();
                break;
            case R.id.fee_bt_submit:
                switch (type){
                    case 0:
                        submit();
                        break;
                    case 1:
                        send();
                        break;
                }
                break;
        }
    }

    private void submit() {
        String details = fee_et_content.getText().toString();
        if (details != null && !details.isEmpty()) {
            BaseParams params = new BaseParams(MyData.URL_Feedback, userEntity);
            params.addBodyParameter("details", details);
            x.http().post(params, new HttpCallBack(this, R.string.feedback_ing) {
                @Override
                public void onResult(String result, SweetAlertDialog dialog) {
                    dialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE)
                            .showText(R.string.feedback_done)
                            .commit()
                            .dismiss(3000);
                }
            });
        } else {
            showToast(R.string.fee_content_null);
        }
    }
    private void send() {
        String details = fee_et_content.getText().toString();
        if (details != null && !details.isEmpty()) {
            BaseParams params = new BaseParams(MyData.URL_SendMessage, userEntity);
            params.addBodyParameter("userId2", userId+"");
            params.addBodyParameter("data",details);
            x.http().post(params, new HttpCallBack(this, R.string.feedback_ing) {
                @Override
                public void onResult(String result, SweetAlertDialog dialog) {
                    Log.d("result",result);
                    MessageEntity entity= JSON.parseObject(result,new TypeReference<MessageEntity>(){});
                    try{
                        dbUtils.saveOrUpdate(entity);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    dialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE)
                            .showText(R.string.sendMessage_done)
                            .commit()
                            .dismiss(3000);
                }
            });
        } else {
            showToast(R.string.message_null);
        }
    }
}
