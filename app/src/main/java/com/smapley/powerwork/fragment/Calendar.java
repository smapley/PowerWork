package com.smapley.powerwork.fragment;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.smapley.powerwork.R;
import com.smapley.powerwork.adapter.PersonalAdapter;
import com.smapley.powerwork.mode.BaseMode;
import com.smapley.powerwork.mode.Cal_Task_Mode;
import com.smapley.powerwork.view.MyCalendar;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

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



    @Override
    protected void initParams(View view) {
        //使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上则不会显示
        cal_ct_layout.setExpandedTitleGravity(Gravity.BOTTOM);
        cal_ct_layout.setExpandedTitleTextAppearance(R.style.per_name_expanded);
        //通过CollapsingToolbarLayout修改字体颜色
        cal_ct_layout.setExpandedTitleColor(getResources().getColor(R.color.cal_text));//设置还没收缩时状态下字体颜色
        cal_ct_layout.setCollapsedTitleTextColor(getResources().getColor(R.color.cal_text));//设置收缩后Toolbar上字体的颜色



        cal_month = getResources().getStringArray(R.array.cal_month);
        initCalendar();
        initRecyclerView();
    }

    @Event({R.id.cal_iv_refresh})
    private void onClick(View view) {

    }

    private void initData() {

        //初始化日期显示
        cal_tv_month.setText(cal_month[cal_mc_calendar.getCalendarMonth()]);
        cal_ct_layout.setTitle(cal_mc_calendar.getCalendarMonth() + 1 + " - " + cal_mc_calendar.getCalendarDay());

        cal_list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Cal_Task_Mode cal_task_mode = new Cal_Task_Mode();
            cal_task_mode.setName("画界面");
            cal_task_mode.setTime("10-26  23:00");
            cal_list.add(cal_task_mode);
        }
        Cal_Task_Mode cal_task_mode = new Cal_Task_Mode();
        cal_task_mode.setName("画界面");
        cal_task_mode.setTime("10-26  23:00");
        cal_task_mode.setCheck(true);
        cal_list.add(cal_task_mode);
        per_adapter = new PersonalAdapter(getActivity(), cal_list);
        cal_rv_recycler.setAdapter(per_adapter);
    }

    /**
     * 初始化列表
     */
    private void initRecyclerView() {

        cal_rv_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        initData();
    }

    private void initCalendar() {
        //设置标记的日期
        cal_mc_calendar.addMark("2015-10-30",R.drawable.cal_cc_mark);

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


}
