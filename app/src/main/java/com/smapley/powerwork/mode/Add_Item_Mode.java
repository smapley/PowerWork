package com.smapley.powerwork.mode;

/**
 * Created by smapley on 15/11/29.
 */
public class Add_Item_Mode implements BaseMode {

    private int type;

    private String path="";
    private long length;
    private String text="";


    public Add_Item_Mode(int type) {
        this.type = type;
    }

    public Add_Item_Mode(){

    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

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
