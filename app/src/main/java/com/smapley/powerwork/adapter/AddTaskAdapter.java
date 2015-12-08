package com.smapley.powerwork.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smapley.powerwork.R;
import com.smapley.powerwork.holder.Add_Menu_Holder;
import com.smapley.powerwork.holder.Add_Pic_Holder;
import com.smapley.powerwork.holder.Add_Text_Holder;
import com.smapley.powerwork.holder.Add_Voice_Holder;
import com.smapley.powerwork.mode.Add_Item_Mode;

import java.util.List;

/**
 * Created by smapley on 15/10/28.
 */
public class AddTaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Add_Item_Mode> list;
    private LayoutInflater inflater;

  //  public int focusPosition = 0;

    public AddTaskAdapter(Context context, List<Add_Item_Mode> list) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;

    }

    public List<Add_Item_Mode> getList() {
        return list;
    }

    public void addItem(Add_Item_Mode mode) {
        list.add(list.size()-1, mode);
        notifyItemInserted(list.size()-1);
        notifyItemRangeChanged(list.size()-2, list.size());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = inflater.inflate(R.layout.adapter_add_menu, parent, false);
                return new Add_Menu_Holder(view);
            case 3:
                view = inflater.inflate(R.layout.adapter_add_pic, parent, false);
                return new Add_Pic_Holder(view);
            case 4:
                view = inflater.inflate(R.layout.adapter_add_voice, parent, false);
                return new Add_Voice_Holder(view);
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
            case 3:
                ((Add_Pic_Holder) holder).setData(context, list.get(position));
                break;
            case 4:
                ((Add_Voice_Holder) holder).setData(context, list.get(position));
                break;
            case 5:
                ((Add_Text_Holder) holder).setData(this, list.get(position), position);
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
