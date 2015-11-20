package com.smapley.powerwork.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.smapley.powerwork.http.HttpCallBack;
import com.smapley.powerwork.http.MyRequstParams;
import com.smapley.powerwork.utils.MyData;
import com.smapley.powerwork.R;
import com.smapley.powerwork.view.CircleImageView;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by smapley on 15/10/23.
 */
@ContentView(R.layout.activity_feedback)
public class Feedback extends BaseActivity {

    @ViewInject(R.id.title_tv_name)
    private TextView title_tv_name;

    @ViewInject(R.id.fee_iv_pic)
    private CircleImageView fee_iv_pic;
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
            asyncImageLoader.loadBitmaps(fee_iv_pic, user_entity.getPic_url());
            fee_tv_name.setText(user_entity.getUsername());
        }
    }

    @OnClick({R.id.fee_bt_submit, R.id.title_iv_back})
    public void onClick(View view) {
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
            RequestParams params = new MyRequstParams(user_entity);
            params.addBodyParameter("details", details);
            httpUtils.send(HttpRequest.HttpMethod.POST, MyData.URL_Feedback, params, new HttpCallBack(this, R.string.feedback_ing) {
                @Override
                public void onResult(String result,SweetAlertDialog dialog) {
                    dialog.showText(R.string.feedback_done);
                    dialog.dismiss(3000);
                }
            });
        } else {
            showToast(R.string.fee_content_null);
        }
    }
}
