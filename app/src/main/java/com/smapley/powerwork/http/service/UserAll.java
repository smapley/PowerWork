package com.smapley.powerwork.http.service;

import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.powerwork.db.entity.UserBaseEntity;
import com.smapley.powerwork.db.entity.UserEntity;
import com.smapley.powerwork.db.mode.UserMode;
import com.smapley.powerwork.db.service.UserService;
import com.smapley.powerwork.http.params.BaseParams;
import com.smapley.powerwork.http.callback.SimpleCallback;
import com.smapley.powerwork.utils.MyData;

import org.xutils.x;

/**
 * Created by smapley on 15/12/18.
 */
public abstract class UserAll {

    private static final int SAVEDATA = 1;

    public void load(UserBaseEntity userBaseEntity) {
        BaseParams params = new BaseParams(MyData.URL_UserAll, userBaseEntity);
        x.http().post(params, new SimpleCallback() {
            @Override
            public void onFail() {
                onFailed();
            }

            @Override
            public void onError(String flag, String details) {
                onError(flag, details);
            }

            @Override
            public void onSuccess(final String data) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        UserMode userMode = JSON.parseObject(data, new TypeReference<UserMode>() {
                        });
                        new UserService().save(userMode);
                        if (userMode.getUserEntity() != null)
                            mhandler.obtainMessage(SAVEDATA, userMode.getUserEntity()).sendToTarget();
                        else
                            mhandler.obtainMessage(SAVEDATA, null).sendToTarget();
                    }
                }).start();
            }
        });
    }

    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SAVEDATA:
                    onSucceed((UserEntity) msg.obj);
                    break;
            }
        }
    };

    public abstract void onFailed();

    public abstract void onError(String flag, String details);

    public abstract void onSucceed(UserEntity user);
}