package com.smapley.powerwork.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smapley.powerwork.R;
import com.smapley.powerwork.db.entity.NoteEntity;
import com.smapley.powerwork.holder.Per_Not_Write_Holder;

import java.util.List;

/**
 * Created by smapley on 15/10/26.
 */
public class NoteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<NoteEntity> list;
    private LayoutInflater inflater;

    public NoteAdapter(Context context, List<NoteEntity> list) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
    }



    public void addAll(List<NoteEntity> listMode){
        list.clear();
        list.addAll(listMode);
        notifyDataSetChanged();
    }




    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        switch (viewType) {
            case 3:
                view = inflater.inflate(R.layout.adapter_per_notice_write, parent, false);
                return new Per_Not_Write_Holder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case 3:
                ((Per_Not_Write_Holder) holder).setData(list.get(position));
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
