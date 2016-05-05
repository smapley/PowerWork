package com.smapley.powerwork.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.powerwork.R;
import com.smapley.powerwork.activity.Project;
import com.smapley.powerwork.adapter.ProjectsAdapter;
import com.smapley.powerwork.db.entity.ProjectEntity;
import com.smapley.powerwork.db.modes.ProjectMode;
import com.smapley.powerwork.db.service.ProjectService;
import com.smapley.powerwork.http.callback.HttpCallBack;
import com.smapley.powerwork.http.params.BaseParams;
import com.smapley.powerwork.http.service.ProjectListService;
import com.smapley.powerwork.mode.BaseMode;
import com.smapley.powerwork.mode.Pro_AddItem_Mode;
import com.smapley.powerwork.utils.MyData;
import com.smapley.powerwork.utils.ThreadSleep;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by smapley on 15/11/16.
 */
@ContentView(R.layout.fragment_projects)
public class Projects extends BaseFragment {


    private static final int GETDATA = 1;
    @ViewInject(R.id.pros_rv_list)
    private RecyclerView pros_rv_list;


    private ProjectsAdapter adapter;

    private List<BaseMode> listProject;

    private ProjectListService projectListService = new ProjectListService() {
        @Override
        public void onSucceed() {
            getDataForDb();
        }
    };

    @Override
    protected void initParams(View view) {
        initView();
        getDataForDb();
        getDataForWeb();
    }


    private void initView() {
        pros_rv_list.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        listProject = new ArrayList<>();
        listProject.add(new Pro_AddItem_Mode());
        adapter = new ProjectsAdapter(getActivity(), listProject);
        pros_rv_list.setAdapter(adapter);
    }

    public void getDataForDb() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //加载数据库缓存的project
                List<ProjectEntity> listProject = ProjectService.findEntityByUseId(userEntity.getUseId());
                if (listProject != null)
                    mhandler.obtainMessage(GETDATA, listProject).sendToTarget();
            }
        }).start();
    }

    public void addProject(String data) {
        BaseParams params = new BaseParams(MyData.URL_AddProject, userEntity);
        params.addBodyParameter("name", data);
        x.http().post(params, new HttpCallBack(getActivity(), R.string.addproject_ing) {
            @Override
            public void onResult(String result, SweetAlertDialog dialog) {
                dialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                dialog.showText(R.string.addproject_ed).commit().dismiss(2000);
                final ProjectMode projectMode = JSON.parseObject(result, new TypeReference<ProjectMode>() {
                });
                new ProjectService().save(projectMode);
                getDataForDb();
                new ThreadSleep().sleep(2000, new ThreadSleep.Callback() {
                    @Override
                    public void onCallback(int number) {
                        Intent intent = new Intent(getActivity(), Project.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("pro_id", projectMode.getProjectEntity().getPro_id());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    public void getDataForWeb() {
        projectListService.load(userEntity);
    }


    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GETDATA:
                    adapter.addAll((List<ProjectEntity>) msg.obj);
                    break;
            }
        }
    };

}
