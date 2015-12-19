package com.smapley.powerwork.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.smapley.powerwork.fragment.BaseFragment;

import java.util.List;

/**
 * Created by smapley on 15/10/25.
 */
public class MainViewPagerAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> list ;

    public MainViewPagerAdapter(FragmentManager fm, List<BaseFragment> list) {
        super(fm);
        this.list = list;
    }
    public void setList(List<BaseFragment> list){
        this.list=list;
        notifyDataSetChanged();

    }


    public void addItem(BaseFragment fragment){
        this.list.add(fragment);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        if(list==null){
            return 0;
        }else {
            return list.size();
        }
    }
}
