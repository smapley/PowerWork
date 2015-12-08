package com.smapley.powerwork.entity;

import com.smapley.powerwork.mode.BaseMode;

/**
 * Created by smapley on 15/11/28.
 */
public class PTaskEntity implements BaseMode {

    private final int type = 2;

    private String name;
    private long time;
    private int use_id;
    private int task_id;
    private String pic_url;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getUse_id() {
        return use_id;
    }

    public void setUse_id(int use_id) {
        this.use_id = use_id;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    @Override
    public int getType() {
        return type;
    }
}
