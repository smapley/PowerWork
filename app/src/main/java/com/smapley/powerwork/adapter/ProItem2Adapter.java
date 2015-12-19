package com.smapley.powerwork.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smapley.powerwork.R;
import com.smapley.powerwork.db.entity.TaskEntity;
import com.smapley.powerwork.holder.Cal_Task_Holder;
import com.smapley.powerwork.holder.Pro_Item2_Group_Holder;
import com.smapley.powerwork.holder.Pro_Item2_Task2_Holder;
import com.smapley.powerwork.mode.BaseMode;
import com.smapley.powerwork.mode.Pro_Item2_Group_Mode;

import java.util.List;

/**
 * Created by smapley on 15/11/17.
 */
public class ProItem2Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<BaseMode> list;
    private LayoutInflater inflater;
    private int myTaskPosition=1;
    private int otherTaskPosition=2;
    private List<TaskEntity> myTaskList;
    private List<TaskEntity> otherTaskList;


    public ProItem2Adapter(Context context, List<BaseMode> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    public void addMyTask(List<TaskEntity> myTaskList) {
        this.myTaskList=myTaskList;
        list.addAll(myTaskPosition, myTaskList);
        notifyItemRangeInserted(myTaskPosition, myTaskList.size());
        myTaskPosition+=myTaskList.size();
        otherTaskPosition+=myTaskList.size();
    }

    public void addOtherTask(List<TaskEntity> otherTaskList) {
        this.otherTaskList=otherTaskList;
        list.addAll(otherTaskPosition, otherTaskList);
        notifyItemRangeInserted(otherTaskPosition,otherTaskList.size());
        otherTaskPosition+=otherTaskList.size();
    }

    public void removeMyTask(){
        if(myTaskList!=null&&!myTaskList.isEmpty()) {
            list.removeAll(myTaskList);
            notifyItemRangeRemoved(1, myTaskList.size());
            otherTaskPosition = otherTaskPosition - myTaskList.size();
            myTaskPosition = 1;
        }

    }
    public void removeOtherTask() {
        if(otherTaskList!=null&&!otherTaskList.isEmpty()) {
            list.removeAll(otherTaskList);
            notifyItemRangeRemoved(myTaskPosition + 1, otherTaskList.size());
            otherTaskPosition = myTaskPosition + 1;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case 0:
                view = inflater.inflate(R.layout.adapter_cal_task, parent, false);
                return new Cal_Task_Holder(view);
            case 1:
                view = inflater.inflate(R.layout.adapter_pro_item2_group, parent, false);
                return new Pro_Item2_Group_Holder(view);
            case 2:
                view = inflater.inflate(R.layout.adapter_pro_item2_task2, parent, false);
                return new Pro_Item2_Task2_Holder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case 0:
                ((Cal_Task_Holder) holder).setData(context, (TaskEntity) list.get(position));
                break;
            case 1:
                ((Pro_Item2_Group_Holder) holder).setData(context,(Pro_Item2_Group_Mode) list.get(position));
                break;
            case 2:
                ((Pro_Item2_Task2_Holder) holder).setData(context, (TaskEntity) list.get(position));
                break;

        }
    }

    @Override
    public int getItemCount() {
        if (list == null)
            return 0;
        else
            return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType();
    }


}
