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
import com.smapley.powerwork.adapter.NoteAdapter;
import com.smapley.powerwork.entity.NoteEntity;
import com.smapley.powerwork.http.BaseParams;
import com.smapley.powerwork.http.MyResponse;
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
@ContentView(R.layout.activity_notes)
public class Notes extends BaseActivity {

    private static final int GETDATA = 1;
    private static final int SAVEDATA = 2;
    @ViewInject(R.id.title_tv_name)
    private TextView title_tv_name;

    @ViewInject(R.id.not_rv_list)
    private RecyclerView not_rv_list;

    private List<NoteEntity> not_lis_data;
    private NoteAdapter not_pa_adapter;


    @Override
    protected void initParams() {
        initView();
        getDataForDb();
        getDataForWeb();


    }

    private void getDataForWeb() {
        BaseParams baseParams=new BaseParams(MyData.URL_NoteList,user_entity);
        x.http().post(baseParams, new Callback.CommonCallback<MyResponse>() {
            @Override
            public void onSuccess(final MyResponse result) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<NoteEntity> listNote= JSON.parseObject(result.data,new TypeReference<List<NoteEntity>>(){});
                        if(listNote!=null&&!listNote.isEmpty()){
                            for(NoteEntity noteEntity:listNote){
                                try {
                                    dbUtils.saveOrUpdate(noteEntity);
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

    private void getDataForDb() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<NoteEntity> listNote = dbUtils.selector(NoteEntity.class).orderBy("cre_date").findAll();
                    if (listNote != null && !listNote.isEmpty())
                        mhandler.obtainMessage(GETDATA, listNote).sendToTarget();
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void initView() {
        title_tv_name.setText(R.string.notes);
        not_rv_list.setLayoutManager(new LinearLayoutManager(this));
        not_lis_data = new ArrayList<>();
        not_pa_adapter = new NoteAdapter(this, not_lis_data);
        not_rv_list.setAdapter(not_pa_adapter);
    }

    @Event({R.id.title_iv_back})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_iv_back:
                finish();
                break;
        }
    }

    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case GETDATA:
                    not_pa_adapter.addAll((List<NoteEntity>) msg.obj);
                    break;
                case SAVEDATA:
                    getDataForDb();
                    break;
            }
        }
    };
}
