package com.smapley.powerwork.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.smapley.powerwork.R;
import com.smapley.powerwork.application.LocalApplication;
import com.smapley.powerwork.utils.ActivityStack;
import com.smapley.powerwork.utils.DullPolish;

import java.net.URI;
import java.util.Calendar;
import java.util.List;

import cn.pedant.SweetAlert.OptAnimationLoader;
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

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.user_pic);
        acc_iv_pic.setImageBitmap(DullPolish.doPolish(this, bitmap, 20));
        acc_et_name.setText(sp_user.getString("name", ""));
        acc_et_phone.setText(sp_user.getString("phone", ""));
        acc_tv_birthday.setText(sp_user.getString("birthday", ""));

        final Calendar calendar = Calendar.getInstance();
        datePickerDialog = DatePickerDialog.newInstance(Account.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), false);

    }


    /**
     * 改变EditText可编辑状态
     */
    private void changeEditState() {
        isEdit = !isEdit;
        acc_et_name.setEnabled(isEdit);
        acc_et_phone.setEnabled(isEdit);
        acc_tv_birthday.setEnabled(isEdit);
        acc_bt_exit.setVisibility(isEdit?View.GONE:View.VISIBLE);

    }

    @OnClick({R.id.acc_iv_changepic, R.id.acc_bt_exit, R.id.title_iv_back, R.id.title_iv_edit, R.id.acc_tv_birthday, R.id.title_iv_done})
    public void viewOnClick(View view) {
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
                if (isEdit) {
                    changeEditState();
                    title_iv_edit.setVisibility(View.VISIBLE);
                    title_iv_done.setVisibility(View.GONE);
                    saveData();
                }
                break;
            case R.id.title_iv_back:
                if (isEdit) {
                    onExit();
                } else {
                    finish();
                }
                break;
            case R.id.acc_iv_changepic:
                new SweetAlertDialog(Account.this, SweetAlertDialog.NORMAL_TYPE,
                        R.string.acc_dialog_title, 0,
                        new int[]{1, 1},
                        new int[]{R.string.cancel, R.string.acc_dialog_ok},
                        new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog, int item) {
                                if (item == 1)
                                    sweetAlertDialog.dismiss();
                                if (item == 2) {
                                    selectPic();
                                    sweetAlertDialog.dismiss();
                                }
                            }
                        }).show();
                break;
            case R.id.acc_bt_exit:
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
                Bitmap bitmap = BitmapFactory.decodeFile(resultList.get(0));
                acc_iv_pic.setImageBitmap(bitmap);
            }
        } catch (Exception e) {

        }
    }

    private void saveData() {
        SharedPreferences.Editor editor = sp_user.edit();
        editor.putString("name", acc_et_name.getText().toString());
        editor.putString("phone", acc_et_phone.getText().toString());
        editor.putString("birthday", acc_tv_birthday.getText().toString());
        editor.commit();
    }

    private void onExit() {
        new SweetAlertDialog(Account.this, SweetAlertDialog.WARNING_TYPE,
                R.string.acc_dialog_title2,
                new int[]{1, 1},
                new int[]{R.string.no_save, R.string.check},
                new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog, int item) {
                        if (item == 1) {
                            finish();
                        }
                        sweetAlertDialog.dismiss();
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
