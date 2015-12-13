package com.smapley.powerwork.entity;

import com.smapley.powerwork.mode.BaseMode;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.util.List;

/**
 * Created by smapley on 15/12/7.
 */
@Table(name = "OtherTask")
public class OtherTaskEntity implements BaseMode{

    @Column(name = "tas_id",isId = true,autoGen = false)
    private int tas_id;
    @Column(name = "pro_id")
    private int pro_id;
    @Column(name = "name")
    private String name;
    @Column(name = "sta_date")
    private long sta_date;
    @Column(name = "end_date")
    private long end_date;
    @Column(name = "progress")
    private int progress;
    @Column(name = "priority")
    private int priority;
    @Column(name = "cre_date")
    private long cre_date;

    private int type=2;

    private List<TasUseEntity> listTasUse;

    public int getTas_id() {
        return tas_id;
    }

    public void setTas_id(int tas_id) {
        this.tas_id = tas_id;
    }

    public int getPro_id() {
        return pro_id;
    }

    public void setPro_id(int pro_id) {
        this.pro_id = pro_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSta_date() {
        return sta_date;
    }

    public void setSta_date(long sta_date) {
        this.sta_date = sta_date;
    }

    public long getEnd_date() {
        return end_date;
    }

    public void setEnd_date(long end_date) {
        this.end_date = end_date;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public long getCre_date() {
        return cre_date;
    }

    public void setCre_date(long cre_date) {
        this.cre_date = cre_date;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<TasUseEntity> getListTasUse() {
        return listTasUse;
    }

    public void setListTasUse(List<TasUseEntity> listTasUse) {
        this.listTasUse = listTasUse;
    }

    @Override
    public int getType() {
        return type;
    }
}
