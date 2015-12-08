package com.smapley.powerwork.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smapley.powerwork.R;
import com.smapley.powerwork.entity.PTaskEntity;
import com.smapley.powerwork.holder.Pro_Item2_Group_Holder;
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


    public ProItem2Adapter(Context context, List<BaseMode> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    public void addAll(List<PTaskEntity> listData){

        list.addAll(listData);
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case 1:
                view = inflater.inflate(R.layout.adapter_pro_item2_group, parent, false);
                return new Pro_Item2_Group_Holder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case 1:
                ((Pro_Item2_Group_Holder) holder).setData(context,(Pro_Item2_Group_Mode) list.get(position));
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
