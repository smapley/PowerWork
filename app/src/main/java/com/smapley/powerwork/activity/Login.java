package com.smapley.powerwork.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Scene;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.smapley.powerwork.R;
import com.smapley.powerwork.entity.Result_Entity;
import com.smapley.powerwork.entity.User_Entity;
import com.smapley.powerwork.utils.ActivityStack;
import com.smapley.powerwork.utils.Code;
import com.smapley.powerwork.utils.MyData;
import com.smapley.powerwork.utils.ThreadSleep;
import com.smapley.powerwork.view.CircleImageView;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by smapley on 15/10/26.
 */
@ContentView(R.layout.activity_login)
public class Login extends BaseActivity {

    @ViewInject(R.id.log_ll_content)
    private ViewGroup log_ll_content;

    private CircleImageView log_ci_pic;
    private EditText log_et_username;
    private EditText log_et_password;
    private EditText reg_et_username;
    private EditText reg_et_password;
    private EditText reg_et_phone;

    private String log_st_usernmae;
    private String log_st_password;
    private String reg_st_password;
    private String reg_st_username;
    private String reg_st_phone;

    private TransitionInflater transitionInflater;
    private TransitionManager transitionManager;
    private Scene log_sc_login;
    private Scene log_sc_register;

    @Override
    protected void initParams() {
        if (user_entity != null) {
            log_st_usernmae = user_entity.getUsername();
            log_st_password = Code.doCode(user_entity.getPassword(), user_entity.getCre_date());
        }
        transitionInflater = TransitionInflater.from(this);
        transitionManager = transitionInflater.inflateTransitionManager(R.transition.transition_manager, log_ll_content);
        log_sc_login = Scene.getSceneForLayout(log_ll_content, R.layout.fragment_login, this);
        log_sc_register = Scene.getSceneForLayout(log_ll_content, R.layout.fragment_register, this);
        goToScene1(null);

    }


