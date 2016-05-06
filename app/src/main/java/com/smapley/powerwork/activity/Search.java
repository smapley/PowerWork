package com.smapley.powerwork.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.powerwork.R;
import com.smapley.powerwork.adapter.SearchAdapter;
import com.smapley.powerwork.db.entity.ProjectEntity;
import com.smapley.powerwork.db.entity.UserEntity;
import com.smapley.powerwork.http.callback.HttpCallBack;
import com.smapley.powerwork.http.params.BaseParams;
import com.smapley.powerwork.mode.SearchMode;
import com.smapley.powerwork.utils.MyData;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by smapley on 15/11/17.
 */
@ContentView(R.layout.activity_search)
public class Search extends BaseActivity {

    @ViewInject(R.id.title_tv_name)
    private TextView title_tv_name;
    @ViewInject(R.id.title_iv_search)
    private ImageView title_iv_search;

    @ViewInject(R.id.sea_ev_name)
    private EditText sea_ev_name;
    @ViewInject(R.id.sea_rv_list)
    private RecyclerView sea_rv_list;

    private int type;

    private SearchAdapter adapter;
    private String data;
    private int projectId;

    @Override
    protected void initParams() {
        type = getIntent().getIntExtra("type", 0);
        projectId=getIntent().getIntExtra("projectId",0);
        initView();

    }

    private void initView() {
        title_iv_search.setVisibility(View.VISIBLE);
        title_tv_name.setText(R.string.search);
        sea_rv_list.setLayoutManager(new LinearLayoutManager(this));

    }

    private void getDataForWeb() {
        switch (type){
            case 0:
                BaseParams params1 = new BaseParams(MyData.URL_SearchProject, userEntity);
                params1.addBodyParameter("data", data);
                params1.addBodyParameter("type",0+"");
                x.http().post(params1, new HttpCallBack(this, R.string.search_ing) {
                    @Override
                    public void onResult(String result, SweetAlertDialog dialog) {
                        dialog.dismiss();
                        List<UserEntity> list = JSON.parseObject(result, new TypeReference<List<UserEntity>>() {
                        });
                        List<SearchMode> searchModes=new ArrayList<SearchMode>();
                        for(UserEntity entity:list){
                            try{
                                dbUtils.saveOrUpdate(entity);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            SearchMode searchMode=new SearchMode(entity);
                            searchModes.add(searchMode);
                        }
                        adapter = new SearchAdapter(Search.this, searchModes,projectId);
                        sea_rv_list.setAdapter(adapter);
                    }
                });
                break;
            case 1:
                BaseParams params = new BaseParams(MyData.URL_SearchProject, userEntity);
                params.addBodyParameter("data", data);
                params.addBodyParameter("type",1+"");
                x.http().post(params, new HttpCallBack(this, R.string.search_ing) {
                    @Override
                    public void onResult(String result, SweetAlertDialog dialog) {
                        dialog.dismiss();
                        List<ProjectEntity> list = JSON.parseObject(result, new TypeReference<List<ProjectEntity>>() {
                        });
                        List<SearchMode> searchModes=new ArrayList<SearchMode>();
                        for(ProjectEntity entity:list){
                            try{
                                dbUtils.saveOrUpdate(entity);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            SearchMode searchMode=new SearchMode(entity);
                            searchModes.add(searchMode);
                        }
                        adapter = new SearchAdapter(Search.this, searchModes,projectId);
                        sea_rv_list.setAdapter(adapter);
                    }
                });
                break;
        }
    }

    @Event({R.id.title_iv_back, R.id.title_iv_search})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_iv_back:
                finish();
                break;
            case R.id.title_iv_search:
                checkName();
                break;
        }
    }

    private void checkName() {
        data = sea_ev_name.getText().toString();
        if (data != null && !data.isEmpty()) {
            getDataForWeb();
        } else {
            showToast(R.string.searchnull);
        }
    }

    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
}
