package com.smapley.powerwork.mode;

/**
 * Created by smapley on 15/10/28.
 */
public class Tea_Btn_Mode extends BaseMode {


    private int item;
    private final int type = 2;

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    @Override
    public int getType() {
        return type;
    }
}
