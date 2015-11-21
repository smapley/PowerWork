package com.smapley.powerwork.holder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.lidroid.xutils.util.LogUtils;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;
import com.smapley.powerwork.R;
import com.smapley.powerwork.activity.AddTask;
import com.smapley.powerwork.activity.MainActivity;
import com.smapley.powerwork.adapter.PersonalAdapter;
import com.smapley.powerwork.mode.Per_Task_Details_Mode;
import com.smapley.powerwork.utils.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by smapley on 15/10/26.
 */
public class Per_Task_Details_Holder extends BaseHolder {

    private View contentView;
    private TextView per_tv_task_details_name;
    private TextView per_tv_task_details_time;
    private ImageView per_iv_task_details_warm;
    private ImageView per_iv_task_details_mark;
    private ImageView per_iv_task_details_appoint;

    public Per_Task_Details_Holder(View view) {
        super(view);
        contentView = view;
        per_tv_task_details_time = (TextView) view.findViewById(R.id.per_tv_task_details_time);
        per_tv_task_details_name = (TextView) view.findViewById(R.id.per_tv_task_details_name);
        per_iv_task_details_warm = (ImageView) view.findViewById(R.id.per_iv_task_details_warm);
        per_iv_task_details_mark = (ImageView) view.findViewById(R.id.per_iv_task_details_mark);
        per_iv_task_details_appoint = (ImageView) view.findViewById(R.id.per_iv_task_details_appoint);
    }

    public void setData(final Context context, final PersonalAdapter adapter, final Per_Task_Details_Mode mode, final int position) {
        per_tv_task_details_name.setText(mode.getName());
        per_tv_task_details_time.setText(mode.getTime());
        //分配任务
        per_iv_task_details_appoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //添加备注
        per_iv_task_details_mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddTask.class);
                context.startActivity(intent);
            }
        });
        //添加闹钟
        per_iv_task_details_warm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //如果没有闹钟则将闹钟初始时间设置为当前时间
                if (mode.getWarm() == 0) {
                    mode.setWarm(System.currentTimeMillis());
                }
                //配置日期选择器
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog datePickerDialog, final int year, final int month, final int day) {
                        //配置时间选择器
                        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
                                //构造时间字符串并转换成long 保存到mode中
                                mode.setWarm(DateUtil.getDateLong(year + "-" + (month + 1) + "-" + day + " " + hourOfDay + ":" + minute, DateUtil.formatDateAndTime));
                            }
                        }, mode.getWarm());
                        timePickerDialog.setVibrate(false);
                        timePickerDialog.setCloseOnSingleTapMinute(false);
                        timePickerDialog.show(((MainActivity) context).getSupportFragmentManager(), "time");
                    }
                }, mode.getWarm());
                datePickerDialog.setVibrate(false);
                datePickerDialog.setYearRange(1985, 2028);
                datePickerDialog.setCloseOnSingleTapDay(false);
                datePickerDialog.show(((MainActivity) context).getSupportFragmentManager(), "data");
            }
        });
    }


}
