package com.smapley.powerwork.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smapley.powerwork.R;
import com.smapley.powerwork.holder.Message_Item_Holder;
import com.smapley.powerwork.mode.Message_Mode;

import java.util.List;

/**
 * Created by smapley on 15/10/26.
 */
public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Message_Mode> list;
    private LayoutInflater inflater;

    public MessageAdapter(Context context, List<Message_Mode> list) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
    }





    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        switch (viewType) {
            case 0:
            case 1:
            case 2:
            case 3:
                view = inflater.inflate(R.layout.adapter_sea_item, parent, false);
                return new Message_Item_Holder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case 0:
            case 1:
            case 2:
            case 3:
                ((Message_Item_Holder) holder).setData(context,list.get(position));
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
