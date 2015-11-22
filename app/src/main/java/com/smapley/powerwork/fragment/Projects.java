package com.smapley.powerwork.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.smapley.powerwork.R;
import com.smapley.powerwork.adapter.ProjectsAdapter;
import com.smapley.powerwork.mode.BaseMode;
import com.smapley.powerwork.mode.Pro_AddItem_Mode;
import com.smapley.powerwork.mode.Pro_Item_Mode;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smapley on 15/11/16.
 */
@ContentView(R.layout.fragment_projects)
public class Projects extends BaseFragment {


    @ViewInject(R.id.pros_rv_list)
    private RecyclerView pros_rv_list;

    private ProjectsAdapter adapter;

    private List<BaseMode> pros_lt_data;

    @Override
    protected void initParams(View view) {
        pros_rv_list.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        pros_lt_data = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Pro_Item_Mode pro_item_mode = new Pro_Item_Mode();

            pro_item_mode.setName("asdf");
            pros_lt_data.add(pro_item_mode);
        }
        pros_lt_data.add(new Pro_AddItem_Mode());
        adapter = new ProjectsAdapter(getActivity(), pros_lt_data);
        pros_rv_list.setAdapter(adapter);

    }

}
