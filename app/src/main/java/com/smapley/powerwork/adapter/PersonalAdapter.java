package com.smapley.powerwork.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smapley.powerwork.R;
import com.smapley.powerwork.entity.NoteEntity;
import com.smapley.powerwork.entity.TaskEntity;
import com.smapley.powerwork.holder.Cal_Task_Holder;
import com.smapley.powerwork.holder.Per_Group_Holder;
import com.smapley.powerwork.holder.Per_Not_Write_Holder;
import com.smapley.powerwork.mode.BaseMode;
import com.smapley.powerwork.mode.Per_Group_Mode;

import java.util.List;

/**
 * Created by smapley on 15/10/26.
 */
public class PersonalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<BaseMode> list;
    private List<TaskEntity> listTask;
    private List<NoteEntity> listNote;
    private LayoutInflater inflater;

    private int taskPosition = 1;
    private int notePosition = 2;

    public PersonalAdapter(Context context, List<BaseMode> list) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
    }

    public void addTask(List<TaskEntity> listTask) {
        this.listTask=listTask;
        list.addAll(taskPosition, listTask);
        notifyItemRangeInserted(taskPosition, listTask.size());
        taskPosition+=listTask.size();
        notePosition+=listTask.size();
    }

    public void addNote(List<NoteEntity> listNote) {
        this.listNote=listNote;
        list.addAll(notePosition, listNote);
        notifyItemRangeInserted(notePosition,listNote.size());
        notePosition+=listNote.size();
    }

    public void removeTask(){
        if(listTask!=null&&!listTask.isEmpty()) {
            list.removeAll(listTask);
            notifyItemRangeRemoved(1, listTask.size());
            notePosition = notePosition - listTask.size();
            taskPosition = 1;
        }

    }
    public void removeNote() {
        if(listNote!=null&&!listNote.isEmpty()) {
            list.removeAll(listNote);
            notifyItemRangeRemoved(taskPosition + 1, listNote.size());
            notePosition = taskPosition + 1;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        switch (viewType) {
            case 0:
                view = inflater.inflate(R.layout.adapter_cal_task, parent, false);
                return new Cal_Task_Holder(view);
            case 1:
                view = inflater.inflate(R.layout.adapter_per_group, parent, false);
                return new Per_Group_Holder(view);
            case 3:
                view = inflater.inflate(R.layout.adapter_per_notice_write, parent, false);
                return new Per_Not_Write_Holder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case 0:
                ((Cal_Task_Holder) holder).setData(context, (TaskEntity) list.get(position));
                break;
            case 1:
                ((Per_Group_Holder) holder).setData(context, (Per_Group_Mode) list.get(position));
                break;
            case 3:
                ((Per_Not_Write_Holder) holder).setData((NoteEntity) list.get(position));
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
