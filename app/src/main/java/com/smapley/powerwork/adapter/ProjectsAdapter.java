package com.smapley.powerwork.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smapley.powerwork.R;
import com.smapley.powerwork.holder.Pro_AddItem_Holder;
import com.smapley.powerwork.holder.Pro_Item_Holder;
import com.smapley.powerwork.mode.BaseMode;
import com.smapley.powerwork.mode.Pro_AddItem_Mode;
import com.smapley.powerwork.mode.Pro_Item_Mode;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by smapley on 15/11/16.
 */
public class ProjectsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<BaseMode> modeList;
    private Context context;
    private LayoutInflater inflater;

    public ProjectsAdapter(Context context, List<BaseMode> modeList) {
        this.modeList = modeList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        switch (viewType) {
            case 1:
                view = inflater.inflate(R.layout.adapter_pro_item, parent, false);
                return new Pro_Item_Holder(view);
            case 2:
                view = inflater.inflate(R.layout.adapter_pro_additem, parent, false);
                return new Pro_AddItem_Holder(view);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case 1:
                ((Pro_Item_Holder) holder).setData(context, (Pro_Item_Mode) modeList.get(position));
                break;
            case 2:
                ((Pro_AddItem_Holder) holder).setData(context, (Pro_AddItem_Mode) modeList.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return modeList.get(position).getType();
    }

    @Override
    public int getItemCount() {
        if (modeList == null) {
            return 0;
        } else {
            return modeList.size();
        }
    }
}
