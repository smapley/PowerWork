package com.smapley.powerwork.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.smapley.powerwork.R;
import com.smapley.powerwork.adapter.MainViewPagerAdapter;
import com.smapley.powerwork.fragment.Calendar;
import com.smapley.powerwork.fragment.Message;
import com.smapley.powerwork.fragment.Personal;
import com.smapley.powerwork.fragment.Projects;
import com.smapley.powerwork.utils.ThreadSleep;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {


    @ViewInject(R.id.main_add_menu_fab)
    private FloatingActionButton main_add_menu_fab;
    @ViewInject(R.id.main_add_menu)
    private View main_add_menu;
    @ViewInject(R.id.main_add_menu_tv_item1)
    private TextView main_add_menu_tv_item1;
    @ViewInject(R.id.main_add_menu_tv_item2)
    private TextView main_add_menu_tv_item2;
    @ViewInject(R.id.main_add_menu_tv_item3)
    private TextView main_add_menu_tv_item3;
    @ViewInject(R.id.main_add_menu_tv_item4)
    private TextView main_add_menu_tv_item4;
    @ViewInject(R.id.main_add_menu_tv_item5)
    private TextView main_add_menu_tv_item5;
    @ViewInject(R.id.main_add_menu_iv_item1)
    private ImageView main_add_menu_iv_item1;
    @ViewInject(R.id.main_add_menu_iv_item2)
    private ImageView main_add_menu_iv_item2;
    @ViewInject(R.id.main_add_menu_iv_item3)
    private ImageView main_add_menu_iv_item3;
    @ViewInject(R.id.main_add_menu_iv_item4)
    private ImageView main_add_menu_iv_item4;
    @ViewInject(R.id.main_add_menu_iv_item5)
    private ImageView main_add_menu_iv_item5;

    private List<TextView> main_add_menu_tv_items;
    private List<ImageView> main_add_menu_iv_items;

    private int[] main_add_menu_iv_background = new int[]{
            R.mipmap.new_annex_iv, R.mipmap.new_write_iv,
            R.mipmap.new_pic_iv, R.mipmap.new_voice_iv,
            R.mipmap.new_write_iv};
    private int[] main_add_menu_iv_press_background = new int[]{
            R.mipmap.new_annex_iv_press, R.mipmap.new_write_iv_press,
            R.mipmap.new_pic_iv_press, R.mipmap.new_voice_iv_press,
            R.mipmap.new_write_iv_press};
    //记录最后add_menu的选择
    private int main_add_menu_num = -1;


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

    private List<Fragment> main_lt_pages;
    private MainViewPagerAdapter main_vp_adapter;
    private Personal main_vp_personal;
    private Projects main_vp_projects;
    private Calendar main_vp_calendar;
    private Message main_vp_message;

    @Override
    protected void initParams() {
        if (sp_user.getBoolean("islogin", false)) {
            //如果登陆 则加载界面
            initView();
            //异步加载Viewpage
            new ThreadSleep().sleep(50, new ThreadSleep.Callback() {
                @Override
                public void onCallback(int number) {
                    initViewPager();
                }
            });

        } else {
            //如果没有登陆 则跳转到登陆界面
            startActivity(new Intent(MainActivity.this, Login.class));
            finish();
        }
    }

    private void initView() {
        main_add_menu_tv_items = new ArrayList<>();
        main_add_menu_iv_items = new ArrayList<>();
        main_add_menu_tv_items.add(main_add_menu_tv_item1);
        main_add_menu_tv_items.add(main_add_menu_tv_item2);
        main_add_menu_tv_items.add(main_add_menu_tv_item3);
        main_add_menu_tv_items.add(main_add_menu_tv_item4);
        main_add_menu_tv_items.add(main_add_menu_tv_item5);
        main_add_menu_iv_items.add(main_add_menu_iv_item1);
        main_add_menu_iv_items.add(main_add_menu_iv_item2);
        main_add_menu_iv_items.add(main_add_menu_iv_item3);
        main_add_menu_iv_items.add(main_add_menu_iv_item4);
        main_add_menu_iv_items.add(main_add_menu_iv_item5);
        initAddMenu();
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

    @Event({R.id.main_iv_item1, R.id.main_iv_item2, R.id.main_iv_item3, R.id.main_iv_item4})
    private void onClick(View view) {
        switch (view.getId()) {
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

    /**
     * 初始化main_add_menu
     */
    private void initAddMenu() {
        //初始化main_Add_Menu
        final float star_X = 0;
        final float end_X = 150;
        final float Y0 = 103;
        final float Y1 = -530;
        final float Y2 = Y1 + Y0;
        final float Y3 = Y2 + Y0;
        final float Y4 = Y3 + Y0;
        final float Y5 = Y4 + Y0;
        final float Y6 = Y5 + Y0;

        main_add_menu_fab.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        main_add_menu.setVisibility(View.VISIBLE);
                        changeAddMenuBackground(-1);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float x = motionEvent.getX();
                        float y = motionEvent.getY();
                        if (x < end_X && x > star_X && y < Y6 && y > Y1) {
                            if (y > Y5) {
                                changeAddMenuBackground(5);
                            } else if (y > Y4) {
                                changeAddMenuBackground(4);
                            } else if (y > Y3) {
                                changeAddMenuBackground(3);
                            } else if (y > Y2) {
                                changeAddMenuBackground(2);
                            } else if (y > Y1) {
                                changeAddMenuBackground(1);
                            }
                        } else {
                            changeAddMenuBackground(-1);
                        }

                        break;
                    case MotionEvent.ACTION_UP:
                        main_add_menu.setVisibility(View.INVISIBLE);
                        onAddMenuSelect();
                        break;
                }

                return true;
            }
        });
    }

    /**
     * main_add_menu点击事件
     */
    private void onAddMenuSelect() {
        if (main_add_menu_num != -1) {
            Intent intent = new Intent(MainActivity.this, AddTask.class);
            intent.putExtra("type", main_add_menu_num);
            startActivity(intent);
        }

    }

    /**
     * 变换main_add_menu背景
     */
    private void changeAddMenuBackground(int position) {
        main_add_menu_num = position;
        for (int i = 0; i < main_add_menu_tv_items.size(); i++) {
            if (i + 1 != position) {
                main_add_menu_tv_items.get(i).setVisibility(View.INVISIBLE);
                main_add_menu_iv_items.get(i).setImageResource(main_add_menu_iv_background[i]);
            } else {
                main_add_menu_tv_items.get(i).setVisibility(View.VISIBLE);
                main_add_menu_iv_items.get(i).setImageResource(main_add_menu_iv_press_background[i]);

            }
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
