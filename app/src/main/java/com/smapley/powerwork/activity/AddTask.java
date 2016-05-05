package com.smapley.powerwork.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.smapley.powerwork.R;
import com.smapley.powerwork.adapter.AddTaskAdapter;
import com.smapley.powerwork.db.entity.NoteDetailsEntity;

import org.xutils.common.util.LogUtil;
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
    @ViewInject(R.id.title_iv_done)
    private ImageView title_iv_done;

    @ViewInject(R.id.add_rv_list)
    private RecyclerView add_rv_list;

    private List<NoteDetailsEntity> add_lis_data;
    private AddTaskAdapter add_aa_adapter;

    @Override
    protected void initParams() {
        title_tv_name.setText(R.string.add);
        title_iv_done.setVisibility(View.VISIBLE);

        add_rv_list.setLayoutManager(new LinearLayoutManager(this));
        add_lis_data = new ArrayList<>();
        add_lis_data.add(new NoteDetailsEntity(5));
        add_lis_data.add(new NoteDetailsEntity(0));
        add_aa_adapter = new AddTaskAdapter(this, add_lis_data);
        add_rv_list.setAdapter(add_aa_adapter);
    }


    @Event({R.id.title_iv_back, R.id.title_iv_done})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_iv_back:
                finish();
                break;
            case R.id.title_iv_done:
                add_rv_list.clearFocus();
                Intent intent = new Intent(AddTask.this, PublishTask.class);
                intent.putExtra("data", JSON.toJSONString(add_aa_adapter.getList()));
                LogUtil.d("---"+JSON.toJSONString(add_aa_adapter.getList()));
                startActivity(intent);
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
                        NoteDetailsEntity add_item_mode = new NoteDetailsEntity(3);
                        add_item_mode.setPath(resultList.get(i));
                        add_aa_adapter.addItem(add_item_mode);
                        add_aa_adapter.addItem(new NoteDetailsEntity(5));
                    }
                }
                break;
        }
    }
}
