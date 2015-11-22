package com.smapley.powerwork.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.smapley.powerwork.R;
import com.smapley.powerwork.adapter.AddTaskAdapter;
import com.smapley.powerwork.mode.Add_Menu_Mode;
import com.smapley.powerwork.mode.Add_Pic_Mode;
import com.smapley.powerwork.mode.Add_Text_Mode;
import com.smapley.powerwork.mode.BaseMode;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by smapley on 15/10/24.
 */
@ContentView(R.layout.activity_addtask)
public class AddTask extends BaseActivity {
    @ViewInject(R.id.title_tv_name)
    private TextView title_tv_name;

    @ViewInject(R.id.add_rv_list)
    private RecyclerView add_rv_list;

    private List<BaseMode> add_lis_data;
    private AddTaskAdapter add_aa_adapter;

    private int TYPE_NOW = -1;

    @Override
    protected void initParams() {
        title_tv_name.setText(R.string.addtask);

        add_rv_list.setLayoutManager(new LinearLayoutManager(this));
        add_lis_data = new ArrayList<>();
        add_lis_data.add(new Add_Text_Mode());
        add_lis_data.add(new Add_Menu_Mode());
        add_aa_adapter = new AddTaskAdapter(this, add_lis_data);
        add_rv_list.setAdapter(add_aa_adapter);


        doJump();


    }


    @Event({R.id.title_iv_back})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_iv_back:
                finish();
                break;
        }
    }

    /**
     * 跳转到相应界面
     */
    public void doJump() {
        TYPE_NOW = getIntent().getIntExtra("type", -1);
        showToast(TYPE_NOW + "");
        switch (TYPE_NOW) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 3:
                //选择图片
                if (resultCode == RESULT_OK) {
                    List<String> resultList = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    for (int i = 0; i < resultList.size(); i++) {
                        Add_Pic_Mode add_Pic_Mode = new Add_Pic_Mode();
                        add_Pic_Mode.setPath(resultList.get(i));
                        add_aa_adapter.addItem(new Add_Text_Mode());
                        add_aa_adapter.addItem(add_Pic_Mode);
                    }
                }
                break;
        }
    }
}
