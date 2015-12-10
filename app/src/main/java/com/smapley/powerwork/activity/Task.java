package com.smapley.powerwork.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.powerwork.R;
import com.smapley.powerwork.adapter.CalAdapter;
import com.smapley.powerwork.entity.TaskEntity;
import com.smapley.powerwork.http.BaseParams;
import com.smapley.powerwork.http.MyResponse;
import com.smapley.powerwork.mode.BaseMode;
import com.smapley.powerwork.utils.MyData;

import org.xutils.common.Callback;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smapley on 15/10/29.
 */
@ContentView(R.layout.activity_task)
public class Task extends BaseActivity {

    private static final int GETDATA= 1;
    private static final int SAVEDATA = 2;
    @ViewInject(R.id.title_tv_name)
    private TextView title_tv_name;

    @ViewInject(R.id.tas_rv_list)
    private RecyclerView tas_rv_list;

    private List<BaseMode> tas_lis_data;
    private CalAdapter calAdapter;

    @Override
    protected void initParams() {

        initView();
        getDataForDb();
        getDataForWeb();


    }

    private void initView() {
        title_tv_name.setText(R.string.task);
        tas_rv_list.setLayoutManager(new LinearLayoutManager(this));
        tas_lis_data = new ArrayList<>();
        calAdapter = new CalAdapter(this, tas_lis_data);
        tas_rv_list.setAdapter(calAdapter);
    }

    @Event({R.id.title_iv_back})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_iv_back:
                finish();
                break;
        }
    }

    public void getDataForDb() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<TaskEntity> listTask = dbUtils.selector(TaskEntity.class).orderBy("end_date").findAll();
                    mhandler.obtainMessage(GETDATA, listTask).sendToTarget();
                } catch (DbException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public void getDataForWeb() {
        BaseParams params = new BaseParams(MyData.URL_TaskList, user_entity);
        x.http().post(params, new Callback.CommonCallback<MyResponse>() {
            @Override
            public void onSuccess(MyResponse result) {
                final List<TaskEntity> listTask = JSON.parseObject(result.data, new TypeReference<List<TaskEntity>>() {
                });
                if (listTask != null && !listTask.isEmpty()) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //添加新表
                            for (TaskEntity taskEntity : listTask) {
                                try {
                                    //添加Task
                                    dbUtils.saveOrUpdate(taskEntity);
                                } catch (DbException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    //添加TasUse
                                    dbUtils.saveOrUpdate(taskEntity.getTasUseEntity());
                                } catch (DbException e) {
                                    e.printStackTrace();
                                }
                            }
                            mhandler.obtainMessage(SAVEDATA).sendToTarget();
                        }
                    }).start();

                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GETDATA:
                    calAdapter.addAll((List<BaseMode>) msg.obj);
                    break;
                case SAVEDATA:
                    getDataForDb();
                    break;
            }
        }
    };
}
