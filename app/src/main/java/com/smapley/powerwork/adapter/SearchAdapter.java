package com.smapley.powerwork.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smapley.powerwork.R;
import com.smapley.powerwork.db.entity.ProjectEntity;
import com.smapley.powerwork.holder.Sea_Item_Holder;

import java.util.List;

/**
 * Created by smapley on 15/10/28.
 */
public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<ProjectEntity> list;
    private LayoutInflater inflater;

    public SearchAdapter(Context context, List<ProjectEntity> list) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 1:
                view = inflater.inflate(R.layout.adapter_sea_item, parent, false);
                return new Sea_Item_Holder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case 1:
                ((Sea_Item_Holder) holder).setData(context, list.get(position));
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
