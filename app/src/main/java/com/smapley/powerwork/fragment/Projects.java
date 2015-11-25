package com.smapley.powerwork.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.powerwork.R;
import com.smapley.powerwork.adapter.ProjectsAdapter;
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


    @ViewInject(R.id.pros_rv_list)
    private RecyclerView pros_rv_list;


    private ProjectsAdapter adapter;

    private List<BaseMode> listProject;

    @Override
    protected void initParams(View view) {
        pros_rv_list.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        adapter = new ProjectsAdapter(getActivity());
        pros_rv_list.setAdapter(adapter);
        initData();
        getData();




    }

    private void initData() {
        listProject = new ArrayList<>();
        try {
            //加载数据库缓存的project
            List<ProjectEntity> listProUse = dbUtils.findAll(ProjectEntity.class);
            if(listProUse!=null) {
                listProject.clear();
                listProject.addAll(listProUse);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }

        adapter.addAll(listProject);
    }

    public void getData() {
        BaseParams params = new BaseParams(MyData.URL_ProjectList, user_entity);
        x.http().post(params, new Callback.CommonCallback<MyResponse>() {

            @Override
            public void onSuccess(MyResponse result) {
                if (result.flag.equals(MyData.SUCC)) {
                    List<ProjectEntity> listData = JSON.parseObject(result.data, new TypeReference<List<ProjectEntity>>() {
                    });
                    //删除project表
                    try {
                        dbUtils.delete(ProjectEntity.class);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                    //添加新获取的表
                    for (ProjectEntity entity:listData){
                        try {
                            dbUtils.save(entity);
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    }
                    listProject.clear();
                    listProject.addAll(listData);
                    adapter.addAll(listProject);
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
}
