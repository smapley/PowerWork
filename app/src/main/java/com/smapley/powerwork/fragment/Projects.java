package com.smapley.powerwork.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.powerwork.R;
import com.smapley.powerwork.adapter.ProjectsAdapter;
import com.smapley.powerwork.entity.ProUserEntity;
import com.smapley.powerwork.entity.ProjectEntity;
import com.smapley.powerwork.http.BaseParams;
import com.smapley.powerwork.http.MyResponse;
import com.smapley.powerwork.mode.BaseMode;
import com.smapley.powerwork.utils.MyData;

import org.xutils.common.Callback;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smapley on 15/11/16.
 */
@ContentView(R.layout.fragment_projects)
public class Projects extends BaseFragment {


    private static final int GETDATA = 1;
    private static final int SAVEDATA = 2;
    @ViewInject(R.id.pros_rv_list)
    private RecyclerView pros_rv_list;


    private ProjectsAdapter adapter;

    private List<BaseMode> listProject;

    @Override
    protected void initParams(View view) {
        initView();
        getDataForDb();
        getDataForWeb();


    }

    private void initView() {
        pros_rv_list.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        listProject = new ArrayList<>();
        adapter = new ProjectsAdapter(getActivity(), listProject);
        pros_rv_list.setAdapter(adapter);
    }

    private void getDataForDb() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //加载数据库缓存的project
                    List<ProjectEntity> listProject = dbUtils.selector(ProjectEntity.class).orderBy("cre_date", true).findAll();
                    if(listProject!=null&&!listProject.isEmpty())
                        mhandler.obtainMessage(GETDATA,listProject).sendToTarget();
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void getDataForWeb() {
        BaseParams params = new BaseParams(MyData.URL_ProjectList, user_entity);
        x.http().post(params, new Callback.CommonCallback<MyResponse>() {

            @Override
            public void onSuccess(final MyResponse result) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (result.flag.equals(MyData.SUCC)) {
                            List<ProjectEntity> listData = JSON.parseObject(result.data, new TypeReference<List<ProjectEntity>>() {
                            });
                            //添加新获取的表
                            for (ProjectEntity entity : listData) {
                                try {
                                    dbUtils.saveOrUpdate(entity);
                                    if (entity.getListProUse() != null && !entity.getListProUse().isEmpty()) {
                                        for (ProUserEntity prouser : entity.getListProUse()) {
                                            try {
                                                dbUtils.replace(prouser);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }

                                } catch (DbException e) {
                                    e.printStackTrace();
                                }
                            }
                            mhandler.obtainMessage(SAVEDATA).sendToTarget();
                        }
                    }
                }).start();
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
            switch (msg.what){
                case GETDATA:
                    adapter.addAll((List<ProjectEntity>) msg.obj);
                    break;
                case SAVEDATA:
                    getDataForDb();
                    break;
            }
        }
    };

}