    private void doRegister() {
        dialog = new SweetAlertDialog(this);
        RequestParams params = new RequestParams();
        params.addBodyParameter("username", reg_st_username);
        params.addBodyParameter("password", reg_st_password);
        params.addBodyParameter("phone", reg_st_phone);
        httpUtils.send(HttpRequest.HttpMethod.POST, MyData.URL_REGISTER, params, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                dialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
                dialog.showText(R.string.log_dia_register_ing);
                dialog.show();

            }

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Result_Entity result_entity = JSON.parseObject(responseInfo.result, new TypeReference<Result_Entity>() {
                });
                LogUtils.i(responseInfo.result);
                if (MyData.SUCC.equals(result_entity.getFlag())) {
                    dialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE)
                            .showText(R.string.log_dia_register_suc)
                            .commit()
                            .dismiss(2000);
                    User_Entity user_entity = JSON.parseObject(result_entity.getData(), new TypeReference<User_Entity>() {
                    });
                    afterRegister(user_entity);

                } else {
                    dialog.changeAlertType(SweetAlertDialog.ERROR_TYPE)
                            .showText(result_entity.getDetails())
                            .showConfirmButton()
                            .commit();
                }

            }

            @Override
            public void onFailure(HttpException error, String msg) {
                dialog.changeAlertType(SweetAlertDialog.ERROR_TYPE)
                        .showText(R.id.connect_fai)
                        .commit()
                        .dismiss(2000);
                error.printStackTrace();

            }
        });


    }

    private void doLogin() {
        dialog = new SweetAlertDialog(this);
        RequestParams params = new RequestParams();
        params.addBodyParameter("username", log_st_usernmae);
        params.addBodyParameter("password", log_st_password);
        httpUtils.send(HttpRequest.HttpMethod.POST, MyData.URL_LOGIN, params, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                dialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
                dialog.showText(R.string.log_dia_login_ing);
                dialog.show();

            }

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Result_Entity result_entity = JSON.parseObject(responseInfo.result, new TypeReference<Result_Entity>() {
                });
                if (MyData.SUCC.equals(result_entity.getFlag())) {
                    dialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE)
                            .showText(R.string.log_dia_login_suc)
                            .commit()
                            .dismiss(2000);
                    User_Entity user_entity = JSON.parseObject(result_entity.getData(), new TypeReference<User_Entity>() {
                    });
                    afterLogin(user_entity);

                } else {
                    dialog.changeAlertType(SweetAlertDialog.ERROR_TYPE)
                            .showText(result_entity.getDetails())
                            .showConfirmButton()
                            .commit();
                }
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                error.printStackTrace();
                dialog.changeAlertType(SweetAlertDialog.ERROR_TYPE)
                        .showText(R.id.connect_fai)
                        .commit()
                        .dismiss(2000);

            }
        });

    }

    private void afterRegister(User_Entity user_entity) {
        log_st_usernmae = user_entity.getUsername();
        log_st_password = Code.doCode(user_entity.getPassword(), user_entity.getCre_date());
        goToScene1(null);
        new ThreadSleep().sleep(2000, new ThreadSleep.Calback() {
            @Override
            public void onCalback(int number) {
                doLogin();
            }
        });

    }

    private void afterLogin(User_Entity user_entity) {
        try {
            SharedPreferences.Editor editor = sp_user.edit();
            editor.putInt("id", user_entity.getUse_id());
            editor.putBoolean("islogin", true);
            editor.commit();
            dbUtils.deleteById(User_Entity.class, user_entity.getUse_id());
            dbUtils.save(user_entity);
            this.user_entity = user_entity;

            toNextActivity();

        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    private void toNextActivity() {
        startActivity(new Intent(Login.this, MainActivity.class));
        finish();
    }

    public void goToScene1(View view) {
        transitionManager.transitionTo(log_sc_login);
        log_ci_pic = (CircleImageView) log_sc_login.getSceneRoot().findViewById(R.id.log_ci_pic);
        log_et_username = (EditText) log_sc_login.getSceneRoot().findViewById(R.id.log_et_username);
        log_et_password = (EditText) log_sc_login.getSceneRoot().findViewById(R.id.log_et_password);
        log_et_password.setText(log_st_password);
        log_et_username.setText(log_st_usernmae);
        if (user_entity != null) {
            asyncImageLoader.loadBitmaps(log_ci_pic, user_entity.getPic_url());
        }
        log_et_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                User_Entity user_entity = null;
                try {
                    user_entity = dbUtils.findFirst(Selector.from(User_Entity.class).where("username", "=", log_et_username.getText().toString()));
                } catch (DbException e) {
                    e.printStackTrace();
                }
                if (user_entity != null) {
                    log_et_password.setText(Code.doCode(user_entity.getPassword(), user_entity.getCre_date()));
                    asyncImageLoader.loadBitmaps( log_ci_pic, user_entity.getPic_url());
                } else {
                    log_et_password.setText("");
                    log_ci_pic.setImageResource(R.mipmap.logo);
                }
            }
        });
    }


    public void goToScene2(View view) {
        transitionManager.transitionTo(log_sc_register);
        reg_et_username = (EditText) log_sc_register.getSceneRoot().findViewById(R.id.log_et_username);
        reg_et_password = (EditText) log_sc_register.getSceneRoot().findViewById(R.id.log_et_password);
        reg_et_phone = (EditText) log_sc_register.getSceneRoot().findViewById(R.id.log_et_phone);
    }

    public void checkLogin(View view) {
        log_st_usernmae = log_et_username.getText().toString();
        log_st_password = log_et_password.getText().toString();
        if (log_st_usernmae != null && !log_st_usernmae.equals("")) {
            if (log_st_password != null && !log_st_password.equals("")) {
                doLogin();
            } else {
                showToast(R.string.log_null_password);
            }
        } else {
            showToast(R.string.log_null_username);
        }
    }

    public void checkRegister(View view) {
        reg_st_username = reg_et_username.getText().toString();
        reg_st_password = reg_et_password.getText().toString();
        reg_st_phone = reg_et_phone.getText().toString();
        if (reg_st_username != null && !reg_st_username.equals("")) {
            if (reg_st_password != null && !reg_st_password.equals("")) {
                if (reg_st_phone != null && !reg_st_phone.equals("")) {
                    doRegister();
                } else {
                    showToast(R.string.log_null_phone);
                }
            } else {
                showToast(R.string.log_null_password);
            }
        } else {
            showToast(R.string.log_null_username);
        }
    }

}
