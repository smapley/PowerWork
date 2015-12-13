package com.smapley.powerwork.activity;

import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;
import com.smapley.powerwork.R;
import com.smapley.powerwork.entity.ProUserEntity;
import com.smapley.powerwork.entity.ProjectEntity;
import com.smapley.powerwork.entity.TasUseEntity;
import com.smapley.powerwork.http.BaseParams;
import com.smapley.powerwork.http.HttpCallBack;
import com.smapley.powerwork.mode.Add_Item_Mode;
import com.smapley.powerwork.utils.ActivityStack;
import com.smapley.powerwork.utils.DateUtil;
import com.smapley.powerwork.utils.MyData;
import com.smapley.powerwork.utils.ThreadSleep;

import org.xutils.common.util.LogUtil;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by smapley on 15/11/28.
 */
@ContentView(R.layout.activity_publishtask)
public class PublishTask extends BaseActivity {

    @ViewInject(R.id.title_tv_name)
    private TextView title_tv_name;
    @ViewInject(R.id.title_iv_done)
    private ImageView title_iv_done;

    @ViewInject(R.id.pub_tv_note)
    private TextView pub_tv_note;
    @ViewInject(R.id.pub_tv_task)
    private TextView pub_tv_task;

    @ViewInject(R.id.pub_ll_task)
    private LinearLayout pub_ll_task;

    @ViewInject(R.id.pub_et_task_name)
    private TextView pub_et_task_name;
    @ViewInject(R.id.pub_tv_task_projectname)
    private TextView pub_tv_task_projectname;
    @ViewInject(R.id.pub_tv_task_performer)
    private TextView pub_tv_task_performer;
    @ViewInject(R.id.pub_tv_task_participation)
    private TextView pub_tv_task_participation;
    @ViewInject(R.id.pub_tv_task_endtime)
    private TextView pub_tv_task_endtime;

    @ViewInject(R.id.pub_et_note_name)
    private TextView pub_et_note_name;
    @ViewInject(R.id.pub_sh_note_alarm)
    private Switch pub_sh_note_alarm;
    @ViewInject(R.id.pub_ll_note_alarm)
    private LinearLayout pub_ll_note_alarm;
    @ViewInject(R.id.pub_tv_note_alarm)
    private TextView pub_tv_note_alarm;

    private int type = 0;

    private List<Add_Item_Mode> list;
    private List<ProjectEntity> listProject;
    private List<ProUserEntity> listProUse;
    //参与者
    private List<ProUserEntity> listPar;
    private List<TasUseEntity> listTasUse;
    private int pro_id;
    //创建者TasUse
    private TasUseEntity creatTasUse;
    //执行者TasUse
    private TasUseEntity perforTasUse;

    @Override
    protected void initParams() {
        initData();
        initView();
    }

