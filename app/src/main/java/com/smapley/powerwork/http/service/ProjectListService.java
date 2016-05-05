package com.smapley.powerwork.http.service;

import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.powerwork.db.Refresh;
import com.smapley.powerwork.db.RefreshService;
import com.smapley.powerwork.db.entity.UserEntity;
import com.smapley.powerwork.db.modes.ProjectMode;
import com.smapley.powerwork.db.service.ProjectService;
import com.smapley.powerwork.http.callback.SimpleCallback;
import com.smapley.powerwork.http.params.BaseParams;
import com.smapley.powerwork.utils.MyData;

import org.xutils.x;

import java.util.List;

/**
 * Created by smapley on 15/12/18.
 */
public abstract class ProjectListService {

    private final int SAVEDATA = 1;
    private long time;
    private Refresh refresh;
    private RefreshService refreshService;

    public ProjectListService() {
        refreshService = new RefreshService();
    }

    public void load(UserEntity userEntity) {
        time = System.currentTimeMillis();
        refresh = refreshService.findById(userEntity.getUseId());
        BaseParams params = new BaseParams(MyData.URL_ProjectList, userEntity);
        params.addBodyParameter("time", refresh.getProjectList() + "");
        x.http().post(params, new SimpleCallback() {
            @Override
            public void onSuccess(final String data) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<ProjectMode> projectModes = JSON.parseObject(data, new TypeReference<List<ProjectMode>>() {
                        });
                        if (projectModes != null && !projectModes.isEmpty()) {
                            for (ProjectMode projectMode : projectModes) {
                                ProjectService.save(projectMode);
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
                    refresh.setProjectList(time);
                    refreshService.saveOrUpdate(refresh);
                    onSucceed();
                    break;
            }
        }
    };

    public abstract void onSucceed();
}