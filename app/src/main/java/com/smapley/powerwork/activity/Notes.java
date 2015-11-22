package com.smapley.powerwork.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.smapley.powerwork.R;
import com.smapley.powerwork.adapter.PersonalAdapter;
import com.smapley.powerwork.mode.BaseMode;
import com.smapley.powerwork.mode.Per_Not_Pic_Mode;
import com.smapley.powerwork.mode.Per_Not_Text_Mode;
import com.smapley.powerwork.mode.Per_Not_Voice_Mode;
import com.smapley.powerwork.mode.Per_Not_Write_Mode;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smapley on 15/10/29.
 */
@ContentView(R.layout.activity_notes)
public class Notes extends BaseActivity {

    @ViewInject(R.id.title_tv_name)
    private TextView title_tv_name;

    @ViewInject(R.id.not_rv_list)
    private RecyclerView not_rv_list;

    private List<BaseMode> not_lis_data;
    private PersonalAdapter not_pa_adapter;


    @Override
    protected void initParams() {
        title_tv_name.setText(R.string.notes);
        not_rv_list.setLayoutManager(new LinearLayoutManager(this));

        not_lis_data = new ArrayList<>();

        Per_Not_Voice_Mode Per_Not_Voice_Mode = new Per_Not_Voice_Mode();
        Per_Not_Voice_Mode.setLength("5");
        Per_Not_Voice_Mode.setTime("10-25");
        not_lis_data.add(Per_Not_Voice_Mode);

        Per_Not_Text_Mode Per_Not_Text_Mode = new Per_Not_Text_Mode();
        Per_Not_Text_Mode.setName("*********");
        Per_Not_Text_Mode.setTime("10-26");
        not_lis_data.add(Per_Not_Text_Mode);

        Per_Not_Pic_Mode Per_Not_Pic_Mode = new Per_Not_Pic_Mode();
        Per_Not_Pic_Mode.setName("图片1");
        Per_Not_Pic_Mode.setTime("10-26");
        not_lis_data.add(Per_Not_Pic_Mode);

        Per_Not_Write_Mode per_Not_Write_Mode = new Per_Not_Write_Mode();
        per_Not_Write_Mode.setName("手写1");
        per_Not_Write_Mode.setTime("10-26");
        not_lis_data.add(per_Not_Write_Mode);

        not_pa_adapter = new PersonalAdapter(this, not_lis_data);
        not_rv_list.setAdapter(not_pa_adapter);


    }

    @Event({R.id.title_iv_back})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_iv_back:
                finish();
                break;
        }
    }
}
