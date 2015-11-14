package com.smapley.powerwork.mode;

/**
 * Created by smapley on 15/10/26.
 */
public class Per_Not_Voice_Mode extends BaseMode {

    private String  length;
    private String time;

    private final int type = 3;

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
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
