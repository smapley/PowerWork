package com.smapley.powerwork.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smapley.powerwork.R;
import com.smapley.powerwork.db.entity.FileEntity;
import com.smapley.powerwork.db.entity.FolderEntity;
import com.smapley.powerwork.fragment.Pro_Item3;
import com.smapley.powerwork.holder.Pro_Item3_File_Holder;
import com.smapley.powerwork.holder.Pro_Item3_Folder_Holder;
import com.smapley.powerwork.mode.BaseMode;

import java.util.List;

/**
 * Created by smapley on 15/11/17.
 */
public class ProItem3Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<BaseMode> list;
    private LayoutInflater inflater;

    private int folderPosition = 0;
    private int filePosition = 0;
    private List<FolderEntity> folderList;
    private List<FileEntity> fileList;
    private Pro_Item3 pro_item3;

    public ProItem3Adapter(Context context, Pro_Item3 pro_item3, List<BaseMode> list) {
        this.context = context;
        this.list = list;
        this.pro_item3 = pro_item3;
        inflater = LayoutInflater.from(context);
    }

    public void addFolder(List<FolderEntity> folderList) {
        this.folderList = folderList;
        list.addAll(folderPosition, folderList);
        notifyItemRangeInserted(folderPosition, folderList.size());
        folderPosition += folderList.size();
        filePosition += folderList.size();
    }

    public void addFile(List<FileEntity> fileList) {
        this.fileList = fileList;
        list.addAll(filePosition, fileList);
        notifyItemRangeInserted(filePosition, fileList.size());
        filePosition += fileList.size();
    }

    public void removeFolder() {
        if (folderList != null && !folderList.isEmpty()) {
            list.removeAll(folderList);
            notifyItemRangeRemoved(0, folderList.size());
            filePosition = filePosition - folderList.size();
            folderPosition = 0;
        }

    }

    public void removeFile() {
        if (fileList != null && !fileList.isEmpty()) {
            list.removeAll(fileList);
            notifyItemRangeRemoved(folderPosition , fileList.size());
            filePosition = folderPosition ;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case 0:
                view = inflater.inflate(R.layout.adapter_pro_item3_folder, parent, false);
                return new Pro_Item3_Folder_Holder(view);
            default:
                view = inflater.inflate(R.layout.adapter_pro_item3_file, parent, false);
                return new Pro_Item3_File_Holder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case 0:
                ((Pro_Item3_Folder_Holder) holder).setData(pro_item3, (FolderEntity) list.get(position));
                break;
            default:
                ((Pro_Item3_File_Holder) holder).setData(pro_item3, (FileEntity) list.get(position));
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
