package com.smapley.powerwork.mode;

/**
 * Created by smapley on 15/10/30.
 */
public class Add_Pic_Mode extends BaseMode {


    private final int type = 3;

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }

    private String Path;

    @Override
    public int getType() {
        return type;
    }
}