    private void initData() {
        String data = getIntent().getStringExtra("data");
        try {
            list = JSON.parseObject(data, new TypeReference<List<Add_Item_Mode>>() {
            });
            list.remove(list.size() - 1);
            LogUtil.d("---" + list.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            listProject = dbUtils.findAll(ProjectEntity.class);
            listProUse = dbUtils.selector(ProUserEntity.class).where("pro_id", "=", listProject.get(0).getPro_id()).findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //初始化创建者和执行者TasUse
        listTasUse=new ArrayList<>();
        listPar=new ArrayList<>();
        creatTasUse=new TasUseEntity();
        creatTasUse.setRank(0);
        creatTasUse.setUse_id(user_entity.getUseId());
        perforTasUse=new TasUseEntity();
        perforTasUse.setRank(1);
    }

    private void initView() {
        title_tv_name.setText(R.string.publish);
        title_iv_done.setVisibility(View.VISIBLE);
        pub_sh_note_alarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                pub_ll_note_alarm.setVisibility(b ? View.VISIBLE : View.GONE);
            }
        });
        //默认提醒时间一天后
        String date = DateUtil.getDateString(System.currentTimeMillis() + 86400000, DateUtil.formatDateAndTime);
        pub_tv_note_alarm.setText(date);
        pub_tv_task_endtime.setText(date);
        //默认名字
        String name = list.get(0).getText().toString();
        if (name != null && !name.isEmpty()) {
            pub_et_note_name.setHint(name);
            pub_et_task_name.setHint(name);
        } else {
            name = DateUtil.getDateString(System.currentTimeMillis(), DateUtil.formatTime);
            pub_et_note_name.setHint("Note" + name);
            pub_et_task_name.setHint("Task" + name);
        }
        //默认project
        if (listProject != null && !listProject.isEmpty()) {
            pub_tv_task_projectname.setText(listProject.get(0).getName());
            pro_id = listProject.get(0).getPro_id();
        }
        //默认performer
        if (listProUse != null && !listProUse.isEmpty()) {
            pub_tv_task_performer.setText(listProUse.get(0).getUser_truename());
            listTasUse.clear();
            perforTasUse.setUse_id(listProUse.get(0).getUse_id());
            listTasUse.add(creatTasUse);
            listTasUse.add(perforTasUse);
            //更新参与者
            listPar.clear();
            listPar.addAll(listProUse);
            listPar.remove(0);
        }
    }

    private void upData() {
        if (type == 0) {
            addTask();
        } else {
            addNote();
        }
    }

    private void addTask() {
        long endTime = DateUtil.getDateLong(pub_tv_task_endtime.getText().toString(), DateUtil.formatDateAndTime);
        String name = pub_et_task_name.getText().toString();
        if(name.isEmpty()){
            name=pub_et_task_name.getHint().toString();
        }
        BaseParams params = new BaseParams(MyData.URL_AddTask, user_entity);
        params.addBodyParameter("name", name);
        params.addBodyParameter("endtime", endTime + "");
        params.addBodyParameter("pro_id", pro_id + "");
        params.addBodyParameter("tasuse", JSON.toJSONString(listTasUse));
        params.addBodyParameter("size", list.size() + "");
        params.setMultipart(true);
        for (int i = 0; i < list.size(); i++) {
            params.addBodyParameter("type" + i, list.get(i).getType() + "");
            params.addBodyParameter("text" + i, list.get(i).getText());
            String path = list.get(i).getPath();
            if (path != null && !path.isEmpty()) {
                params.addBodyParameter("file" + i, new File(path));
            }
            params.addBodyParameter("length" + i, list.get(i).getLength() + "");
        }
        x.http().post(params, new HttpCallBack(this, R.string.publish_ing) {
            @Override
            public void onResult(String result, SweetAlertDialog dialog) {
                dialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE)
                        .showText(R.string.publish_succ)
                        .commit()
                        .dismiss(2000);
                new ThreadSleep().sleep(2000, new ThreadSleep.Callback() {
                    @Override
                    public void onCallback(int number) {
                        ActivityStack.getInstance().finishActivity(PublishTask.class);
                        ActivityStack.getInstance().finishActivity(AddTask.class);
                    }
                });
            }
        });
    }

    private void addNote() {
        //获取闹钟时间如果没有开启闹钟则将闹钟值设为1000
        boolean isAlarm = pub_sh_note_alarm.isChecked();
        long alarm = isAlarm ? DateUtil.getDateLong(pub_tv_note_alarm.getText().toString(), DateUtil.formatDateAndTime) : 1000;

        String name = pub_et_note_name.getText().toString();
        if(name.isEmpty()){
            name=pub_et_note_name.getHint().toString();
        }
        BaseParams params = new BaseParams(MyData.URL_AddNote, user_entity);
        params.addBodyParameter("name", name);
        params.addBodyParameter("alarm", alarm + "");
        params.addBodyParameter("size", list.size() + "");
        params.setMultipart(true);
        for (int i = 0; i < list.size(); i++) {
            params.addBodyParameter("type" + i, list.get(i).getType() + "");
            params.addBodyParameter("text" + i, list.get(i).getText());
            String path = list.get(i).getPath();
            if (path != null && !path.isEmpty()) {
                params.addBodyParameter("file" + i, new File(path));
            }
            params.addBodyParameter("length" + i, list.get(i).getLength() + "");
        }
        x.http().post(params, new HttpCallBack(this, R.string.publish_ing) {
            @Override
            public void onResult(String result, SweetAlertDialog dialog) {
                dialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE)
                        .showText(R.string.publish_succ)
                        .commit()
                        .dismiss(2000);
                new ThreadSleep().sleep(2000, new ThreadSleep.Callback() {
                    @Override
                    public void onCallback(int number) {
                        ActivityStack.getInstance().finishActivity(PublishTask.class);
                        ActivityStack.getInstance().finishActivity(AddTask.class);
                    }
                });
            }
        });
    }

    @Event({R.id.title_iv_back, R.id.title_iv_done, R.id.pub_tv_note, R.id.pub_tv_task, R.id.pub_tv_task_projectname,
            R.id.pub_tv_task_performer, R.id.pub_tv_task_participation, R.id.pub_tv_note_alarm, R.id.pub_tv_task_endtime})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_iv_back:
                finish();
                break;
            case R.id.title_iv_done:
                upData();
                break;
            case R.id.pub_tv_task:
                changeType(0);
                break;
            case R.id.pub_tv_note:
                changeType(1);
                break;
            case R.id.pub_tv_note_alarm:
            case R.id.pub_tv_task_endtime:
                choseDate();
                break;
            case R.id.pub_tv_task_projectname:
                choseProject(view);
                break;
            case R.id.pub_tv_task_performer:
                chosePerformer(view);
                break;
            case R.id.pub_tv_task_participation:
                choseParticipation(view);
                break;
        }
    }

    //参与者
    private void choseParticipation(View view) {
        final List<CheckBox> listCheckBox = new ArrayList<>();
        LayoutInflater inflater = LayoutInflater.from(this);
        View popView = inflater.inflate(R.layout.popup_pub_participation, null, false);
        TextView cancel = (TextView) popView.findViewById(R.id.pub_choseproject_cancel);
        TextView ok = (TextView) popView.findViewById(R.id.pub_choseproject_ok);
        TextView clear = (TextView) popView.findViewById(R.id.pub_participation_clear);
        TextView choseall = (TextView) popView.findViewById(R.id.pub_participation_choseall);
        final PopupWindow popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置不可点击背景
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        //设置popwindow出现和消失动画
        popupWindow.setAnimationStyle(R.style.pop_voice);
        final RadioGroup radioGroup = (RadioGroup) popView.findViewById(R.id.pub_choseproject_rg);
        for (int i = 0; i < listPar.size(); i++) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setId(2000 + i);
            checkBox.setText(listPar.get(i).getUser_truename());
            LogUtil.d("---" + listPar.get(i).getUser_truename());
            checkBox.setTextColor(getResources().getColor(R.color.white));
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
            radioGroup.addView(checkBox, params);
            listCheckBox.add(checkBox);
            //默认选中
            if (!listTasUse.isEmpty()) {
                for (int j = 0; j < listTasUse.size(); j++) {
                    if (listPar.get(i).getUse_id() == listTasUse.get(j).getUse_id()) {
                        checkBox.setChecked(true);
                    }
                }
            }
        }
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        //设置背景变暗
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.alpha = 0.4f;
        getWindow().setAttributes(layoutParams);
        //全部反选
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < listCheckBox.size(); i++) {
                    listCheckBox.get(i).setChecked(false);
                }
            }
        });
        //全部选中
        choseall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < listCheckBox.size(); i++) {
                    listCheckBox.get(i).setChecked(true);
                }
            }
        });
        //取消
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hintPop(popupWindow);
            }
        });
        //确定
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hintPop(popupWindow);
                listTasUse.clear();
                listTasUse.add(creatTasUse);
                listTasUse.add(perforTasUse);
                for (int i = 0; i < listCheckBox.size(); i++) {
                    if (listCheckBox.get(i).isChecked()) {
                        TasUseEntity tasUseEntity=new TasUseEntity();
                        tasUseEntity.setRank(2);
                        tasUseEntity.setUse_id(listPar.get(i).getUse_id());
                        listTasUse.add(tasUseEntity);
                    }
                }
                pub_tv_task_participation.setText(listPar.size() + "人");
            }
        });
    }

    //选择执行者
    private void chosePerformer(View view) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View popView = inflater.inflate(R.layout.popup_pub_choseproject, null, false);
        TextView cancel = (TextView) popView.findViewById(R.id.pub_choseproject_cancel);
        TextView ok = (TextView) popView.findViewById(R.id.pub_choseproject_ok);
        final PopupWindow popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置不可点击背景
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        //设置popwindow出现和消失动画
        popupWindow.setAnimationStyle(R.style.pop_voice);
        final RadioGroup radioGroup = (RadioGroup) popView.findViewById(R.id.pub_choseproject_rg);
        for (int i = 0; i < listProUse.size(); i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setId(1000 + i);
            radioButton.setText(listProUse.get(i).getUser_truename());
            LogUtil.d("---" + listProUse.get(i).getUser_truename());
            radioButton.setTextColor(getResources().getColor(R.color.white));
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
            radioGroup.addView(radioButton, params);
            if (pub_tv_task_performer.getText().toString().equals(radioButton.getText().toString())) {
                radioGroup.check(radioButton.getId());
            }
        }
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        //设置背景变暗
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.alpha = 0.4f;
        getWindow().setAttributes(layoutParams);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hintPop(popupWindow);
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hintPop(popupWindow);
                int num = radioGroup.getCheckedRadioButtonId() - 1000;
                pub_tv_task_performer.setText(listProUse.get(num).getUser_truename());
                //重置参与者
                pub_tv_task_participation.setText(R.string.no_man);
                //添加执行者到list
                listTasUse.clear();
                perforTasUse.setUse_id(listProUse.get(num).getUse_id());
                listTasUse.add(creatTasUse);
                listTasUse.add(perforTasUse);
                //更新参与者
                listPar.clear();
                listPar.addAll(listProUse);
                listPar.remove(num);
            }
        });
    }

    //选择项目
    private void choseProject(View view) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View popView = inflater.inflate(R.layout.popup_pub_choseproject, null, false);
        TextView cancel = (TextView) popView.findViewById(R.id.pub_choseproject_cancel);
        TextView ok = (TextView) popView.findViewById(R.id.pub_choseproject_ok);
        final PopupWindow popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置不可点击背景
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        //设置popwindow出现和消失动画
        popupWindow.setAnimationStyle(R.style.pop_voice);
        final RadioGroup radioGroup = (RadioGroup) popView.findViewById(R.id.pub_choseproject_rg);
        for (int i = 0; i < listProject.size(); i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setId(1000 + i);
            radioButton.setText(listProject.get(i).getName());
            LogUtil.d("---" + listProject.get(i).getName());
            radioButton.setTextColor(getResources().getColor(R.color.white));
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
            radioGroup.addView(radioButton, params);
            if (pub_tv_task_projectname.getText().toString().equals(radioButton.getText().toString())) {
                radioGroup.check(radioButton.getId());
            }
        }
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        //设置背景变暗
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.alpha = 0.4f;
        getWindow().setAttributes(layoutParams);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hintPop(popupWindow);
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hintPop(popupWindow);
                int num = radioGroup.getCheckedRadioButtonId() - 1000;
                pub_tv_task_projectname.setText(listProject.get(num).getName());
                pro_id = listProject.get(num).getPro_id();
                try {
                    listProUse = dbUtils.selector(ProUserEntity.class).where("pro_id", "=", pro_id).findAll();
                } catch (DbException e) {
                    e.printStackTrace();
                }
                //改变项目时 重置执行者和参与者
                pub_tv_task_performer.setText(listProUse.get(0).getUser_truename());
                pub_tv_task_participation.setText(R.string.no_man);
                //添加执行者到list
                listTasUse.clear();
                perforTasUse.setUse_id(listProUse.get(0).getUse_id());
                listTasUse.add(creatTasUse);
                listTasUse.add(perforTasUse);
                //更新参与者
                listPar.clear();
                listPar.addAll(listProUse);
                listPar.remove(0);
            }
        });
    }

    private void hintPop(PopupWindow popupWindow) {
        popupWindow.dismiss();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.alpha = 1f;
        getWindow().setAttributes(layoutParams);
    }

    private void choseDate() {
        //如果没有闹钟则将闹钟初始时间设置为当前时间
        final long alarm = DateUtil.getDateLong(pub_tv_note_alarm.getText().toString(), DateUtil.formatDateAndTime);

        //配置日期选择器
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog datePickerDialog, final int year, final int month, final int day) {
                //配置时间选择器
                TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
                        //构造时间字符串并转换成long 保存到mode中
                        String date = year + "-" + (month + 1) + "-" + day + " " + hourOfDay + ":" + minute;
                        if (type == 0) {
                            pub_tv_task_endtime.setText(date);
                        } else {
                            pub_tv_note_alarm.setText(date);
                        }
                    }
                }, alarm);
                timePickerDialog.setVibrate(false);
                timePickerDialog.setCloseOnSingleTapMinute(false);
                timePickerDialog.show(getSupportFragmentManager(), "time");
            }
        }, alarm);
        datePickerDialog.setVibrate(false);
        datePickerDialog.setYearRange(1985, 2028);
        datePickerDialog.setCloseOnSingleTapDay(false);
        datePickerDialog.show(getSupportFragmentManager(), "data");
    }

    private void changeType(int i) {
        type = i;
        pub_tv_task.setTextColor(i == 0 ? getResources().getColor(R.color.white) : getResources().getColor(R.color.transparent_black));
        pub_tv_note.setTextColor(i == 1 ? getResources().getColor(R.color.white) : getResources().getColor(R.color.transparent_black));
        pub_tv_task.setBackgroundResource(i == 0 ? R.drawable.circle_blue_5 : R.drawable.circle_white_5);
        pub_tv_note.setBackgroundResource(i == 1 ? R.drawable.circle_blue_5 : R.drawable.circle_white_5);
        pub_ll_task.setVisibility(i == 0 ? View.VISIBLE : View.GONE);
        if (i == 0) {
            pub_et_note_name.clearFocus();
            pub_et_task_name.requestFocus();
        } else {
            pub_et_note_name.requestFocus();
            pub_et_task_name.clearFocus();
        }
    }
}
