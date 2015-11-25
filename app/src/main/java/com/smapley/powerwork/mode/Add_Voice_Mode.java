package com.smapley.powerwork.mode;

/**
 * Created by smapley on 15/10/30.
 */
public class Add_Voice_Mode implements BaseMode {


    private final int type = 4;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    private String path;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    private long time;

    @Override
    public int getType() {
        return type;
    }
}
