package com.smapley.powerwork.mode;

/**
 * Created by smapley on 15/11/17.
 */
public class Pro_Item5_Title_Mode implements BaseMode {

    private int type = 1;

    private String name;

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
