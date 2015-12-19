package com.smapley.powerwork.db.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name = "User")
public class UserEntity {
    @Column(name = "id", isId = true, autoGen = false)
    private int useId;
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "truename")
    private String truename;
    @Column(name = "pic_url")
    private String picUrl;
    @Column(name = "phone")
    private String phone;
    @Column(name = "birthday")
    private long birthday;
    @Column(name = "cre_date")
    private long creDate;
    @Column(name = "refresh")
    private long refresh;
    @Column(name = "state")
    private int state;


    public int getUseId() {
        return useId;
    }

    public void setUseId(int useId) {
        this.useId = useId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public long getCreDate() {
        return creDate;
    }

    public void setCreDate(long creDate) {
        this.creDate = creDate;
    }

    public long getRefresh() {
        return refresh;
    }

    public void setRefresh(long refresh) {
        this.refresh = refresh;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }


}
