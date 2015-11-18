package com.smapley.powerwork.entity;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.NoAutoIncrement;

import java.sql.Timestamp;

public class User_Entity  {

    @Id
    @NoAutoIncrement
    private int use_id;
    private String username;
    private String password;
    private String nickname;
    private String truename;
    private String pic_url;
    private String phone;
    private long birthday;
    private long cre_date;
    private String key;

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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
