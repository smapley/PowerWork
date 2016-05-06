package com.smapley.powerwork.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Scene;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.powerwork.R;
import com.smapley.powerwork.application.LocalApplication;
import com.smapley.powerwork.db.Refresh;
import com.smapley.powerwork.db.entity.UserEntity;
import com.smapley.powerwork.db.services.UserService;
import com.smapley.powerwork.http.callback.HttpCallBack;
import com.smapley.powerwork.http.UserParams;
import com.smapley.powerwork.utils.MyData;

import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by smapley on 15/10/26.
 */
@ContentView(R.layout.activity_login)
public class Login extends BaseActivity {

    @ViewInject(R.id.log_ll_content)
    private ViewGroup log_ll_content;

    private ImageView log_ci_pic;
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
        if (userEntity != null) {
            log_st_usernmae = userEntity.getUsername();
            log_st_password = userEntity.getPassword();

        }
        transitionInflater = TransitionInflater.from(this);
        transitionManager = transitionInflater.inflateTransitionManager(R.transition.transition_manager, log_ll_content);
        log_sc_login = Scene.getSceneForLayout(log_ll_content, R.layout.fragment_login, this);
        log_sc_register = Scene.getSceneForLayout(log_ll_content, R.layout.fragment_register, this);
        goToScene1(null);

    }


    private void doRegister() {
        UserParams params = new UserParams(MyData.URL_REGISTER);
        params.username = reg_st_username;
        params.password = reg_st_password;
        params.phone = reg_st_phone;
        x.http().post(params, new HttpCallBack(Login.this, R.string.log_dia_register_ing) {
            @Override
            public void onResult(String result, SweetAlertDialog dialog) {
                dialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE)
                        .showText(R.string.log_dia_register_suc)
                        .commit()
                        .dismiss(2000);
                UserEntity userEntity = JSON.parseObject(result, new TypeReference<UserEntity>() {
                });
                afterLogin(userEntity);
            }
        });


    }

    private void doLogin() {
        UserParams params = new UserParams(MyData.URL_LOGIN);
        params.username = log_st_usernmae;
        params.password = log_st_password;
        x.http().post(params, new HttpCallBack(Login.this, R.string.log_dia_login_ing) {
            @Override
            public void onResult(String result, SweetAlertDialog dialog) {
                dialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE)
                        .showText(R.string.log_dia_login_suc)
                        .commit()
                        .dismiss(2000);
                UserEntity userEntity = JSON.parseObject(result, new TypeReference<UserEntity>() {
                });
                afterLogin(userEntity);
            }
        });
    }

    private void afterLogin(UserEntity userEntity) {
        try {
            SharedPreferences.Editor editor = sp_user.edit();
            editor.putInt("id", userEntity.getUseId());
            editor.putBoolean("islogin", true);
            editor.commit();
            new UserService().save(userEntity);
            //新建刷新表
            Refresh refresh = new Refresh();
            refresh.setUse_id(userEntity.getUseId());
            dbUtils.saveOrUpdate(refresh);
            this.userEntity = userEntity;

            toNextActivity();

        } catch (DbException e) {
            e.printStackTrace();
        }
        toNextActivity();
    }

    private void toNextActivity() {
        startActivity(new Intent(Login.this, MainActivity.class));
        finish();
    }

    public void goToScene1(View view) {
        transitionManager.transitionTo(log_sc_login);
        log_ci_pic = (ImageView) log_sc_login.getSceneRoot().findViewById(R.id.log_ci_pic);
        log_et_username = (EditText) log_sc_login.getSceneRoot().findViewById(R.id.log_et_username);
        log_et_password = (EditText) log_sc_login.getSceneRoot().findViewById(R.id.log_et_password);
        log_et_password.setText(log_st_password);
        log_et_username.setText(log_st_usernmae);
        if (userEntity != null) {
            x.image().bind(log_ci_pic, MyData.URL_PIC + userEntity.getPicUrl(), LocalApplication.getInstance().CirtlesImage);
        } else {
            x.image().bind(log_ci_pic, "assets://logo.png", LocalApplication.getInstance().CirtlesImage);
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
                UserEntity userBaseEntity = null;
                UserEntity userEntity = null;
                try {
                    userBaseEntity = dbUtils.selector(UserEntity.class).where("username", "=", log_et_username.getText().toString()).findFirst();
                } catch (DbException e) {
                    e.printStackTrace();
                }
                if (userBaseEntity != null)
                    try {
                        userEntity = dbUtils.findById(UserEntity.class, userBaseEntity.getUseId());
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                if (userEntity != null) {
                    log_et_password.setText(userBaseEntity.getPassword());
                    x.image().bind(log_ci_pic, MyData.URL_PIC + userEntity.getPicUrl(), LocalApplication.getInstance().CirtlesImage);
                } else {
                    log_et_password.setText("");
                    x.image().bind(log_ci_pic, "assets://logo.png", LocalApplication.getInstance().CirtlesImage);
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
