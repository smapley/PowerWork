package com.smapley.powerwork.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by smapley on 15/12/1.
 */
@Table(name = "ProUser")
public class ProUserEntity {

    @Column(name = "id", isId = true)
    private int id;
    @Column(name = "use_id")
    private int use_id;
    @Column(name = "user_truename")
    private String user_truename;
    @Column(name = "pic_url")
    private String pic_url;
    @Column(name = "pro_id")
    private int pro_id;
    @Column(name = "rank")
    private int rank;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUse_id() {
        return use_id;
    }

    public void setUse_id(int use_id) {
        this.use_id = use_id;
    }

    public String getUser_truename() {
        return user_truename;
    }

    public void setUser_truename(String user_truename) {
        this.user_truename = user_truename;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public int getPro_id() {
        return pro_id;
    }

    public void setPro_id(int pro_id) {
        this.pro_id = pro_id;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
