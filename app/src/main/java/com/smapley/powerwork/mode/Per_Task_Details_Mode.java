package com.smapley.powerwork.mode;

/**
 * Created by smapley on 15/10/26.
 */
public class Per_Task_Details_Mode extends BaseMode {

    private boolean check;

    private String name;

    private String time;

    private final int type = 7;

    public boolean isCheck() {
        return check;
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
