package com.smapley.powerwork.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sleepbot.datetimepicker.time.RadialTextsView;
import com.smapley.powerwork.R;
import com.smapley.powerwork.holder.Pro_Item5_Folder_Holder;
import com.smapley.powerwork.holder.Pro_Item5_Title_Holder;
import com.smapley.powerwork.mode.BaseMode;
import com.smapley.powerwork.mode.Pro_Item5_Folder_Mode;
import com.smapley.powerwork.mode.Pro_Item5_Title_Mode;

import java.util.List;

/**
 * Created by smapley on 15/11/17.
 */
public class ProItem5Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<BaseMode> list;
    private LayoutInflater inflater;

    private boolean hasFolder = false;
    private boolean hasFile = false;
    private int folderPosition = -1;
    private int filePosition = -1;

    public ProItem5Adapter(Context context, List<BaseMode> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    public void addFolder(BaseMode mode) {
        if (!hasFolder) {
            Pro_Item5_Title_Mode pro_item5_title_mode = new Pro_Item5_Title_Mode();
            pro_item5_title_mode.setName(context.getString(R.string.pro_item5_title_folder));
            list.add(0, pro_item5_title_mode);
            notifyItemInserted(0);
            notifyItemRangeChanged(0, list.size());
            folderPosition = 1;
            hasFolder = true;
        }
        list.add(folderPosition, mode);
        notifyItemInserted(folderPosition);
        folderPosition++;
        notifyItemRangeChanged(folderPosition, list.size());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case 1:
                view = inflater.inflate(R.layout.adapter_pro_item5_title, parent, false);
                return new Pro_Item5_Title_Holder(view);
            case 2:
                view = inflater.inflate(R.layout.adapter_pro_item5_folder, parent, false);
                return new Pro_Item5_Folder_Holder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case 1:
                ((Pro_Item5_Title_Holder) holder).setData((Pro_Item5_Title_Mode) list.get(position));
                break;
            case 2:
                ((Pro_Item5_Folder_Holder) holder).setData((Pro_Item5_Folder_Mode) list.get(position));
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
