package com.smapley.powerwork.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smapley.powerwork.R;
import com.smapley.powerwork.entity.TaskEntity;
import com.smapley.powerwork.holder.Cal_Task_Holder;
import com.smapley.powerwork.mode.BaseMode;

import java.util.List;

/**
 * Created by smapley on 15/10/26.
 */
public class CalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<BaseMode> list;
    private LayoutInflater inflater;

    public CalAdapter(Context context, List<BaseMode> list) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
    }

    public void addAll(List<BaseMode> list){
        this.list=list;
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        switch (viewType) {
            case 0:
                view = inflater.inflate(R.layout.adapter_cal_task, parent, false);
                return new Cal_Task_Holder(view);

        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case 0:
                ((Cal_Task_Holder) holder).setData(context, (TaskEntity) list.get(position));
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
