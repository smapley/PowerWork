package com.smapley.powerwork.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.smapley.powerwork.R;
import com.smapley.powerwork.entity.User_Entity;
import com.smapley.powerwork.http.BaseParams;
import com.smapley.powerwork.http.HttpCallBack;
import com.smapley.powerwork.utils.ActivityStack;
import com.smapley.powerwork.utils.DateUtil;
import com.smapley.powerwork.utils.MyData;

import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by smapley on 15/10/24.
 */
@ContentView(R.layout.activity_account)
public class Account extends BaseActivity implements DatePickerDialog.OnDateSetListener {

    @ViewInject(R.id.title_tv_name)
    private TextView title_tv_name;
    @ViewInject(R.id.title_iv_edit)
    private ImageView title_iv_edit;
    @ViewInject(R.id.title_iv_done)
    private ImageView title_iv_done;

    @ViewInject(R.id.acc_iv_pic)
    private ImageView acc_iv_pic;
    @ViewInject(R.id.acc_et_name)
    private EditText acc_et_name;
    @ViewInject(R.id.acc_et_phone)
    private EditText acc_et_phone;
    @ViewInject(R.id.acc_tv_birthday)
    private TextView acc_tv_birthday;
    @ViewInject(R.id.acc_bt_exit)
    private Button acc_bt_exit;

    //记录当前的编辑状态
    private boolean isEdit = false;

    private DatePickerDialog datePickerDialog;

    @Override
    protected void initParams() {
        title_tv_name.setText(R.string.account);
        title_iv_edit.setVisibility(View.VISIBLE);

        initView();

        final Calendar calendar = Calendar.getInstance();
        datePickerDialog = DatePickerDialog.newInstance(Account.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), false);

    }

    private void initView() {
        if (user_entity != null) {
            x.image().bind(acc_iv_pic, MyData.URL_PIC+user_entity.getPic_url());
            acc_et_name.setText(user_entity.getTruename());
            acc_et_phone.setText(user_entity.getPhone());
            acc_tv_birthday.setText(DateUtil.getDateString(user_entity.getCre_date(), DateUtil.formatDate));
        }
    }


    /**
     * 改变EditText可编辑状态
     */
    private void changeEditState() {
        isEdit = !isEdit;
        acc_et_name.setEnabled(isEdit);
        acc_et_phone.setEnabled(isEdit);
        acc_tv_birthday.setEnabled(isEdit);
        acc_bt_exit.setVisibility(isEdit ? View.GONE : View.VISIBLE);

    }

    @Event({R.id.acc_iv_changepic, R.id.acc_bt_exit, R.id.title_iv_back, R.id.title_iv_edit, R.id.acc_tv_birthday, R.id.title_iv_done})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.acc_tv_birthday:
                datePickerDialog.setVibrate(false);
                datePickerDialog.setYearRange(1985, 2028);
                datePickerDialog.setCloseOnSingleTapDay(false);
                datePickerDialog.show(getSupportFragmentManager(), "data");
                break;
            case R.id.title_iv_edit:
                if (!isEdit) {
                    changeEditState();
                    title_iv_edit.setVisibility(View.GONE);
                    title_iv_done.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.title_iv_done:
                saveData();

                break;
            case R.id.title_iv_back:
                if (isEdit) {
                    onExit();
                } else {
                    finish();
                }
                break;
            case R.id.acc_iv_changepic:
                dialog = new SweetAlertDialog(Account.this);
                dialog.showText(R.string.acc_dialog_title)
                        .showCancelButton()
                        .showConfirmButton(R.string.acc_dialog_ok)
                        .setOnSweetClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onConfirmClick(SweetAlertDialog dialog) {
                                selectPic();
                                dialog.dismiss();
                            }

                            @Override
                            public void onFirstClick(SweetAlertDialog dialog) {

                            }

                            @Override
                            public void onCancelClick(SweetAlertDialog dialog) {
                                dialog.dismiss();
                            }
                        }).show();
                break;
            case R.id.acc_bt_exit:
                SharedPreferences.Editor editor = sp_user.edit();
                editor.putBoolean("islogin", false);
                editor.commit();
                ActivityStack.getInstance().finishAllActivity();
                startActivity(new Intent(Account.this, Login.class));
                break;
        }
    }

    /**
     * 从相册选择头像
     */
    private void selectPic() {
        int selectedMode = MultiImageSelectorActivity.MODE_SINGLE;
        boolean showCamera = true;
        int maxNum = 1;
        Intent intent = new Intent(Account.this, MultiImageSelectorActivity.class);
        // 是否显示拍摄图片
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, showCamera);
        // 最大可选择图片数量
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, maxNum);
        // 选择模式
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, selectedMode);
        // 默认选择
//                if (mSelectPath != null && mSelectPath.size() > 0) {
//                    intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mSelectPath);
//                }
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK && requestCode == 0) {
                List<String> resultList = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                BaseParams params = new BaseParams(MyData.URL_UserPicUpLoad,user_entity);
                params.setMultipart(true);
                params.addBodyParameter("file", new File(resultList.get(0)));
                x.http().post(params, new HttpCallBack(Account.this, R.string.acc_dialog_uppic) {
                    @Override
                    public void onResult(String result, SweetAlertDialog dialog) {
                        dialog.dismiss();
                        try {
                            user_entity = JSON.parseObject(result, new TypeReference<User_Entity>() {
                            });
                            dbUtils.update(user_entity, "pic_url");
                            initView();
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        } catch (Exception e) {

        }
    }

    private void saveData() {
        BaseParams params = new BaseParams(MyData.URL_Account, user_entity);
        params.addBodyParameter("truename", acc_et_name.getText().toString());
        params.addBodyParameter("phone", acc_et_phone.getText().toString());
        params.addBodyParameter("birthday", DateUtil.getDateLong(acc_tv_birthday.getText().toString(), DateUtil.formatDate) + "");
        x.http().post(params, new HttpCallBack(Account.this, R.string.acc_dialog_savedata) {
            @Override
            public void onResult(String result, SweetAlertDialog dialog) {
                dialog.dismiss();
                try {
                    user_entity = JSON.parseObject(result, new TypeReference<User_Entity>() {
                    });
                    dbUtils.update(user_entity, "truename", "phone", "birthday");
                    if (isEdit) {
                        changeEditState();
                        title_iv_edit.setVisibility(View.VISIBLE);
                        title_iv_done.setVisibility(View.GONE);
                    }
                    initView();
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void onExit() {
        dialog = new SweetAlertDialog(Account.this, SweetAlertDialog.WARNING_TYPE);
        dialog.showCancelButton(R.string.no_save)
                .showConfirmButton(R.string.check)
                .showText(R.string.acc_dialog_title2)
                .setOnSweetClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onConfirmClick(SweetAlertDialog dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onFirstClick(SweetAlertDialog dialog) {

                    }

                    @Override
                    public void onCancelClick(SweetAlertDialog dialog) {
                        finish();
                        dialog.dismiss();

                    }
                }).show();
    }

    @Override
    public void onBackPressed() {
        if (isEdit) {
            onExit();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        acc_tv_birthday.setText(year + " - " + month + " - " + day);
    }
}
