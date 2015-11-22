package com.smapley.powerwork.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name = "user")
public class User_Entity  {

    @Column(name = "id",isId = true,autoGen = false)
    private int use_id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "truename")
    private String truename;
    @Column(name = "pic_url")
    private String pic_url;
    @Column(name = "phone")
    private String phone;
    @Column(name = "birthday")
    private long birthday;
    @Column(name = "cre_date")
    private long cre_date;
    @Column(name = "skey")
    private String skey;

    public int getUse_id() {
        return use_id;
    }

    public void setUse_id(int use_id) {
        this.use_id = use_id;
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

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public void setCre_date(long cre_date) {
        this.cre_date = cre_date;
    }

    public long getBirthday() {
        return birthday;
    }

    public long getCre_date() {
        return cre_date;
    }

    public String getSkey() {
        return skey;
    }

    public void setSkey(String skey) {
        this.skey = skey;
    }
}
