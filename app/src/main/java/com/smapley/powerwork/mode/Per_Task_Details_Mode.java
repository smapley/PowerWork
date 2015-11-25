package com.smapley.powerwork.mode;

/**
 * Created by smapley on 15/10/26.
 */
public class Per_Task_Details_Mode implements BaseMode {

    private boolean check;

    private String name;

    private String time;

    //闹钟时间
    private long warm=0;

    private final int type = 7;

    public boolean isCheck() {
        return check;
    }

    public long getWarm() {
        return warm;
    }

    public void setWarm(long warm) {
        this.warm = warm;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getType() {
        return type;

    }
}
