package com.smapley.powerwork.entity;

import com.smapley.powerwork.mode.BaseMode;

import org.xutils.db.annotation.Table;

/**
 * Created by smapley on 15/11/27.
 */
@Table(name = "Dynamic")
public class DynamicEntity implements BaseMode {

    private final int type = 1;

    private String pic_url;
    private String use_name;
    private int dType;
    private long cre_date;
    private String dPic_url;
    private String detai;
    private boolean isPraise;
    private int praise_num;
    private boolean isDiscuss;
    private int discuss_num;




    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getUse_name() {
        return use_name;
    }

    public void setUse_name(String use_name) {
        this.use_name = use_name;
    }

    public int getdType() {
        return dType;
    }

    public void setdType(int dType) {
        this.dType = dType;
    }

    public long getCre_date() {
        return cre_date;
    }

    public void setCre_date(long cre_date) {
        this.cre_date = cre_date;
    }

    public String getdPic_url() {
        return dPic_url;
    }

    public void setdPic_url(String dPic_url) {
        this.dPic_url = dPic_url;
    }

    public String getDetai() {
        return detai;
    }

    public void setDetai(String detai) {
        this.detai = detai;
    }

    public boolean isPraise() {
        return isPraise;
    }

    public void setIsPraise(boolean isPraise) {
        this.isPraise = isPraise;
    }

    public int getPraise_num() {
        return praise_num;
    }

    public void setPraise_num(int praise_num) {
        this.praise_num = praise_num;
    }

    public boolean isDiscuss() {
        return isDiscuss;
    }

    public void setIsDiscuss(boolean isDiscuss) {
        this.isDiscuss = isDiscuss;
    }

    public int getDiscuss_num() {
        return discuss_num;
    }

    public void setDiscuss_num(int discuss_num) {
        this.discuss_num = discuss_num;
    }

    @Override
    public int getType() {
        return 1;
    }
}
