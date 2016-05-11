package com.smapley.powerwork.mode;

import com.smapley.powerwork.db.entity.MessageEntity;
import com.smapley.powerwork.db.entity.UserEntity;

/**
 * Created by smapley on 15/11/29.
 */
public class Message_Mode implements BaseMode {

    private int type;

    private int id;
    private long cre_date;
    private String pic_url;
    private String name;
    private int projectId;


    public Message_Mode(MessageEntity entity,UserEntity userEntity) {
        this.type = entity.getType();
        this.id=entity.getMes_id();
        this.cre_date=entity.getCre_date();
        this.pic_url=userEntity.getPicUrl();
        this.name=entity.getDetails();
        this.projectId=entity.getPro_id();
    }

    public Message_Mode(){

    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getCre_date() {
        return cre_date;
    }

    public void setCre_date(long cre_date) {
        this.cre_date = cre_date;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getType() {
        return type;
    }
}
