package com.smapley.powerwork.http.service;

import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.powerwork.db.Refresh;
import com.smapley.powerwork.db.RefreshService;
import com.smapley.powerwork.db.entity.UserBaseEntity;
import com.smapley.powerwork.db.modes.TaskMode;
import com.smapley.powerwork.db.service.TaskService;
import com.smapley.powerwork.http.callback.SimpleCallback;
import com.smapley.powerwork.http.params.BaseParams;
import com.smapley.powerwork.utils.MyData;

import org.xutils.x;

import java.util.List;

/**
 * Created by smapley on 15/12/18.
 */
public abstract class TaskListService {

    private final int SAVEDATA = 1;
    private long time;
    private Refresh refresh;
    private RefreshService refreshService;

    public TaskListService() {
        refreshService = new RefreshService();
    }

    public void load(UserBaseEntity userBaseEntity) {
        time = System.currentTimeMillis();
        refresh = refreshService.findById(userBaseEntity.getUseId());
        BaseParams params = new BaseParams(MyData.URL_TaskList, userBaseEntity);
        params.addBodyParameter("time", refresh.getTaskList() + "");
        x.http().post(params, new SimpleCallback() {
            @Override
            public void onSuccess(final String data) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<TaskMode> taskModes = JSON.parseObject(data, new TypeReference<List<TaskMode>>() {
                        });
                        if (taskModes != null && !taskModes.isEmpty()) {
                            TaskService taskService = new TaskService();
                            for (TaskMode taskMode : taskModes) {
                                taskService.save(taskMode);
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
                    refresh.setTaskList(time);
                    refreshService.saveOrUpdate(refresh);
                    onSucceed();
                    break;
            }
        }
    };

    public abstract void onSucceed();
}