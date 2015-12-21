package com.smapley.powerwork.http.service;

import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.powerwork.application.LocalApplication;
import com.smapley.powerwork.db.Refresh;
import com.smapley.powerwork.db.RefreshService;
import com.smapley.powerwork.db.entity.MessageEntity;
import com.smapley.powerwork.db.entity.UserBaseEntity;
import com.smapley.powerwork.http.callback.SimpleCallback;
import com.smapley.powerwork.http.params.BaseParams;
import com.smapley.powerwork.utils.MyData;

import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.List;

/**
 * Created by smapley on 15/12/18.
 */
public abstract class MessageListService {

    private final int SAVEDATA = 1;
    private long time;
    private Refresh refresh;
    private RefreshService refreshService;

    public MessageListService() {
        refreshService = new RefreshService();
    }

    public void load(UserBaseEntity userBaseEntity) {
        time = System.currentTimeMillis();
        refresh = refreshService.findById(userBaseEntity.getUseId());
        BaseParams params = new BaseParams(MyData.URL_MessageList, userBaseEntity);
        params.addBodyParameter("time", refresh.getMessageList() + "");
        x.http().post(params, new SimpleCallback() {
            @Override
            public void onSuccess(final String data) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<MessageEntity> messageEntities = JSON.parseObject(data, new TypeReference<List<MessageEntity>>() {
                        });
                        if (messageEntities != null && !messageEntities.isEmpty()) {
                            for (MessageEntity messageEntity : messageEntities) {
                                try {
                                    LocalApplication.getInstance().dbUtils.saveOrUpdate(messageEntity);
                                } catch (DbException e) {
                                    e.printStackTrace();
                                }
                            }
                            mhandler.obtainMessage(SAVEDATA).sendToTarget();
                        }
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
                    refresh.setMessageList(time);
                    refreshService.saveOrUpdate(refresh);
                    onSucceed();
                    break;
            }
        }
    };

    public abstract void onSucceed();
}