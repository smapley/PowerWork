package com.smapley.powerwork.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smapley.powerwork.R;
import com.smapley.powerwork.entity.TaskEntity;
import com.smapley.powerwork.holder.Cal_Task_Holder;
import com.smapley.powerwork.holder.Per_Group_Holder;
import com.smapley.powerwork.holder.Per_Not_Pic_Holder;
import com.smapley.powerwork.holder.Per_Not_Text_Holder;
import com.smapley.powerwork.holder.Per_Not_Voice_Holder;
import com.smapley.powerwork.holder.Per_Not_Write_Holder;
import com.smapley.powerwork.holder.Per_Task_Details_Holder;
import com.smapley.powerwork.holder.Per_Task_Holder;
import com.smapley.powerwork.mode.BaseMode;
import com.smapley.powerwork.mode.Per_Group_Mode;
import com.smapley.powerwork.mode.Per_Not_Pic_Mode;
import com.smapley.powerwork.mode.Per_Not_Text_Mode;
import com.smapley.powerwork.mode.Per_Not_Voice_Mode;
import com.smapley.powerwork.mode.Per_Not_Write_Mode;
import com.smapley.powerwork.mode.Per_Task_Details_Mode;
import com.smapley.powerwork.mode.Per_Task_Mode;

import java.util.List;

/**
 * Created by smapley on 15/10/26.
 */
public class PersonalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<BaseMode> list;
    private LayoutInflater inflater;

    public PersonalAdapter(Context context, List<BaseMode> list) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
    }

    public void addAll(List<BaseMode> list){
        this.list=list;
        notifyDataSetChanged();
    }

    public void addItem(BaseMode mode, int position) {
        list.add(position, mode);
        notifyItemInserted(position);
        notifyItemRangeChanged(position, getItemCount() - 1);
    }

    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount() - 1);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        switch (viewType) {
            case 0:
                view = inflater.inflate(R.layout.adapter_cal_task, parent, false);
                return new Cal_Task_Holder(view);
            case 1:
                view = inflater.inflate(R.layout.adapter_per_group, parent, false);
                return new Per_Group_Holder(view);
            case 2:
                view = inflater.inflate(R.layout.adapter_per_task, parent, false);
                return new Per_Task_Holder(view);
            case 3:
                view = inflater.inflate(R.layout.adapter_per_notice_voice, parent, false);
                return new Per_Not_Voice_Holder(view);
            case 4:
                view = inflater.inflate(R.layout.adapter_per_notice_text, parent, false);
                return new Per_Not_Text_Holder(view);
            case 5:
                view = inflater.inflate(R.layout.adapter_per_notice_pic, parent, false);
                return new Per_Not_Pic_Holder(view);
            case 6:
                view = inflater.inflate(R.layout.adapter_per_notice_write, parent, false);
                return new Per_Not_Write_Holder(view);
            case 7:
                view = inflater.inflate(R.layout.adapter_per_task_details, parent, false);
                return new Per_Task_Details_Holder(view);
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
                ((Per_Group_Holder) holder).setData(context, (Per_Group_Mode) list.get(position));
                break;
            case 2:
                ((Per_Task_Holder) holder).setData(context, this, (Per_Task_Mode) list.get(position), position);
                break;
            case 3:
                ((Per_Not_Voice_Holder) holder).setData((Per_Not_Voice_Mode) list.get(position));
                break;
            case 4:
                ((Per_Not_Text_Holder) holder).setData((Per_Not_Text_Mode) list.get(position));
                break;
            case 5:
                ((Per_Not_Pic_Holder) holder).setData((Per_Not_Pic_Mode) list.get(position));
                break;
            case 6:
                ((Per_Not_Write_Holder) holder).setData((Per_Not_Write_Mode) list.get(position));
                break;
            case 7:
                ((Per_Task_Details_Holder) holder).setData(context, this, (Per_Task_Details_Mode) list.get(position), position);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType();
    }


}
