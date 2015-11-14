package com.smapley.powerwork.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smapley.powerwork.R;
import com.smapley.powerwork.holder.Add_Menu_Holder;
import com.smapley.powerwork.holder.Add_Text_Holder;
import com.smapley.powerwork.holder.Tea_Btn_Holder;
import com.smapley.powerwork.holder.Tea_Mem_Holder;
import com.smapley.powerwork.mode.Add_Text_Mode;
import com.smapley.powerwork.mode.BaseMode;
import com.smapley.powerwork.mode.Tea_Btn_Mode;
import com.smapley.powerwork.mode.Tea_Mem_Mode;

import java.util.List;

/**
 * Created by smapley on 15/10/28.
 */
public class AddTaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<BaseMode> list;
    private LayoutInflater inflater;

    public AddTaskAdapter(Context context, List<BaseMode> list) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = inflater.inflate(R.layout.adapter_add_menu, parent, false);
                return new Add_Menu_Holder(view);
            case 5:
                view = inflater.inflate(R.layout.adapter_add_text, parent, false);
                return new Add_Text_Holder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case 0:
                ((Add_Menu_Holder) holder).setData(context, this, position);
                break;
            case 5:
                ((Add_Text_Holder) holder).setData(context, (Add_Text_Mode) list.get(position));
                break;
        }

    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        } else {
            return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType();
    }
}
