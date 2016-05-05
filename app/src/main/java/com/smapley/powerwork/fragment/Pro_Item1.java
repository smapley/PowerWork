package com.smapley.powerwork.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.powerwork.R;
import com.smapley.powerwork.adapter.ProItem1Adapter;
import com.smapley.powerwork.db.entity.DynamicEntity;
import com.smapley.powerwork.db.mode.DynamicMode;
import com.smapley.powerwork.db.service.DynamicService;
import com.smapley.powerwork.http.MyResponse;
import com.smapley.powerwork.http.params.BaseParams;
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
@ContentView(R.layout.fragment_pro_item1)
public class Pro_Item1 extends BaseFragment {


    private static final int GETDATE = 1;
    private static final int SAVEDATA = 2;
    @ViewInject(R.id.pro_item1_rv_list)
    private RecyclerView pro_item1_rv_list;

    private List<DynamicEntity> list;
    private ProItem1Adapter adapter;
    private int pro_id;


    @Override
    protected void initParams(View view) {
        initData();
        initView();
        getDataForDb();
        getDataForWeb();


    }


    private void initData() {
        pro_id = getArguments().getInt("pro_id");
    }

    private void initView() {
        pro_item1_rv_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = new ArrayList<>();
        adapter = new ProItem1Adapter(getActivity(), list);
        pro_item1_rv_list.setAdapter(adapter);
    }

    public void getDataForDb() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<DynamicEntity> listDynamic = dbUtils
                            .selector(DynamicEntity.class)
                            .where("pro_id", "=", pro_id + "")
                            .orderBy("cre_date",true)
                            .findAll();
                    if (listDynamic != null && !listDynamic.isEmpty()) {
                        mhandler.obtainMessage(GETDATE, listDynamic).sendToTarget();
                    }
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void getDataForWeb() {

        BaseParams baseParams = new BaseParams(MyData.URL_DynamicList, userEntity);
        baseParams.addBodyParameter("pro_id", pro_id + "");
        x.http().post(baseParams, new Callback.CommonCallback<MyResponse>() {
            @Override
            public void onSuccess(final MyResponse result) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<DynamicMode> dynamicModes = JSON.parseObject(result.data, new TypeReference<List<DynamicMode>>() {
                        });
                        if (dynamicModes != null && !dynamicModes.isEmpty()) {
                            for (DynamicMode dynamicMode : dynamicModes) {
                                DynamicService.save(dynamicMode);
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
            switch (msg.what) {
                case GETDATE:
                    adapter.addAll((List<DynamicEntity>) msg.obj);
                    break;
                case SAVEDATA:
                    getDataForDb();
                    break;
            }
        }
    };

}
