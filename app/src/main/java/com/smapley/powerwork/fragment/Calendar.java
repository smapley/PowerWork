package com.smapley.powerwork.fragment;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.powerwork.R;
import com.smapley.powerwork.adapter.PersonalAdapter;
import com.smapley.powerwork.entity.TasUseEntity;
import com.smapley.powerwork.entity.TaskEntity;
import com.smapley.powerwork.http.BaseParams;
import com.smapley.powerwork.http.MyResponse;
import com.smapley.powerwork.mode.BaseMode;
import com.smapley.powerwork.utils.DateUtil;
import com.smapley.powerwork.utils.MyData;
import com.smapley.powerwork.view.MyCalendar;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smapley on 15/10/25.
 */
@ContentView(R.layout.fragment_calendar)
public class Calendar extends BaseFragment {

    @ViewInject(R.id.cal_ct_layout)
    private CollapsingToolbarLayout cal_ct_layout;

    @ViewInject(R.id.cal_tv_month)
    private TextView cal_tv_month;

    @ViewInject(R.id.cal_mc_calendar)
    private MyCalendar cal_mc_calendar;
    @ViewInject(R.id.cal_rv_recycler)
    private RecyclerView cal_rv_recycler;
    private PersonalAdapter per_adapter;
    private List<BaseMode> cal_list;

    private String[] cal_month;
    //当前选择的日期
    private String dateChecked;


    @Override
    protected void initParams(View view) {
        //使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上则不会显示
        cal_ct_layout.setExpandedTitleGravity(Gravity.BOTTOM);
        cal_ct_layout.setExpandedTitleTextAppearance(R.style.per_name_expanded);
        //通过CollapsingToolbarLayout修改字体颜色
        cal_ct_layout.setExpandedTitleColor(getResources().getColor(R.color.cal_text));//设置还没收缩时状态下字体颜色
        cal_ct_layout.setCollapsedTitleTextColor(getResources().getColor(R.color.cal_text));//设置收缩后Toolbar上字体的颜色

        //初始化数据
        initData();
        //初始化日历组件
        initCalendar();
        //初始化列表组件
        initRecyclerView();
        //初始化组件
        initView();
        //显示Task
        showCal();
        //网络获取数据
        getData();
    }

    @Event({R.id.cal_iv_refresh})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.cal_iv_refresh:
                getData();
                LogUtil.d("asdfasdfasdfasdf");
                break;
        }
    }

    private void initData() {
        cal_month = getResources().getStringArray(R.array.cal_month);
        dateChecked = DateUtil.getDateString(System.currentTimeMillis(), DateUtil.formatDate);
    }

    /**
     * 初始化列表
     */
    private void initRecyclerView() {
        cal_rv_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        cal_list = new ArrayList<>();
        per_adapter = new PersonalAdapter(getActivity(), cal_list);
        cal_rv_recycler.setAdapter(per_adapter);

    }

    public void getData() {
        BaseParams params = new BaseParams(MyData.URL_TaskList, user_entity);
        x.http().post(params, new Callback.CommonCallback<MyResponse>() {
            @Override
            public void onSuccess(MyResponse result) {
                List<TaskEntity> listTask = JSON.parseObject(result.data, new TypeReference<List<TaskEntity>>() {
                });
                if (listTask != null && !listTask.isEmpty()) {
                    //删除旧表
                    try {
                        dbUtils.delete(TaskEntity.class);
                        dbUtils.delete(TasUseEntity.class);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                    //添加新表
                    for (TaskEntity taskEntity : listTask) {
                        try {
                            //添加Task
                            dbUtils.save(taskEntity);
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                        try {
                            //添加TasUse
                            dbUtils.save(taskEntity.getTasUseEntity());
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    }
                    //更新数据
                    showCal();
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


    private void initCalendar() {

        //监听所选中的日期
        cal_mc_calendar.setOnCalendarClickListener(new MyCalendar.OnCalendarClickListener() {
            @Override
            public void onCalendarClick(View view, int row, int col, String dateFormat) {


                String[] dateStr = dateFormat.split("-");
                int month = Integer.parseInt(dateStr[1]);
                int day = Integer.parseInt(dateStr[2]);

                //上月下月翻转跳转
                if (cal_mc_calendar.getCalendarMonth() - month == 0// 跨年跳转
                        || cal_mc_calendar.getCalendarMonth() - month == -12) {
                    cal_mc_calendar.lastMonth();

                } else if (month - cal_mc_calendar.getCalendarMonth() == 2 // 跨年跳转
                        || month - cal_mc_calendar.getCalendarMonth() == -10) {
                    cal_mc_calendar.nextMonth();

                } else {
                    //设置当前点击日期的背景
                    cal_mc_calendar.setCalendarDayBgColor(dateFormat, R.mipmap.cal_iv_nowday);
                    //设置日期显示
                    cal_tv_month.setText(cal_month[month - 1]);
                    cal_ct_layout.setTitle(month + " - " + day);
                    dateChecked = dateFormat;
                    showCal();
                }
            }
        });

        //监听当前月份
        cal_mc_calendar.setOnCalendarDateChangedListener(new MyCalendar.OnCalendarDateChangedListener() {
            @Override
            public void onCalendarDateChanged(int year, int month) {
                cal_tv_month.setText(cal_month[month]);
            }
        });
    }

    private void initView() {
        //初始化日期显示
        cal_tv_month.setText(cal_month[cal_mc_calendar.getCalendarMonth()]);
        cal_ct_layout.setTitle(cal_mc_calendar.getCalendarMonth() + 1 + " - " + cal_mc_calendar.getCalendarDay());
    }

    //显示日历&&更新日历
    private void showCal() {
        //从数据库获取数据更新界面
        try {
            List<TaskEntity> listTask = dbUtils.findAll(TaskEntity.class);
            if (listTask != null && !listTask.isEmpty()) {
                //设置标记的日期
                cal_mc_calendar.removeAllMarks();
                for (TaskEntity taskEntity : listTask) {
                    cal_mc_calendar.addMark(DateUtil.getDateString(taskEntity.getEnd_date(), DateUtil.formatDate), R.drawable.cal_cc_mark);
                }
                //显示当天任务
                cal_list.clear();
                for (TaskEntity taskEntity : listTask) {
                    if (dateChecked.equals(DateUtil.getDateString(taskEntity.getEnd_date(), DateUtil.formatDate)))
                        cal_list.add(taskEntity);
                }
                per_adapter.addAll(cal_list);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
}
