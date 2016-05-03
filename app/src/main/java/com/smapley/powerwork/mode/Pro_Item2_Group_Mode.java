package com.smapley.powerwork.mode;

/**
 * Created by smapley on 15/11/28.
 */
public class Pro_Item2_Group_Mode implements BaseMode {
    private final int type = 1;

    private String name;
    private boolean isShowAdd;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isShowAdd() {
        return isShowAdd;
    }

    public void setIsShowAdd(boolean isShowAdd) {
        this.isShowAdd = isShowAdd;
    }

    @Override
    public int getType() {
        return type;
    }
}
