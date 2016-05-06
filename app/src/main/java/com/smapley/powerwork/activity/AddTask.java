package com.smapley.powerwork.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.smapley.powerwork.R;
import com.smapley.powerwork.adapter.AddTaskAdapter;
import com.smapley.powerwork.db.entity.TaskDetailsEntity;
import com.smapley.powerwork.db.entity.TaskEntity;
import com.smapley.powerwork.db.modes.TaskMode;
import com.smapley.powerwork.db.service.TaskService;
import com.smapley.powerwork.utils.ThreadSleep;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by smapley on 15/10/24.
 */
@ContentView(R.layout.activity_addtask)
public class AddTask extends BaseActivity {
    private static final int GETDATA = 1;
    @ViewInject(R.id.title_tv_name)
    private TextView title_tv_name;
    @ViewInject(R.id.title_iv_done)
    private ImageView title_iv_done;
    @ViewInject(R.id.title_iv_search)
    private ImageView title_iv_search;

    @ViewInject(R.id.add_rv_list)
    private RecyclerView add_rv_list;


    private List<TaskDetailsEntity> add_lis_data;
    private AddTaskAdapter add_aa_adapter;

    private Boolean edit = true;
    private int taskId;
    private String taskName;

    @Override
    protected void initParams() {
        add_rv_list.setLayoutManager(new LinearLayoutManager(this));
        add_lis_data = new ArrayList<>();
        checkType();
    }

    private void checkType() {
        edit = getIntent().getBooleanExtra("edit", true);
        taskId = getIntent().getIntExtra("taskId", 0);
        taskName = getIntent().getStringExtra("taskName");
        if (taskId > 0) {
            title_iv_search.setVisibility(View.VISIBLE);
            title_iv_done.setVisibility(View.GONE);
            title_tv_name.setText(taskName);
            getDataForDb();
        } else {
            title_iv_search.setVisibility(View.GONE);
            title_tv_name.setText(R.string.add);
            title_iv_done.setVisibility(View.VISIBLE);
            add_lis_data.add(new TaskDetailsEntity(5));
            add_lis_data.add(new TaskDetailsEntity(0));
            add_aa_adapter = new AddTaskAdapter(this, add_lis_data, edit);
            add_rv_list.setAdapter(add_aa_adapter);
        }
    }

    public void getDataForDb() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //从数据库获取数据更新界面
                TaskMode taskMode = new TaskService().findById(taskId);
                if (taskMode != null)
                    mhandler.obtainMessage(GETDATA, taskMode).sendToTarget();
            }
        }).start();
    }

    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GETDATA:
                    TaskMode TaskMode = (TaskMode) msg.obj;
                    TaskEntity TaskEntity=TaskMode.getTaskEntity();

                    add_lis_data.clear();
                    add_lis_data.addAll(TaskMode.getListTaskDetailsEntities());
                    add_aa_adapter = new AddTaskAdapter(AddTask.this, add_lis_data, edit);
                    add_rv_list.setAdapter(add_aa_adapter);
                    break;

            }
        }
    };

    @Event({R.id.title_iv_back, R.id.title_iv_done,R.id.title_iv_search})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_iv_search:
                //完成任务
                AlertDialog.Builder builder=new AlertDialog.Builder(AddTask.this);
                builder.setMessage("完成任务？");
                builder.setNegativeButton("取消",null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final ProgressDialog progressDialog=new ProgressDialog(AddTask.this);
                        progressDialog.show();
                        new ThreadSleep().sleep(1000, new ThreadSleep.Callback() {
                            @Override
                            public void onCallback(int number) {
                                progressDialog.dismiss();
                                Toast.makeText(AddTask.this,"修改任务状态成功",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                builder.create().show();
                break;
            case R.id.title_iv_back:
                finish();
                break;
            case R.id.title_iv_done:
                add_rv_list.clearFocus();
                Intent intent = new Intent(AddTask.this, PublishTask.class);
                intent.putExtra("data", JSON.toJSONString(add_aa_adapter.getList()));
                LogUtil.d("---" + JSON.toJSONString(add_aa_adapter.getList()));
                startActivity(intent);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 3:
                //选择图片
                if (resultCode == RESULT_OK) {
                    List<String> resultList = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    for (int i = 0; i < resultList.size(); i++) {
                        TaskDetailsEntity add_item_mode = new TaskDetailsEntity(3);
                        add_item_mode.setPath(resultList.get(i));
                        add_aa_adapter.addItem(add_item_mode);
                        add_aa_adapter.addItem(new TaskDetailsEntity(5));
                    }
                }
                break;
        }
    }
}
