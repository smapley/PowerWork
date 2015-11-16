package com.smapley.powerwork.mode;

/**
 * Created by smapley on 15/10/26.
 */
public class Pro_Item_Mode extends BaseMode {

    private final int type = 1;

    private int pro_id;
    private String path;
    private String name;
    private boolean hasNow=false;

    public int getPro_id() {
        return pro_id;
    }

    public void setPro_id(int pro_id) {
        this.pro_id = pro_id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHasNow() {
        return hasNow;
    }

    public void setHasNow(boolean hasNow) {
        this.hasNow = hasNow;
    }

    @Override
    public int getType() {
        return type;

    }
}
