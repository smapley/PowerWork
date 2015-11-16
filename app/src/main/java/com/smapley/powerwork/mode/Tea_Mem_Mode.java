package com.smapley.powerwork.mode;

/**
 * Created by smapley on 15/10/28.
 */
public class Tea_Mem_Mode extends BaseMode {

    private String pic_url;

    private final int type = 1;

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    @Override
    public int getType() {
        return type;
    }
}
