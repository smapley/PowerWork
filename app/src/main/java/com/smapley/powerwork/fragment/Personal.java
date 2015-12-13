package com.smapley.powerwork.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.powerwork.R;
import com.smapley.powerwork.adapter.PersonalAdapter;
import com.smapley.powerwork.application.LocalApplication;
import com.smapley.powerwork.entity.NoteEntity;
import com.smapley.powerwork.entity.TaskEntity;
import com.smapley.powerwork.http.BaseParams;
import com.smapley.powerwork.http.MyResponse;
import com.smapley.powerwork.mode.BaseMode;
import com.smapley.powerwork.mode.Per_Group_Mode;
import com.smapley.powerwork.utils.DateUtil;
import com.smapley.powerwork.utils.MyData;

import org.xutils.common.Callback;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smapley on 15/10/25.
 */
@ContentView(R.layout.fragment_personal)
public class Personal extends BaseFragment {

    private static final int TASKDATA = 1;
    private static final int NOTEDATA = 2;
    private static final int SAVETASK = 3;
    private static final int SAVENOTE = 4;
    @ViewInject(R.id.per_ct_layout)
    private CollapsingToolbarLayout per_ct_layout;
    @ViewInject(R.id.per_iv_pic)
    private ImageView per_iv_pic;
    @ViewInject(R.id.per_tb_bar)
    private Toolbar per_tb_bar;
    @ViewInject(R.id.per_rv_listview)
    private RecyclerView per_rv_listview;

    private List<BaseMode> per_list;
    private PersonalAdapter per_adapter;


    @Override
    protected void initParams(View view) {
        initView();
        initRecyclerView();
        getDataForDb();
        getDataForWeb();


//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.logo);
//        per_iv_pic.setImageBitmap(DullPolish.doPolish(getActivity(), bitmap, 20));
    }

    private void initView() {
        //使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上则不会显示
        per_ct_layout.setTitle(sp_user.getString("nickname", getString(R.string.app_name)));
        per_ct_layout.setExpandedTitleTextAppearance(R.style.per_name_expanded);
        per_ct_layout.setCollapsedTitleTextAppearance(R.style.per_name_collapsed);
        //通过CollapsingToolbarLayout修改字体颜色
        per_ct_layout.setExpandedTitleColor(getResources().getColor(R.color.cal_text));//设置还没收缩时状态下字体颜色
        per_ct_layout.setCollapsedTitleTextColor(getResources().getColor(R.color.cal_text));//设置收缩后Toolbar上字体的颜色
        if (user_entity != null) {
            x.image().bind(per_iv_pic, MyData.URL_PIC + user_entity.getPicUrl(), LocalApplication.getInstance().FilletImage);
        }
    }

    private void initRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        per_rv_listview.setLayoutManager(layoutManager);
        per_list = new ArrayList<>();
        Per_Group_Mode per_group_mode1 = new Per_Group_Mode();
        per_group_mode1.setName("我的任务");
        per_group_mode1.setItem(1);
        per_list.add(per_group_mode1);
        Per_Group_Mode per_group_mode2 = new Per_Group_Mode();
        per_group_mode2.setName("我的记录");
        per_group_mode2.setItem(2);
        per_list.add(per_group_mode2);
        Per_Group_Mode per_group_mode3 = new Per_Group_Mode();
        per_group_mode3.setName("我的成就");
        per_group_mode3.setItem(3);
        per_list.add(per_group_mode3);
        Per_Group_Mode per_group_mode4 = new Per_Group_Mode();
        per_group_mode4.setName("设置");
        per_group_mode4.setItem(4);
        per_list.add(per_group_mode4);
        per_adapter = new PersonalAdapter(getActivity(), per_list);
        per_rv_listview.setAdapter(per_adapter);

    }


    public void getDataForWeb() {
        getTask();
        getNote();


    }

    private void getNote() {
        BaseParams baseParams = new BaseParams(MyData.URL_NoteList, user_entity);
        x.http().post(baseParams, new Callback.CommonCallback<MyResponse>() {
            @Override
            public void onSuccess(final MyResponse result) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<NoteEntity> listNote = JSON.parseObject(result.data, new TypeReference<List<NoteEntity>>() {
                        });
                        if (listNote != null && !listNote.isEmpty()) {
                            for (NoteEntity noteEntity : listNote) {
                                try {
                                    dbUtils.saveOrUpdate(noteEntity);
                                } catch (DbException e) {
                                    e.printStackTrace();
                                }
                            }
                            mhandler.obtainMessage(SAVENOTE).sendToTarget();
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

    private void getTask() {
        BaseParams baseParams = new BaseParams(MyData.URL_TaskList, user_entity);
        x.http().post(baseParams, new Callback.CommonCallback<MyResponse>() {
            @Override
            public void onSuccess(final MyResponse result) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<TaskEntity> listTask = JSON.parseObject(result.data, new TypeReference<List<TaskEntity>>() {
                        });
                        if (listTask != null && !listTask.isEmpty()) {
                            for (TaskEntity taskEntity : listTask) {
                                try {
                                    dbUtils.saveOrUpdate(taskEntity);
                                } catch (DbException e) {
                                    e.printStackTrace();
                                }
                            }
                            mhandler.obtainMessage(SAVETASK).sendToTarget();
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

    public void getDataForDb() {
        getTaskForDb();
        getNoteForDb();


    }

    private void getNoteForDb() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    long time1 = DateUtil.getDateLong(DateUtil.getDateString(System.currentTimeMillis() - DateUtil.onDay, DateUtil.formatDate), DateUtil.formatDate);
                    long time2 = System.currentTimeMillis();
                    //从昨天到现在创建的Note
                    List<NoteEntity> listNote = dbUtils.selector(NoteEntity.class).where("cre_date", "between", new String[]{time1 + "", time2 + ""}).findAll();
                    mhandler.obtainMessage(NOTEDATA, listNote).sendToTarget();
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void getTaskForDb() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    long time1 = 0;
                    long time2 = DateUtil.getDateLong(DateUtil.getDateString(System.currentTimeMillis() + DateUtil.onDay, DateUtil.formatDate), DateUtil.formatDate);
                    //今天以前没有完成的任务
                    List<TaskEntity> listTask = dbUtils.selector(TaskEntity.class)
                            .where("progress","<","100").findAll();
                    mhandler.obtainMessage(TASKDATA, listTask).sendToTarget();

                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TASKDATA:
                    List<TaskEntity> listTask = (List<TaskEntity>) msg.obj;
                    if (listTask != null && !listTask.isEmpty()) {
                        per_adapter.removeTask();
                        per_adapter.addTask(listTask);
                    }
                    break;
                case NOTEDATA:
                    List<NoteEntity> listNote = (List<NoteEntity>) msg.obj;
                    if (listNote != null && !listNote.isEmpty()) {
                        per_adapter.removeNote();
                        per_adapter.addNote(listNote);
                    }
                    break;
                case SAVETASK:
                    getTaskForDb();
                    break;
                case SAVENOTE:
                    getNoteForDb();
                    break;
            }
        }
    };
}
