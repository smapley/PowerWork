package com.smapley.powerwork.activity;

import android.content.SharedPreferences;
import android.transition.Scene;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.smapley.powerwork.R;
import com.smapley.powerwork.utils.ActivityStack;

/**
 * Created by smapley on 15/10/26.
 */
@ContentView(R.layout.activity_login)
public class Login extends BaseActivity {

    @ViewInject(R.id.log_ll_content)
    private ViewGroup log_ll_content;

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
        log_st_usernmae = sp_user.getString("username", "");
        log_st_password = sp_user.getString("password", "");

        transitionInflater = TransitionInflater.from(this);
        transitionManager = transitionInflater.inflateTransitionManager(R.transition.transition_manager, log_ll_content);
        log_sc_login = Scene.getSceneForLayout(log_ll_content, R.layout.fragment_login, this);
        log_sc_register = Scene.getSceneForLayout(log_ll_content, R.layout.fragment_register, this);
        goToScene1(null);
    }

    private void doRegister(){
        SharedPreferences.Editor editor = sp_user.edit();
        editor.putString("username", reg_st_username);
        editor.putString("password", reg_st_password);
        editor.putString("phone", reg_st_phone);
        editor.commit();
        finish();
        ActivityStack.getInstance().finishToActivity(MainActivity.class, true);
    }

    private void doLogin() {
        SharedPreferences.Editor editor = sp_user.edit();
        editor.putString("username", log_st_usernmae);
        editor.putString("password", log_st_password);
        editor.commit();
        finish();
        ActivityStack.getInstance().finishToActivity(MainActivity.class, true);
    }

    public void goToScene1(View view) {
        transitionManager.transitionTo(log_sc_login);
        if(log_et_username==null){
            log_et_username= (EditText) log_sc_login.getSceneRoot().findViewById(R.id.log_et_username);
        }
        if(log_et_password==null){
            log_et_password= (EditText) log_sc_login.getSceneRoot().findViewById(R.id.log_et_password);
        }
        if(view==null) {
            log_et_password.setText(log_st_password);
            log_et_username.setText(log_st_usernmae);
        }
    }


    public void goToScene2(View view) {
        transitionManager.transitionTo(log_sc_register);
        if(reg_et_username==null){
            reg_et_username= (EditText) log_sc_register.getSceneRoot().findViewById(R.id.log_et_username);
        }
        if(reg_et_password==null){
            reg_et_password= (EditText) log_sc_register.getSceneRoot().findViewById(R.id.log_et_password);
        }
        if(reg_et_phone==null){
            reg_et_phone= (EditText) log_sc_register.getSceneRoot().findViewById(R.id.log_et_phone);
        }
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
        reg_st_username=reg_et_username.getText().toString();
        reg_st_password=reg_et_password.getText().toString();
        reg_st_phone=reg_et_phone.getText().toString();
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
