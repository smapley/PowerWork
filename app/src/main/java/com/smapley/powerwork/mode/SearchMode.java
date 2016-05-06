package com.smapley.powerwork.mode;

import com.smapley.powerwork.db.entity.ProjectEntity;
import com.smapley.powerwork.db.entity.UserEntity;

/**
 * Created by smapley on 15/11/29.
 */
public class SearchMode implements BaseMode {

    private int type;

    private int id;
    private long cre_date;
    private String pic_url;
    private String name;

    public SearchMode(ProjectEntity entity) {
        this.type = 1;
        this.cre_date=entity.getCre_date();
        this.pic_url=entity.getPic_url();
        this.name=entity.getName();
        this.id=entity.getPro_id();
    }
    public SearchMode(UserEntity entity) {
        this.type = 0;
        this.id=entity.getUseId();
        this.cre_date=Long.parseLong(entity.getPhone());
        this.pic_url=entity.getPicUrl();
        this.name=entity.getTruename();
    }

    public SearchMode(){

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
