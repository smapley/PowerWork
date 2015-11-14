package com.smapley.powerwork.mode;

/**
 * Created by smapley on 15/10/26.
 */
public class Per_Not_Pic_Mode extends BaseMode {

    private String name;
    private String time;
    private final int type = 5;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public int getType() {
        return type;
    }
}
