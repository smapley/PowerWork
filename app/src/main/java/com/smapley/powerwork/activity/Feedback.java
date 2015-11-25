package com.smapley.powerwork.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.smapley.powerwork.R;
import com.smapley.powerwork.application.LocalApplication;
import com.smapley.powerwork.http.BaseParams;
import com.smapley.powerwork.http.HttpCallBack;
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


    @Override
    protected void initParams() {
        title_tv_name.setText(R.string.feedback);
        if (user_entity != null) {
            x.image().bind(fee_iv_pic, MyData.URL_PIC + user_entity.getPicUrl(), LocalApplication.getInstance().CirtlesImage);
            fee_tv_name.setText(user_entity.getUsername());
        }
    }

    @Event({R.id.fee_bt_submit, R.id.title_iv_back})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_iv_back:
                finish();
                break;
            case R.id.fee_bt_submit:
                submit();
                break;
        }
    }

    private void submit() {
        String details = fee_et_content.getText().toString();
        if (details != null && !details.isEmpty()) {
            BaseParams params = new BaseParams(MyData.URL_Feedback, user_entity);
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
}
