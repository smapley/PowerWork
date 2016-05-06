package com.smapley.powerwork.fragment;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.powerwork.R;
import com.smapley.powerwork.activity.Search;
import com.smapley.powerwork.adapter.SearchAdapter;
import com.smapley.powerwork.db.modes.ProUseMode;
import com.smapley.powerwork.db.service.ProUseService;
import com.smapley.powerwork.http.MyResponse;
import com.smapley.powerwork.http.params.BaseParams;
import com.smapley.powerwork.mode.SearchMode;
import com.smapley.powerwork.utils.MyData;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smapley on 15/11/16.
 */
@ContentView(R.layout.fragment_pro_item5)
public class Pro_Item5 extends BaseFragment {


    private static final int GETDATAFORDB = 1;
    @ViewInject(R.id.pro_item5_rv_list)
    private RecyclerView sea_rv_list;

    private SearchAdapter adapter;
    private int pro_id;

    @Override
    protected void initParams(View view) {
        pro_id = getArguments().getInt("pro_id");
        getDataForWeb();
        getDataForDb();
    }

    @Override
    public void getDataForDb() {
        List<ProUseMode> proUseModes=  ProUseService.findByProId(pro_id);
        List<SearchMode> searchModes=new ArrayList<SearchMode>();
        for(ProUseMode proUseMode:proUseModes){
            SearchMode mode=new SearchMode(proUseMode.getUserEntity());
            searchModes.add(mode);
        }

        adapter = new SearchAdapter(getActivity(), searchModes,0);
        sea_rv_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        sea_rv_list.setAdapter(adapter);
    }


    @Override
    public void getDataForWeb() {
        BaseParams params = new BaseParams(MyData.URL_TeamList, userEntity);
        params.addBodyParameter("projectId", pro_id + "");
        x.http().post(params, new Callback.CommonCallback<MyResponse>() {
            @Override
            public void onSuccess(MyResponse result) {
                Log.d("result",result.data);
                final List<ProUseMode> proUseModes = JSON.parseObject(result.data, new TypeReference<List<ProUseMode>>() {
                });
                if (proUseModes != null && !proUseModes.isEmpty()) {
                    List<SearchMode> searchModes=new ArrayList<SearchMode>();
                    for(ProUseMode proUseMode:proUseModes){
                        SearchMode mode=new SearchMode(proUseMode.getUserEntity());
                        searchModes.add(mode);
                    }

                    adapter = new SearchAdapter(getActivity(), searchModes,0);
                    sea_rv_list.setLayoutManager(new LinearLayoutManager(getActivity()));
                    sea_rv_list.setAdapter(adapter);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //添加新表
                            for (ProUseMode proUseMode:proUseModes) {
                                ProUseService.save(proUseMode);

                            }
                            mhandler.obtainMessage(GETDATAFORDB).sendToTarget();
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
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GETDATAFORDB:
                    getDataForDb();
                    break;
            }
        }
    };

    @Event({R.id.pro_item5_tv_add})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.pro_item5_tv_add:
                Intent intent = new Intent(getActivity(), Search.class);
                intent.putExtra("type", 0);
                intent.putExtra("projectId",pro_id);
                startActivity(intent);
                break;
        }
    }
}
