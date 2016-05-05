package com.smapley.powerwork.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.powerwork.R;
import com.smapley.powerwork.adapter.ProItem2Adapter;
import com.smapley.powerwork.db.entity.TaskEntity;
import com.smapley.powerwork.http.MyResponse;
import com.smapley.powerwork.http.params.BaseParams;
import com.smapley.powerwork.mode.BaseMode;
import com.smapley.powerwork.mode.Pro_Item2_Group_Mode;
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
@ContentView(R.layout.fragment_pro_item2)
public class Pro_Item2 extends BaseFragment {

    private static final int GETMYTASK = 1;
    private static final int GETOTHERTASK = 2;
    private static final int SAVEMYTASK = 3;
    private static final int SAVEOTHERTASK = 4;
    @ViewInject(R.id.pro_item2_rv_list)
    private RecyclerView pro_item2_rv_list;

    private ProItem2Adapter adapter;

    private List<BaseMode> listData;

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

        listData = new ArrayList<>();
        Pro_Item2_Group_Mode myGroup = new Pro_Item2_Group_Mode();
        myGroup.setName(getString(R.string.pro_item2_group_mytask));
        myGroup.setIsShowAdd(true);
        listData.add(myGroup);

        Pro_Item2_Group_Mode otherGroup = new Pro_Item2_Group_Mode();
        otherGroup.setName(getString(R.string.pro_item2_group_othertask));
        otherGroup.setIsShowAdd(false);
        listData.add(otherGroup);
    }

    private void initView() {
        pro_item2_rv_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ProItem2Adapter(getActivity(), listData);
        pro_item2_rv_list.setAdapter(adapter);
    }

    public void getDataForDb() {
        getMyTaskForDb();
        getOtherTaskForDb();


    }

    private void getOtherTaskForDb() {
        new Thread(new Runnable() {
            @Override
            public void run() {
//                try {
//                    List<OtherTaskEntity> listTask=dbUtils.selector(OtherTaskEntity.class).where("pro_id","=",pro_id+"").findAll();
//                    if(listTask!=null&&!listTask.isEmpty())
//                        mhandler.obtainMessage(GETOTHERTASK,listTask).sendToTarget();
//                } catch (DbException e) {
//                    e.printStackTrace();
//                }

            }
        }).start();
    }

    private void getMyTaskForDb() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<TaskEntity> listTask=dbUtils
                            .selector(TaskEntity.class)
                            .where("pro_id", "=", pro_id + "")
                            .findAll();
                    if(listTask!=null&&!listTask.isEmpty())
                        mhandler.obtainMessage(GETMYTASK,listTask).sendToTarget();
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void getDataForWeb() {
        getMytaskForWeb();
        getOtherTaskForWeb();
    }

    private void getMytaskForWeb() {
        BaseParams params = new BaseParams(MyData.URL_TaskList, userEntity);
        x.http().post(params, new Callback.CommonCallback<MyResponse>() {
            @Override
            public void onSuccess(MyResponse result) {
                final List<TaskEntity> listTask = JSON.parseObject(result.data, new TypeReference<List<TaskEntity>>() {
                });
                if (listTask != null && !listTask.isEmpty()) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //添加新表
                            for (TaskEntity taskMode : listTask) {
                                try {
                                    //添加Task
                                    dbUtils.saveOrUpdate(taskMode);
                                } catch (DbException e) {
                                    e.printStackTrace();
                                }

                            }
                            mhandler.obtainMessage(SAVEMYTASK).sendToTarget();
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

    private void getOtherTaskForWeb() {
        BaseParams params = new BaseParams(MyData.URL_OtherTaskList, userEntity);
        params.addBodyParameter("pro_id",pro_id+"");
        x.http().post(params, new Callback.CommonCallback<MyResponse>() {
            @Override
            public void onSuccess(MyResponse result) {
//                final List<OtherTaskEntity> listTask = JSON.parseObject(result.data, new TypeReference<List<OtherTaskEntity>>() {
//                });
//                if (listTask != null && !listTask.isEmpty()) {
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            //添加新表
//                            for (OtherTaskEntity otherTaskEntity : listTask) {
//                                try {
//                                    //添加Task
//                                    dbUtils.saveOrUpdate(otherTaskEntity);
//                                    for(TasUseMode tasUseMode :otherTaskEntity.getListTasUse())
//                                        dbUtils.replace(tasUseMode);
//                                } catch (DbException e) {
//                                    e.printStackTrace();
//                                }
//
//                            }
//                            mhandler.obtainMessage(SAVEOTHERTASK).sendToTarget();
//                        }
//                    }).start();
//
//                }
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

    private Handler mhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case GETMYTASK:
                    adapter.removeMyTask();
                    adapter.addMyTask((List<TaskEntity>) msg.obj);
                    break;
                case GETOTHERTASK:
                    adapter.removeOtherTask();
//                    adapter.addOtherTask((List<OtherTaskEntity>) msg.obj);
                    break;
                case SAVEMYTASK:
                    getMyTaskForDb();
                    break;
                case SAVEOTHERTASK:
                    getOtherTaskForDb();
                    break;
            }
        }
    };
}
