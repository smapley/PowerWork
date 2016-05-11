package com.smapley.powerwork.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.powerwork.R;
import com.smapley.powerwork.db.entity.ProjectEntity;
import com.smapley.powerwork.fragment.BaseFragment;
import com.smapley.powerwork.fragment.Pro_Item1;
import com.smapley.powerwork.fragment.Pro_Item2;
import com.smapley.powerwork.fragment.Pro_Item3;
import com.smapley.powerwork.fragment.Pro_Item4;
import com.smapley.powerwork.fragment.Pro_Item5;
import com.smapley.powerwork.http.callback.HttpCallBack;
import com.smapley.powerwork.http.params.BaseParams;
import com.smapley.powerwork.utils.MyData;

import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by smapley on 15/11/16.
 */
@ContentView(R.layout.activity_project)
public class Project extends BaseActivity {
    @ViewInject(R.id.pro_ct_layout)
    private CollapsingToolbarLayout pro_ct_layout;

    @ViewInject(R.id.pro_fl_content)
    private FrameLayout pro_fl_content;

    @ViewInject(R.id.pro_item1_iv)
    private ImageView pro_item1_iv;

    @ViewInject(R.id.pro_tv_btn_item1)
    private TextView pro_tv_btn_item1;
    @ViewInject(R.id.pro_tv_btn_item2)
    private TextView pro_tv_btn_item2;
    @ViewInject(R.id.pro_tv_btn_item3)
    private TextView pro_tv_btn_item3;
    @ViewInject(R.id.pro_tv_btn_item4)
    private TextView pro_tv_btn_item4;
    @ViewInject(R.id.pro_tv_btn_item5)
    private TextView pro_tv_btn_item5;
    @ViewInject(R.id.pro_iv_btn_image1)
    private ImageView pro_iv_btn_image1;
    @ViewInject(R.id.pro_iv_btn_image2)
    private ImageView pro_iv_btn_image2;
    @ViewInject(R.id.pro_iv_btn_image3)
    private ImageView pro_iv_btn_image3;
    @ViewInject(R.id.pro_iv_btn_image4)
    private ImageView pro_iv_btn_image4;
    @ViewInject(R.id.pro_iv_btn_image5)
    private ImageView pro_iv_btn_image5;

    private Pro_Item1 pro_item1;
    private Pro_Item2 pro_item2;
    private Pro_Item3 pro_item3;
    private Pro_Item4 pro_item4;
    private Pro_Item5 pro_item5;

    private List<BaseFragment> pro_lt_fragment;

    private FragmentManager fragmentManager;

    private Bundle bundle;
    private ProjectEntity project;
    private int pro_id;

    @Override
    protected void initParams() {
        //使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上则不会显示
        pro_ct_layout.setExpandedTitleTextAppearance(R.style.pro_name_expanded);
        pro_ct_layout.setCollapsedTitleTextAppearance(R.style.per_name_collapsed);
        //通过CollapsingToolbarLayout修改字体颜色
        pro_ct_layout.setExpandedTitleColor(getResources().getColor(R.color.cal_text));//设置还没收缩时状态下字体颜色
        pro_ct_layout.setCollapsedTitleTextColor(getResources().getColor(R.color.cal_text));//设置收缩后Toolbar上字体的颜色
        initData();
        initView();
        initFragment();
    }

