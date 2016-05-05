package com.smapley.powerwork.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.smapley.powerwork.R;
import com.smapley.powerwork.adapter.MainViewPagerAdapter;
import com.smapley.powerwork.fragment.BaseFragment;
import com.smapley.powerwork.fragment.Calendar;
import com.smapley.powerwork.fragment.Message;
import com.smapley.powerwork.fragment.Personal;
import com.smapley.powerwork.fragment.Projects;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewInject(R.id.main_vp_pagers)
    private ViewPager main_vp_pagers;
    @ViewInject(R.id.main_iv_item1)
    private ImageView main_iv_item1;
    @ViewInject(R.id.main_iv_item2)
    private ImageView main_iv_item2;
    @ViewInject(R.id.main_iv_item3)
    private ImageView main_iv_item3;
    @ViewInject(R.id.main_iv_item4)
    private ImageView main_iv_item4;

    private List<BaseFragment> main_lt_pages;
    private MainViewPagerAdapter main_vp_adapter;
    private Personal main_vp_personal;
    private Projects main_vp_projects;
    private Calendar main_vp_calendar;
    private Message main_vp_message;

    @Override
    protected void initParams() {
        if (sp_user.getBoolean("islogin", false)) {

            //异步加载Viewpage
            initViewPager();
        } else {
            //如果没有登陆 则跳转到登陆界面
            startActivity(new Intent(MainActivity.this, Login.class));
            finish();
        }
    }


    private void initViewPager() {
        main_lt_pages = new ArrayList<>();
        main_vp_personal = new Personal();
        main_vp_projects = new Projects();
        main_vp_calendar = new Calendar();
        main_vp_message = new Message();
        main_lt_pages.add(main_vp_calendar);
        main_lt_pages.add(main_vp_projects);
        main_lt_pages.add(main_vp_personal);
        main_lt_pages.add(main_vp_message);

        main_vp_adapter = new MainViewPagerAdapter(getSupportFragmentManager(), main_lt_pages);
        main_vp_pagers.setAdapter(main_vp_adapter);
        main_vp_pagers.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeTabBackground(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Event({R.id.main_iv_item1, R.id.main_iv_item2, R.id.main_iv_item3, R.id.main_iv_item4,R.id.main_add_menu_fab})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_add_menu_fab:
                startActivity(new Intent(MainActivity.this, AddTask.class));
                break;
            case R.id.main_iv_item1:
                main_vp_pagers.setCurrentItem(0);
                changeTabBackground(0);
                break;
            case R.id.main_iv_item2:
                main_vp_pagers.setCurrentItem(1);
                changeTabBackground(1);
                break;
            case R.id.main_iv_item3:
                main_vp_pagers.setCurrentItem(2);
                changeTabBackground(2);
                break;
            case R.id.main_iv_item4:
                main_vp_pagers.setCurrentItem(3);
                changeTabBackground(3);
                break;
        }
    }


    /**
     * 切换选项卡背景
     */
    private void changeTabBackground(int position) {
        main_iv_item1.setImageResource(R.mipmap.main_iv_item1);
        main_iv_item2.setImageResource(R.mipmap.main_iv_item2);
        main_iv_item3.setImageResource(R.mipmap.main_iv_item3);
        main_iv_item4.setImageResource(R.mipmap.main_iv_item4);
        switch (position) {
            case 0:
                main_iv_item1.setImageResource(R.mipmap.main_iv_item1_press);
                break;
            case 1:
                main_iv_item2.setImageResource(R.mipmap.main_iv_item2_press);
                break;
            case 2:
                main_iv_item3.setImageResource(R.mipmap.main_iv_item3_press);
                break;
            case 3:
                main_iv_item4.setImageResource(R.mipmap.main_iv_item4_press);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    main_vp_projects.addProject(data.getStringExtra("name"));
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent mHomeIntent = new Intent(Intent.ACTION_MAIN);
        mHomeIntent.addCategory(Intent.CATEGORY_HOME);
        mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        this.startActivity(mHomeIntent);
    }
}
