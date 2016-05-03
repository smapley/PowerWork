package com.smapley.powerwork.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.smapley.powerwork.R;
import com.smapley.powerwork.adapter.PersonalAdapter;
import com.smapley.powerwork.http.service.MessageListService;
import com.smapley.powerwork.mode.BaseMode;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by smapley on 15/10/25.
 */
@ContentView(R.layout.fragment_message)
public class Message extends BaseFragment {

    @ViewInject(R.id.mes_rv_list)
    private RecyclerView mes_rv_list;

    private List<BaseMode> mes_lis_data;
    private PersonalAdapter mes_pa_adapter;

    private MessageListService messageListService=new MessageListService() {
        @Override
        public void onSucceed() {

        }
    };

    @Override
    protected void initParams(View view) {

        getDataForWeb();

        mes_rv_list.setLayoutManager(new LinearLayoutManager(getActivity()));
//
//        mes_lis_data = new ArrayList<>();
//        for (int i = 0; i < 4; i++) {
//            Cal_Task_Mode mode = new Cal_Task_Mode();
//            mode.setName("画界面");
//            mode.setTime("10 - 29  17:54");
//            mes_lis_data.add(mode);
//        }
//
//        mes_pa_adapter = new PersonalAdapter(getActivity(), mes_lis_data);
//        mes_rv_list.setAdapter(mes_pa_adapter);


    }

    @Override
    public void getDataForDb() {

    }


    @Override
    public void getDataForWeb() {
        messageListService.load(userBaseEntity);
    }

}
