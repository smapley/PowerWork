package com.smapley.powerwork.mode;

/**
 * Created by smapley on 15/10/30.
 */
public class Add_Text_Mode implements BaseMode {


    private final int type = 5;
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int getType() {
        return type;
    }
}