    private void initView() {
        if (project != null) {
            pro_ct_layout.setTitle(project.getName());
            x.image().bind(pro_item1_iv, MyData.URL_PIC+project.getPic_url());
        }
        pro_item1_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new SweetAlertDialog(Project.this);
                dialog.showText(R.string.acc_dialog_title3)
                        .showCancelButton()
                        .showConfirmButton(R.string.acc_dialog_ok)
                        .setOnSweetClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onConfirmClick(SweetAlertDialog dialog) {
                                selectPic(Project.this);
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

            }
        });
    }

    /**
     * 从相册选择头像
     */
    private void selectPic(Context context) {
        int selectedMode = MultiImageSelectorActivity.MODE_SINGLE;
        boolean showCamera = true;
        int maxNum = 1;
        Intent intent = new Intent(context, MultiImageSelectorActivity.class);
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


    private void initFragment() {
        pro_lt_fragment = new ArrayList<>();
        pro_item1 = new Pro_Item1();
        pro_item2 = new Pro_Item2();
        pro_item3 = new Pro_Item3();
        pro_item4 = new Pro_Item4();
        pro_item5 = new Pro_Item5();
        pro_lt_fragment.add(pro_item1);
        pro_lt_fragment.add(pro_item2);
        pro_lt_fragment.add(pro_item3);
        pro_lt_fragment.add(pro_item4);
        pro_lt_fragment.add(pro_item5);
        for (BaseFragment fragment : pro_lt_fragment) {
            fragment.setArguments(bundle);
        }
        transactionTo(0);
    }

    private void initData() {
        bundle = getIntent().getExtras();
        pro_id = bundle.getInt("pro_id");
        try {
            project = dbUtils.findById(ProjectEntity.class, pro_id);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Event({R.id.pro_ll_btn_layout1, R.id.pro_ll_btn_layout2, R.id.pro_ll_btn_layout3, R.id.pro_ll_btn_layout4, R.id.pro_ll_btn_layout5})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.pro_ll_btn_layout1:
                changeView(0);
                transactionTo(0);
                break;
            case R.id.pro_ll_btn_layout2:
                changeView(1);
                transactionTo(1);
                break;
            case R.id.pro_ll_btn_layout3:
                changeView(2);
                transactionTo(2);
                break;
            case R.id.pro_ll_btn_layout4:
                changeView(3);
                transactionTo(3);
                break;
            case R.id.pro_ll_btn_layout5:
                changeView(4);
                transactionTo(4);
                break;
        }
    }

    private void changeView(int i) {
        pro_iv_btn_image1.setImageResource(R.mipmap.home);
        pro_tv_btn_item1.setTextColor(getResources().getColor(R.color.cal_text));
        pro_iv_btn_image2.setImageResource(R.mipmap.task);
        pro_tv_btn_item2.setTextColor(getResources().getColor(R.color.cal_text));
        pro_iv_btn_image3.setImageResource(R.mipmap.file);
        pro_tv_btn_item3.setTextColor(getResources().getColor(R.color.cal_text));
        pro_iv_btn_image4.setImageResource(R.mipmap.statistics);
        pro_tv_btn_item4.setTextColor(getResources().getColor(R.color.cal_text));
        pro_iv_btn_image5.setImageResource(R.mipmap.team);
        pro_tv_btn_item5.setTextColor(getResources().getColor(R.color.cal_text));

        switch (i) {
            case 0:
                pro_iv_btn_image1.setImageResource(R.mipmap.home_press);
                pro_tv_btn_item1.setTextColor(getResources().getColor(R.color.default_text));
                break;
            case 1:
                pro_iv_btn_image2.setImageResource(R.mipmap.task_press);
                pro_tv_btn_item2.setTextColor(getResources().getColor(R.color.default_text));
                break;
            case 2:
                pro_iv_btn_image3.setImageResource(R.mipmap.file_press);
                pro_tv_btn_item3.setTextColor(getResources().getColor(R.color.default_text));
                break;
            case 3:
                pro_iv_btn_image4.setImageResource(R.mipmap.statistics_press);
                pro_tv_btn_item4.setTextColor(getResources().getColor(R.color.default_text));
                break;
            case 4:
                pro_iv_btn_image5.setImageResource(R.mipmap.team_press);
                pro_tv_btn_item5.setTextColor(getResources().getColor(R.color.default_text));
                break;
        }

    }

    private void transactionTo(int position) {
        if (fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();
        }

        fragmentManager.beginTransaction().replace(R.id.pro_fl_content, pro_lt_fragment.get(position)).commit();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                List<String> resultList1 = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                BaseParams params = new BaseParams(MyData.URL_ProjectPicUpLoad, userEntity);
                params.addBodyParameter("projectId",pro_id+"");
                params.addBodyParameter("file", new File(resultList1.get(0)));
                params.setMultipart(true);
                x.http().post(params, new HttpCallBack(Project.this, R.string.acc_dialog_uppic) {
                    @Override
                    public void onResult(String result, SweetAlertDialog dialog) {
                        dialog.dismiss();
                        try {
                            ProjectEntity projectEntity = JSON.parseObject(result, new TypeReference<ProjectEntity>() {
                            });
                            dbUtils.saveOrUpdate(projectEntity);
                            initData();
                            initView();
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    pro_item3.addFolder(data.getStringExtra("name"));
                }
                break;
            case 2:
                //选择图片
                if (resultCode == RESULT_OK) {
                    List<String> resultList = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    pro_item3.addFile(1,resultList);
                }
                break;
        }
    }

}
