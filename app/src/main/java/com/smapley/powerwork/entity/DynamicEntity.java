package com.smapley.powerwork.entity;

import com.smapley.powerwork.mode.BaseMode;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by smapley on 15/11/27.
 */
@Table(name = "Dynamic")
public class DynamicEntity implements BaseMode {

    @Column(name = "dyn_id",isId = true,autoGen = false)
    private int dyn_id;
    @Column(name = "use_id")
    private int use_id;
    @Column(name = "tas_id")
    private int tas_id;
    @Column(name = "type")
    private int type;
    @Column(name = "pro_id")
    private int pro_id;
    @Column(name = "pic_url")
    private String pic_url;
    @Column(name = "use_name")
    private String use_name;
    @Column(name = "cre_date")
    private long cre_date;
    @Column(name = "dpic_url")
    private String dPic_url;
    @Column(name = "detai")
    private String detai;
    @Column(name = "ispraise")
    private boolean isPraise;
    @Column(name = "praise_num")
    private int praise_num;
    @Column(name = "isdiscuss")
    private boolean isDiscuss;
    @Column(name = "discuss_num")
    private int discuss_num;


    public int getDyn_id() {
        return dyn_id;
    }

    public void setDyn_id(int dyn_id) {
        this.dyn_id = dyn_id;
    }

    public int getUse_id() {
        return use_id;
    }

    public void setUse_id(int use_id) {
        this.use_id = use_id;
    }

    public int getTas_id() {
        return tas_id;
    }

    public void setTas_id(int tas_id) {
        this.tas_id = tas_id;
    }


    public void setType(int type) {
        this.type = type;
    }

    public int getPro_id() {
        return pro_id;
    }

    public void setPro_id(int pro_id) {
        this.pro_id = pro_id;
    }

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

    public void setPraise(boolean isPraise) {
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

    public void setDiscuss(boolean isDiscuss) {
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
        return type;
    }
}
