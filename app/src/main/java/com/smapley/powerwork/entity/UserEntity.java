package com.smapley.powerwork.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * UserEntity entity provides the base persistence definition of the User
 * entity. @author MyEclipse Persistence Tools
 */
@Table(name = "user")
public class UserEntity {

    @Column(name = "id", isId = true, autoGen = false)
    private int useId;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
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
    @Column(name = "skey")
    private String skey;



    public int getUseId() {
        return useId;
    }

    public void setUseId(int useId) {
        this.useId = useId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getSkey() {
        return skey;
    }

    public void setSkey(String skey) {
        this.skey = skey;
    }
}