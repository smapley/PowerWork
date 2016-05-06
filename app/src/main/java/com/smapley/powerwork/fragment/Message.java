package com.smapley.powerwork.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.smapley.powerwork.R;
import com.smapley.powerwork.adapter.MessageAdapter;
import com.smapley.powerwork.db.entity.MessageEntity;
import com.smapley.powerwork.db.entity.UserEntity;
import com.smapley.powerwork.http.service.MessageListService;
import com.smapley.powerwork.mode.Message_Mode;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smapley on 15/10/25.
 */
@ContentView(R.layout.fragment_message)
public class Message extends BaseFragment {

    @ViewInject(R.id.mes_rv_list)
    private RecyclerView mes_rv_list;

    private List<Message_Mode> mes_lis_data=new ArrayList<>();
    private MessageAdapter messageAdapter;

    private MessageListService messageListService=new MessageListService() {
        @Override
        public void onSucceed() {
            getDataForDb();
        }
    };

    @Override
    protected void initParams(View view) {

        getDataForWeb();
        getDataForDb();







    }

    @Override
    public void getDataForDb() {
        try{
            mes_lis_data.clear();
            List<MessageEntity> messageEntities = dbUtils.selector(MessageEntity.class)
                    .where("use_id", "=", userEntity.getUseId())
                    .or("src_use_id", "=", userEntity.getUseId())
                    .orderBy("cre_date", true)
                    .findAll();
            for(MessageEntity entity:messageEntities){
                UserEntity userEntity=dbUtils.findById(UserEntity.class,entity.getSrc_use_id());
                Message_Mode mode=new Message_Mode(entity,userEntity);
                mes_lis_data.add(mode);
            }
            Log.d("size",mes_lis_data.size()+"");
            mes_rv_list.setLayoutManager(new LinearLayoutManager(getActivity()));
            messageAdapter = new MessageAdapter(getActivity(), mes_lis_data);
            mes_rv_list.setAdapter(messageAdapter);
        }catch (Exception e){

        }
    }


    @Override
    public void getDataForWeb() {
        messageListService.load(userEntity);
    }

}
