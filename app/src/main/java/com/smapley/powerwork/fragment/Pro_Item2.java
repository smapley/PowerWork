package com.smapley.powerwork.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.powerwork.R;
import com.smapley.powerwork.adapter.ProItem2Adapter;
import com.smapley.powerwork.entity.PTaskEntity;
import com.smapley.powerwork.http.BaseParams;
import com.smapley.powerwork.http.MyResponse;
import com.smapley.powerwork.mode.BaseMode;
import com.smapley.powerwork.mode.Pro_Item2_Group_Mode;
import com.smapley.powerwork.utils.MyData;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smapley on 15/11/16.
 */
@ContentView(R.layout.fragment_pro_item2)
public class Pro_Item2 extends BaseFragment {

    @ViewInject(R.id.pro_item2_rv_list)
    private RecyclerView pro_item2_rv_list;

    private ProItem2Adapter adapter;

    private List<BaseMode> listData;

    private int pro_id;

    @Override
    protected void initParams(View view) {

        initView();
        initData();
        setView();
        getData();


    }

    private void initView() {
        pro_item2_rv_list.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    private void initData() {
        listData = new ArrayList<>();
        Pro_Item2_Group_Mode myGroup = new Pro_Item2_Group_Mode();
        myGroup.setName(getString(R.string.pro_item2_group_mytask));
        myGroup.setIsShowAdd(true);
        listData.add(myGroup);

        Pro_Item2_Group_Mode otherGroup = new Pro_Item2_Group_Mode();
        otherGroup.setName(getString(R.string.pro_item2_group_othertask));
        otherGroup.setIsShowAdd(false);
        listData.add(otherGroup);
        pro_id = getArguments().getInt("pro_id");
    }

    private void setView() {
        adapter = new ProItem2Adapter(getActivity(), listData);
        pro_item2_rv_list.setAdapter(adapter);
        LogUtil.d("------");
    }

    public void getData() {
        BaseParams params = new BaseParams(MyData.URL_PTask, user_entity);
        params.addBodyParameter("pro_id", pro_id + "");
        x.http().post(params, new Callback.CommonCallback<MyResponse>() {
            @Override
            public void onSuccess(MyResponse result) {
                if (result.flag.equals(MyData.SUCC)) {
                    List<PTaskEntity> listResult = JSON.parseObject(result.data, new TypeReference<List<PTaskEntity>>() {
                    });
                    adapter.addAll(listResult);
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
