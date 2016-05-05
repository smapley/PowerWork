package com.smapley.powerwork.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.smapley.powerwork.R;
import com.smapley.powerwork.adapter.CalAdapter;
import com.smapley.powerwork.db.entity.TaskEntity;
import com.smapley.powerwork.db.service.TaskService;
import com.smapley.powerwork.http.service.TaskListService;
import com.smapley.powerwork.mode.BaseMode;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

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

    private TaskListService taskListService=new TaskListService() {
        @Override
        public void onSucceed() {
            getDataForDb();
        }
    };

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
                    List<TaskEntity> listTask = TaskService.findByUseId(userEntity.getUseId());
                    mhandler.obtainMessage(GETDATA, listTask).sendToTarget();

            }
        }).start();
    }

    public void getDataForWeb() {
        taskListService.load(userEntity);
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
